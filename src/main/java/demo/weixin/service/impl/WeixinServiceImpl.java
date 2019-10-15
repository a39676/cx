package demo.weixin.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.mapper.ArticleShortMapper;
import demo.article.pojo.po.ArticleShort;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.baseCommon.service.CommonService;
import demo.weixin.mapper.WeixinAccessTokenMapper;
import demo.weixin.mapper.WeixinConstantMapper;
import demo.weixin.pojo.bo.WXMessageBO;
import demo.weixin.pojo.constant.WXApiUrlConstant;
import demo.weixin.pojo.constant.WXConstantName;
import demo.weixin.pojo.po.WeixinAccessToken;
import demo.weixin.pojo.result.SaveWXMessageAsFileResult;
import demo.weixin.pojo.result.WeixinCommonResult;
import demo.weixin.pojo.result.WeixinGetAccessTokenResult;
import demo.weixin.pojo.type.WXAppSwitchType;
import demo.weixin.pojo.type.WXMsgType;
import demo.weixin.pojo.type.WeixinResultType;
import demo.weixin.service.WeixinService;
import httpHandel.HttpUtil;
import ioHandle.FileUtilCustom;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import numericHandel.NumericUtilCustom;

@Service
public class WeixinServiceImpl extends CommonService implements WeixinService {

	@Autowired
	private WeixinConstantMapper weixinConstantMapper;
	@Autowired
	private WeixinAccessTokenMapper weixinAccessTokenMapper;
	@Autowired
	private ArticleShortMapper articleShortMapper;
	@Autowired
	private FileUtilCustom ioUtil;

	private static String token = "weixinTestToken";

	@Override
	public String weixinTokenTest(String signature, String timestamp, String nonce, String echostr) {
		StringBuffer sb = new StringBuffer("signature:" + signature + ", timestamp:" + timestamp + ", nonce:" + nonce
				+ ", echostr:" + echostr + ";\n");

		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			sb.append("fail: " + new Date() + "\n");
			return "false";
		}

		if (checkSignature(signature, timestamp, nonce)) {
			sb.append("success: " + new Date() + "\n");
			return echostr;
		} else {
			sb.append("fail: " + new Date() + "\n");
			return "false";
		}
	}

	private boolean checkSignature(String signature, String timestamp, String nonce) {
		// 拼接字符串
		String[] arr = new String[] { token, timestamp, nonce };
		// 排序
		Arrays.sort(arr);
		// 生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		// SHA1加密
		String tmp = SHA1Encrypt(content.toString());
		return tmp.equals(signature);
	}

	private String SHA1Encrypt(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public WeixinCommonResult getNewToken() throws Exception {
		WeixinCommonResult errorResult = new WeixinCommonResult();

		StringBuffer urlStr = new StringBuffer(WXApiUrlConstant.getToken);
		String switchType = weixinConstantMapper.getConstant(WXConstantName.switchType);
		String appid = null;
		String secret = null;

		if (WXAppSwitchType.app3.getType().equals(switchType)) {
			appid = weixinConstantMapper.getConstant(WXConstantName.appId39676);
			secret = weixinConstantMapper.getConstant(WXConstantName.appSecret39676);
		} else if (WXAppSwitchType.app1.getType().equals(switchType)) {
			appid = weixinConstantMapper.getConstant(WXConstantName.appid);
			secret = weixinConstantMapper.getConstant(WXConstantName.appsecret);
		} else if (WXAppSwitchType.app2.getType().equals(switchType)) {
			appid = weixinConstantMapper.getConstant(WXConstantName.appid2);
			secret = weixinConstantMapper.getConstant(WXConstantName.appsecret2);
		}

		if (StringUtils.isAnyBlank(appid, secret)) {
			errorResult.fillWithResult(ResultTypeCX.serviceError);
			return errorResult;
		}

		urlStr.append("?appid=" + appid);
		urlStr.append("&secret=" + secret);
		urlStr.append("&grant_type=" + WXConstantName.grant_type);

		HttpUtil httpUtil = new HttpUtil();
		String httpResult = httpUtil.sendGet(urlStr.toString());

		WeixinGetAccessTokenResult result = new WeixinGetAccessTokenResult()
				.buildResult(createWeixinResultFromResponseString(httpResult));

		String accessToken = null;
		if (result.isSuccess()) {
			accessToken = result.getJson().getString("access_token");
			result.setAccessToken(accessToken);
		} else {
			return result;
		}

		if (StringUtils.isBlank(accessToken)) {
			errorResult.fillWithResult(ResultTypeCX.communicationError);
			return errorResult;
		} else {
			WeixinAccessToken accessTokenPO = new WeixinAccessToken();
			accessTokenPO.setCreateTime(new Date());
			accessTokenPO.setToken(result.getAccessToken());
			weixinAccessTokenMapper.insertSelective(accessTokenPO);
		}

		return result;
	}

	private WeixinCommonResult createWeixinResultFromResponseString(String httpResult) {
		WeixinCommonResult result = new WeixinCommonResult();

		if (StringUtils.isBlank(httpResult)) {
			result.fillWithResult(ResultTypeCX.communicationError);
			return result;
		}

		JSONObject tmpJson = null;
		try {
			tmpJson = JSONObject.fromObject(httpResult);
		} catch (Exception e) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		result.setJson(tmpJson);

		if (tmpJson.keySet().size() < 1) {
			result.fillWithResult(WeixinResultType.serviceError);
			return result;
		}

		if (tmpJson.containsKey("errmsg") || tmpJson.containsKey("errcode")) {
			result.setIsFail();
			result.setMessage(tmpJson.getString("errmsg"));
		} else {
			result.setIsSuccess();
		}

		return result;
	}

	@Override
	public void getNewWXMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter op)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("UTF-8");
		}
		BufferedReader reader = request.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}

		ArticleShort s = new ArticleShort();
		s.setContent(sb.toString());
		s.setUserId(8L);
		s.setArticleId(snowFlake.getNextId());
		articleShortMapper.insertSelective(s);

		JSONObject jsonInput = null;
		if (sb.toString().trim().startsWith("{")) {
			jsonInput = JSONObject.fromObject(sb.toString());
		} else if (sb.toString().trim().startsWith("<")) {
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON baseJson = xmlSerializer.read(sb.toString());
			jsonInput = JSONObject.fromObject(baseJson);
		}
		
		WXMessageBO wxMessageBO = buildWXMessageByJson(jsonInput);

		WXMsgType msgType = WXMsgType.getType(wxMessageBO.getMsgType());

		SaveWXMessageAsFileResult saveMessageResult = null;
		if (msgType == null) {
			// TODO
			// should do something notify master wx
		} else if (msgType.equals(WXMsgType.event)) {
			// TODO
		} else if (msgType.equals(WXMsgType.text) || msgType.equals(WXMsgType.image) || msgType.equals(WXMsgType.voice)
				|| msgType.equals(WXMsgType.video) || msgType.equals(WXMsgType.shortvideo)
				|| msgType.equals(WXMsgType.location) || msgType.equals(WXMsgType.link)) {
			// TODO
			String openId = wxMessageBO.getFromUserName();
			if (StringUtils.isBlank(openId)) {
				return;
			}
			saveMessageResult = saveWXMessageAsFile(openId, wxMessageBO.getContent());
		}

		JSONObject jsonOutput = new JSONObject();
		jsonOutput.put("ToUserName", wxMessageBO.getFromUserName());
		jsonOutput.put("FromUserName", wxMessageBO.getToUserName());
		jsonOutput.put("CreateTime", System.currentTimeMillis());
		jsonOutput.put("MsgType", WXMsgType.text);
		String replyContent = null;
		if (saveMessageResult.isSuccess()) {
			if (msgType.equals(WXMsgType.text)) {
				replyContent = "已收到" + wxMessageBO.getContent();
			}
		} else {
			if (msgType.equals(WXMsgType.text)) {
				replyContent = "讯息异常" + wxMessageBO.getContent();
			}
		}
		jsonOutput.put("Content", replyContent);

		op.print(jsonOutput.toString());
	}

	private SaveWXMessageAsFileResult saveWXMessageAsFile(String openId, String content) throws IOException {
		SaveWXMessageAsFileResult result = new SaveWXMessageAsFileResult();

		String storePrefixPath = weixinConstantMapper.getConstant(WXConstantName.messageStorePrefixPath);
		if (StringUtils.isBlank(storePrefixPath)) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}

		String fileName = openId + "_" + System.currentTimeMillis() + ".txt";
		File mainFolder = new File(storePrefixPath);
		String finalFilePath = storePrefixPath + "/" + openId + "/" + fileName;

		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.fillWithResult(ResultTypeCX.serviceError);
				return result;
			}
		}

		if (StringUtils.isBlank(content) || content.replaceAll("\\s", "").length() < 6) {
			result.fillWithResult(ResultTypeCX.articleTooShort);
			return result;
		}

		List<String> lines = Arrays.asList(content.split(System.lineSeparator()));
		StringBuffer sb = new StringBuffer();

		for (String line : lines) {
			sb.append(StringEscapeUtils.escapeHtml(line));
		}

		String articleContentAfterTrim = sb.toString().trim();

		try {
			ioUtil.byteToFile(articleContentAfterTrim.getBytes("utf8"), finalFilePath);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.fillWithResult(ResultTypeCX.errorWhenArticleSave);
			return result;
		}

		result.setFilePath(finalFilePath);

		result.setIsSuccess();
		return result;
	}

	public void loadWXMessageFromFiles(String storePrefixPath, String openId) {
		File mainFolder = new File(storePrefixPath);
		String targetFilePath = storePrefixPath + "/" + openId + "/";
		File targetFolder = new File(targetFilePath);

		if (!mainFolder.exists() || !targetFolder.exists() || targetFolder.isFile()) {
			// TODO
			// service error
		}

	}

	private WXMessageBO buildWXMessageByJson(JSONObject json) {
		WXMessageBO bo = new WXMessageBO();
		bo.setMsgType(json.getString("MsgType"));
		WXMsgType msgType = WXMsgType.getType(json.getString("MsgType"));

		bo.setToUserName(json.getString("ToUserName"));
		bo.setFromUserName(json.getString("FromUserName"));
		bo.setMsgId(json.getString("MsgId"));
		if (json.containsKey("CreateTime") && NumericUtilCustom.matchInteger(json.getString("CreateTime"))) {
			bo.setCreateTime(json.getLong("CreateTime"));
		}

		if (msgType != null) {
			if (msgType.equals(WXMsgType.text)) {
				bo.setContent(json.getString("Content"));
				
			} else if (msgType.equals(WXMsgType.image)) {
				bo.setPicUrl(json.getString("PicUrl"));
				
			} else if (msgType.equals(WXMsgType.voice)) {
				bo.setMediaId(json.getString("MediaId"));
				bo.setFormat(json.getString("Format"));
				
			} else if (msgType.equals(WXMsgType.video)) {
				bo.setMediaId(json.getString("MediaId"));
				bo.setThumbMediaId(json.getString("ThumbMediaId"));
				
			} else if (msgType.equals(WXMsgType.location)) {
				if (json.containsKey("Location_X")
						&& NumericUtilCustom.matchSimpleNumber(json.getString("Location_X"))) {
					bo.setLocation_X(new BigDecimal(json.getString("Location_X")));
				}
				if (json.containsKey("Location_Y")
						&& NumericUtilCustom.matchSimpleNumber(json.getString("Location_Y"))) {
					bo.setLocation_Y(new BigDecimal(json.getString("Location_Y")));
				}
				if (json.containsKey("Scale") && NumericUtilCustom.matchSimpleNumber(json.getString("Scale"))) {
					bo.setScale(new BigDecimal(json.getString("Scale")));
				}
				bo.setLabel(json.getString("Label"));
				
			} else if (msgType.equals(WXMsgType.link)) {
				bo.setTitle(json.getString("Title"));
				bo.setDescription(json.getString("Description"));
				bo.setUrl(json.getString("Url"));
				
			} else if(msgType.equals(WXMsgType.event)){
				bo.setEvent(json.getString("Event"));
				bo.setEventKey(json.getString("EventKey"));
				
			}
		}

		return bo;
	}
}