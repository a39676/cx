package demo.article.article.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.searchingDemo.pojo.constant.SearchingDemoConstant;
import demo.article.article.mapper.ArticleBurnMapper;
import demo.article.article.pojo.constant.ArticleBurnUrlConstant;
import demo.article.article.pojo.constant.ArticleViewConstant;
import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.dto.ReadBurningMessageByPwdDTO;
import demo.article.article.pojo.po.ArticleBurn;
import demo.article.article.pojo.po.ArticleBurnExample;
import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.result.CreatingBurnMessageResult;
import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.service.ArticleBurnService;
import demo.base.system.pojo.constant.SystemRedisKey;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleBurnServiceImpl extends ArticleCommonService implements ArticleBurnService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private ArticleBurnMapper articleBurnMapper;

	private String getArticleBurnStorePrefixPath() {
		return articleOptionService.getArticleBurnStorePrefixPath();
	}

	@Override
	public ModelAndView articleBurnLink(HttpServletRequest request) {
		if (systemOptionService.isDev() || hostnameService.isMainHostname(request)) {
			return new ModelAndView(ArticleViewConstant.articleBurnLink);
		}
		return null;
	}

	@Override
	public CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto, HttpServletRequest request) {
		CreatingBurnMessageResult r = new CreatingBurnMessageResult();

		if (!systemOptionService.isDev() && !hostnameService.isMainHostname(request)) {
			return null;
		}

		if (StringUtils.isBlank(dto.getContent())
				|| dto.getContent().length() > articleOptionService.getMaxArticleLength()) {
			r.setMessage("Error param");
			return r;
		}

		int count = 0;
		if (!isBigUser()) {
			count = redisOriginalConnectService.checkFunctionalModuleVisitData(request,
					SystemRedisKey.articleBurnInsertCountingKeyPrefix);
		}
		if (!"dev".equals(systemOptionService.getEnvName())) {
			if (count >= SearchingDemoConstant.maxInsertCountIn30Minutes) {
				r.failWithMessage("短时间内加入的任务太多了, 请稍后再试");
				return r;
			}
		}

		String contentAfterSanitize = sanitize(dto.getContent());
		Long newArticleId = snowFlake.getNextId();

		ArticleFileSaveResult saveArticleFileResult = null;
		try {
			saveArticleFileResult = saveArticleFile(getArticleBurnStorePrefixPath(), contentAfterSanitize);
		} catch (Exception e) {
			r.failWithMessage("保存信息异常");
			return r;
		}

		if (!saveArticleFileResult.isSuccess()) {
			r.setMessage(saveArticleFileResult.getMessage());
			return r;
		}

		ArticleBurn po = new ArticleBurn();
		po.setArticleId(newArticleId);
		Long readId = snowFlake.getNextId();
		Long burnId = snowFlake.getNextId();
		po.setReadId(readId);
		po.setBurnId(burnId);
		po.setFilePath(saveArticleFileResult.getFilePath());

		if (StringUtils.isNotBlank(dto.getPwd())) {
			po.setMd5hash(DigestUtils.md5Hex(dto.getPwd()).toUpperCase());
		}

		if (dto.getReadLimit() != null) {
			if (dto.getReadLimit() > 1) {
				po.setReadLimit(dto.getReadLimit());
			}
			if (dto.getReadLimit() > 10) { // 暂定最多阅读10次
				po.setReadLimit(10);
			}
		} else {
			po.setReadLimit(3);
		}

		LocalDateTime now = LocalDateTime.now();
		int validMinute = 30;
		if (dto.getValidTime() != null && dto.getValidTime() > 30 && dto.getValidTime() < 4320) {
			validMinute = dto.getValidTime();
		}
		po.setValidTime(now.plusMinutes(validMinute));
		articleBurnMapper.insertSelective(po);

		try {
			r.setReadKey(URLEncoder.encode(systemOptionService.encryptId(po.getReadId()),
					StandardCharsets.UTF_8.toString()));
			r.setBurnKey(URLEncoder.encode(systemOptionService.encryptId(po.getBurnId()),
					StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
		}
		r.setReadUri(
				ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.readBurningMessage + "?readKey=" + r.getReadKey());
		r.setBurnUri(ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.burnMessage + "?burnKey=" + r.getBurnKey());
		r.setIsSuccess();
		redisOriginalConnectService.insertFunctionalModuleVisitData(request,
				SystemRedisKey.articleBurnInsertCountingKeyPrefix);

		return r;
	}

	@Override
	public ModelAndView readBurningMessage(String readKey) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.readBurningMessage);
		ArticleBurnResult result = findArticleByReadKey(readKey, null);
		view.addObject("content", result.getContent());
		view.addObject("needPwd", result.getNeedPwd());
		if (result.getNeedPwd()) {
			view.addObject("readKey", readKey);
		}
		if (result.isSuccess()) {
			view.addObject("burnUri", result.getBurnUri());
		}
		return view;
	}

	@Override
	public ArticleBurnResult getBurningMessageByReadKeyAndPwd(ReadBurningMessageByPwdDTO dto) {
		return findArticleByReadKey(dto.getReadKey(), dto.getPwd());
	}

	private ArticleBurnResult findArticleByReadKey(String readKey, String pwd) {
		if (StringUtils.isBlank(readKey)) {
			return fillArticleBurnResultWithErrorMessage(null);
		}

		Long readId = null;
		try {
			readId = systemOptionService.decryptPrivateKey(readKey);
			if (readId == null) {
				return fillArticleBurnResultWithErrorMessage(null);
			}
		} catch (Exception e) {
			return fillArticleBurnResultWithErrorMessage(null);
		}
		ArticleBurnExample example = new ArticleBurnExample();
		example.createCriteria().andIsBurnedEqualTo(false).andValidTimeGreaterThan(LocalDateTime.now())
				.andReadIdEqualTo(readId);
		List<ArticleBurn> poList = articleBurnMapper.selectByExample(example);

		if (poList == null || poList.size() != 1) {
			return fillArticleBurnResultWithErrorMessage(null);
		}

		ArticleBurn po = poList.get(0);

		if (po.getReadLimit() <= po.getReadCount()) {
			po.setIsBurned(true);
			articleBurnMapper.updateByPrimaryKeySelective(po);
			return fillArticleBurnResultWithErrorMessage(null);
		}

		if (po.getMd5hash() != null) {
			ArticleBurnResult r = fillArticleBurnResultWithErrorMessage("-");
			r.setNeedPwd(true);
			r.setReadCount(po.getReadCount());
			r.setReadLimit(po.getReadLimit());
			if (pwd == null) {
				return r;
			} else {
				String inputPwdMd5 = DigestUtils.md5Hex(pwd).toUpperCase();
				if (!po.getMd5hash().equals(inputPwdMd5)) {
					r.setContent("请输入正确密码");
					return r;
				}
			}
		}

		if (po.getReadCount() < po.getReadLimit() - 1) {
			po.setReadCount(po.getReadCount() + 1);
			articleBurnMapper.updateByPrimaryKeySelective(po);
			return fillArticleBurnResultWithPO(po);

		} else if (po.getReadCount() == po.getReadLimit() - 1) {
			po.setReadCount(po.getReadCount() + 1);
			burnMessageAndDeleteFile(po);
			return fillArticleBurnResultWithPO(po);
		}

		return fillArticleBurnResultWithErrorMessage(null);
	}

	private ArticleBurnResult fillArticleBurnResultWithPO(ArticleBurn po) {
		ArticleBurnResult r = new ArticleBurnResult();
		try {
			r.setBurnKey(URLEncoder.encode(systemOptionService.encryptId(po.getBurnId()),
					StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		r.setContent(ioUtil.getStringFromFile(po.getFilePath()));
		r.setReadCount(po.getReadCount());
		r.setReadLimit(po.getReadLimit());
		r.setBurnUri(ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.burnMessage + "?burnKey=" + r.getBurnKey());
		r.setIsSuccess();
		return r;
	}

	private ArticleBurnResult fillArticleBurnResultWithErrorMessage(String msg) {
		ArticleBurnResult r = new ArticleBurnResult();
		if (StringUtils.isBlank(msg)) {
			r.setContent("信息已过期");
		} else {
			r.setContent(msg);
		}
		return r;
	}

	@Override
	public void burnArticleByBurnKey(String burnKey) {
		if (StringUtils.isBlank(burnKey)) {
			return;
		}

		Long burnId = systemOptionService.decryptPrivateKey(burnKey);
		if (burnId == null) {
			return;
		}
		
		ArticleBurnExample example = new ArticleBurnExample();
		example.createCriteria().andBurnIdEqualTo(burnId).andIsBurnedEqualTo(false);
		List<ArticleBurn> poList = articleBurnMapper.selectByExample(example);
		
		if(poList != null && !poList.isEmpty()) {
			for(ArticleBurn po : poList ) {
				burnMessageAndDeleteFile(po);
			}
		}
		
	}
	
	private void burnMessageAndDeleteFile(ArticleBurn po) {
		File tmpFile = new File(po.getFilePath());
		try {
			tmpFile.delete();
		} catch (Exception e) {
		}
		po.setIsBurned(true);
		articleBurnMapper.updateByPrimaryKeySelective(po);
	}

	@Override
	public void cleanExpiredArticleBurn() {
		List<ArticleBurn> poList = articleBurnMapper.findExpiredArticleBurn(LocalDateTime.now().plusMinutes(20));
		if (poList == null || poList.size() < 1) {
			return;
		}

		File tmpFile = null;
		List<Long> poIdList = new ArrayList<Long>();
		for (ArticleBurn po : poList) {
			tmpFile = new File(po.getFilePath());
			if (tmpFile.exists()) {
				try {
					tmpFile.deleteOnExit();
				} catch (Exception e) {

				}
				if (!tmpFile.exists()) {
					poIdList.add(po.getArticleId());
				}
			}
		}

		if (poIdList == null || poIdList.isEmpty()) {
			return;
		}

		articleBurnMapper.batchDeleteById(poIdList);

	}
}
