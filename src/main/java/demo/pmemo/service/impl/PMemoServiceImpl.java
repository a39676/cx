package demo.pmemo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.service.ArticleService;
import demo.article.article.service.impl.ArticleCommonService;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.pmemo.mapper.PNoteMapper;
import demo.pmemo.pojo.constant.PMemoConstant;
import demo.pmemo.pojo.dto.EditPNoteDTO;
import demo.pmemo.pojo.dto.SetPMemoDTO;
import demo.pmemo.pojo.po.PNote;
import demo.pmemo.pojo.po.PNoteExample;
import demo.pmemo.service.PMemoService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class PMemoServiceImpl extends ArticleCommonService implements PMemoService {

	@Autowired
	private PNoteMapper noteMapper;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private FileUtilCustom ioUtil;
	
	@Override
	public String getMemo(String key) {
		if(StringUtils.isBlank(key)) {
			return "";
		}
		
		return constantService.getValByName(PMemoConstant.P_MEMO_REDIS_KEY + key);
	}
	
	@Override
	public CommonResult setMemo(SetPMemoDTO dto) {
		/*
		 * 插入之前, 删除其他memo?
		 */
		CommonResult r = new CommonResult();
		
		if(StringUtils.isAnyBlank(dto.getContent(), dto.getRedisKeyValue())) {
			r.failWithMessage("null param");
			return r;
		}
		
		if(dto.getContent().length() > PMemoConstant.MEMO_MAX_SIZE) {
			r.failWithMessage("too large");
			return r;
		}
		
		Long validSeconds = null;
		try {
			LocalDateTime validDateTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getValidDateStr());
			validSeconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), validDateTime);
			if(validDateTime.isBefore(LocalDateTime.now())) {
				throw new Exception();
			}
		} catch (Exception e) {
			validSeconds = PMemoConstant.DEFAULT_VALID_SECONDS;
		}
		
		constantService.setValByName(PMemoConstant.P_MEMO_REDIS_KEY + dto.getRedisKeyValue(), dto.getContent(), validSeconds, TimeUnit.SECONDS);
		
		r.setIsSuccess();
		return r;
	}
	
	public void cleanMemo() {
		/*
		 *  TODO
		 *  未加入 security url 鉴定权限
		 *  提供删除其他 memo? 
		 */
	}

	@Override
	public CommonResult editPNote(EditPNoteDTO dto) {
		CommonResult r = new CommonResult();
		
		if(StringUtils.isNotBlank(dto.getContent())) {
			dto.setContent(sanitize(dto.getContent()));
		}
		
		Long userId = baseUtilCustom.getUserId();
		PNote po = findPNote(userId);
		
		boolean createNewFlag = (po.getPath() == null);
		if(createNewFlag) {
			po.setPath(getPNoteStorePrefixPath() + "/" + snowFlake.getNextId());
		}
		
		try {
			ArticleFileSaveResult pNoteSaveResult = editPNote(po.getPath(), sanitize(dto.getContent()));
			if(pNoteSaveResult.isFail()) {
				r.addMessage(pNoteSaveResult.getMessage());
				return r;
			}
			
		} catch (IOException e) {
			r.addMessage("pNote save error");
			return r;
		}
		
		if(createNewFlag) {
			po.setEditCount(0);
			noteMapper.insertSelective(po);
		} else {
			po.setEditCount(po.getEditCount() + 1);
			po.setEditTime(LocalDateTime.now());
			noteMapper.updateByPrimaryKeySelective(po);
		}
		
		r.setIsSuccess();
		return r;
	}
	
	private ArticleFileSaveResult editPNote(String finalFilePath, String content) throws IOException {
		content = sanitize(content);
		String storePrefixPath = getPNoteStorePrefixPath();
		ArticleFileSaveResult result = new ArticleFileSaveResult();

		File mainFolder = new File(storePrefixPath);
		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.fillWithResult(ResultTypeCX.serviceError);
				return result;
			}
		}
		
		Element doc = Jsoup.parse(content);
        Elements imgs = doc.select("img[src]");
        /* 解决如果文章内有本地上传的图片, 转到服务器硬盘保存, 并提供 url 访问, */
        for(Element s : imgs) {
        	s.attr("src", articleService.imgSrcHandler(s.attr("src")));
        }
        
        content = doc.toString();
        
		Long maxNoteLength = PMemoConstant.MEMO_MAX_SIZE;
		
		if (content.length() > maxNoteLength) {
			result.failWithMessage("note too long");
			return result;
		}

		StringBuffer sb = new StringBuffer();
		
		sb.append(content);

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		result.setFilePath(finalFilePath);

		result.setIsSuccess();
		return result;
	}
	
	private String getPNoteStorePrefixPath() {
		if(isWindows()) {
			return PMemoConstant.P_NOTE_SAVING_FOLDER;
		} else {
			return "d:/" + PMemoConstant.P_NOTE_SAVING_FOLDER;
		}
	}
	
	private PNote findPNote(Long userId) {
		PNoteExample example = new PNoteExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<PNote> poList = noteMapper.selectByExample(example);
		if(poList == null || poList.isEmpty()) {
			return poList.get(0);
		} else {
			PNote po = new PNote();
			po.setId(snowFlake.getNextId());
			po.setUserId(userId);
			
			return po;
		}
	}
	
}