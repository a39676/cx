package demo.article.article.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import autoTest.testEvent.searchingDemo.pojo.constant.SearchingDemoConstant;
import demo.article.article.mapper.ArticleBurnMapper;
import demo.article.article.pojo.constant.ArticleBurnUrlConstant;
import demo.article.article.pojo.constant.ArticleViewConstant;
import demo.article.article.pojo.dto.CreatingBurnMessageDTO;
import demo.article.article.pojo.po.ArticleBurn;
import demo.article.article.pojo.po.ArticleBurnExample;
import demo.article.article.pojo.result.ArticleBurnResult;
import demo.article.article.pojo.result.CreatingBurnMessageResult;
import demo.article.article.pojo.result.jsonRespon.ArticleFileSaveResult;
import demo.article.article.service.ArticleBurnService;
import demo.base.system.pojo.constant.SystemRedisKey;
import demo.common.pojo.type.ResultTypeCX;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleBurnServiceImpl extends ArticleCommonService implements ArticleBurnService {
	
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private ArticleBurnMapper articleBurnMapper;
	
	private String getArticleBurnStorePrefixPath() {
		return articleConstantService.getArticleBurnStorePrefixPath();
	}
	
	@Override
	public ModelAndView articleBurnLink(HttpServletRequest request) {
		if(systemConstantService.isInZhang3OrDev(request)) {
			return new ModelAndView(ArticleViewConstant.articleBurnLink);
		}
		return null;
	}
	
	@Override
	public CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto, HttpServletRequest request) {
		CreatingBurnMessageResult r = new CreatingBurnMessageResult();
		
		if(!systemConstantService.isInZhang3OrDev(request)) {
			return null;
		}

		if(StringUtils.isBlank(dto.getContent()) || dto.getContent().length() > articleConstantService.getMaxArticleLength()) {
			r.fillWithResult(ResultTypeCX.errorParam);
			return r;
		}
		
		int count = 0;
		if(!isBigUser()) {
			count = redisOriginalConnectService.checkFunctionalModuleVisitData(request, SystemRedisKey.articleBurnInsertCountingKeyPrefix);
		}
		if (!"dev".equals(systemConstantService.getEnvName())) {
			if (count >= SearchingDemoConstant.maxInsertCountIn30Minutes) {
				r.failWithMessage("短时间内加入的任务太多了, 请稍后再试");
				return r;
			}
		}
		
		String contentAfterSanitize = sanitize(dto.getContent());
		Long userId = baseUtilCustom.getUserId();
		Long newArticleId = snowFlake.getNextId();
		
		ArticleFileSaveResult saveArticleFileResult = null;
		try {
			saveArticleFileResult = saveArticleFile(getArticleBurnStorePrefixPath(), userId, contentAfterSanitize);
		} catch (Exception e) {
			r.failWithMessage("保存信息异常");
			return r;
		}
		
		if(!saveArticleFileResult.isSuccess()) {
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
			r.setReadKey(URLEncoder.encode(systemConstantService.encryptId(po.getReadId()), StandardCharsets.UTF_8.toString()));
			r.setBurnKey(URLEncoder.encode(systemConstantService.encryptId(po.getBurnId()), StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
		}
		r.setReadUri(ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.readBurningMessage + "?readKey=" + r.getReadKey());
		r.setBurnUri(ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.burnMessage + "?burnKey=" + r.getBurnKey());
		r.setIsSuccess();
		redisOriginalConnectService.insertFunctionalModuleVisitData(request, SystemRedisKey.articleBurnInsertCountingKeyPrefix);
		
		return r;
	}

	@Override
	public ModelAndView readBurningMessage(String readKey) {
		ModelAndView view = new ModelAndView(ArticleViewConstant.readBurningMessage);
		ArticleBurnResult result = findArticleByReadKey(readKey);
		view.addObject("content", result.getContent());
		if(result.isSuccess()) {
			view.addObject("remainingReadCount", result.getReadLimit() - result.getReadCount() - 1);
			view.addObject("burnUri", ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.burnMessage + "?burnKey=" + result.getBurnKey());
		}
		return view;
	}
	
	private ArticleBurnResult findArticleByReadKey(String readKey) {
		if (StringUtils.isBlank(readKey)) {
			return fillArticleBurnResultWithErrorMessage();
		}

		Long readId = systemConstantService.decryptPrivateKey(readKey);
		ArticleBurnExample example = new ArticleBurnExample();
		example.createCriteria()
		.andIsBurnedEqualTo(false)
		.andValidTimeGreaterThan(LocalDateTime.now())
		.andReadIdEqualTo(readId);
		List<ArticleBurn> poList = articleBurnMapper.selectByExample(example);

		if (poList == null || poList.size() != 1) {
			return fillArticleBurnResultWithErrorMessage();
		}
		
		ArticleBurn po = poList.get(0);

		if (po.getReadLimit() <= po.getReadCount()) {
			articleBurnMapper.burnArticleById(po.getArticleId());
			return fillArticleBurnResultWithErrorMessage();
		}

		if (po.getReadCount() < po.getReadLimit() - 1) {
			articleBurnMapper.readCountPlus(po.getArticleId());
			return fillArticleBurnResultWithPO(po);
		} else if (po.getReadCount() == po.getReadLimit() - 1) {
			articleBurnMapper.lastRead(po.getArticleId());
			return fillArticleBurnResultWithPO(po);
		}

		return fillArticleBurnResultWithErrorMessage();
	}
	
	private ArticleBurnResult fillArticleBurnResultWithPO(ArticleBurn po) {
		ArticleBurnResult r = new ArticleBurnResult();
		try {
			r.setBurnKey(URLEncoder.encode(systemConstantService.encryptId(po.getBurnId()), StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		r.setContent(ioUtil.getStringFromFile(po.getFilePath()));
		r.setReadCount(po.getReadCount());
		r.setReadLimit(po.getReadLimit());
		r.setIsSuccess();
		return r;
	}
	
	private ArticleBurnResult fillArticleBurnResultWithErrorMessage() {
		return fillArticleBurnResultWithErrorMessage(null);
	}
	
	private ArticleBurnResult fillArticleBurnResultWithErrorMessage(String msg) {
		ArticleBurnResult r = new ArticleBurnResult();
		if(StringUtils.isBlank(msg)) {
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
		
		Long burnId = systemConstantService.decryptPrivateKey(burnKey);
		if(burnId != null) {
			articleBurnMapper.burnArticleByBurnId(burnId);
		}
	}

	@Override
	public void cleanExpiredArticleBurn() {
		List<ArticleBurn> poList = articleBurnMapper.findExpiredArticleBurn(LocalDateTime.now().plusMinutes(20));
		if(poList == null || poList.size() < 1) {
			return;
		}
		
		File tmpFile = null;
		List<Long> poIdList = new ArrayList<Long>();
		for(ArticleBurn po : poList) {
			tmpFile = new File(po.getFilePath());
			if(tmpFile.exists()) {
				try {
					tmpFile.deleteOnExit();
				} catch (Exception e) {
					
				}
				if(!tmpFile.exists()) {
					poIdList.add(po.getArticleId());
				}
			}
		}
		
		articleBurnMapper.batchDeleteById(poIdList);
		
	}
}
