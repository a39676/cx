package demo.article.article.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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
import demo.article.article.service.ArticleService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.pojo.type.ResultTypeCX;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class ArticleBurnServiceImpl extends ArticleCommonService implements ArticleBurnService {
	
	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleBurnMapper articleBurnMapper;
	
	private String getArticleBurnStorePrefixPath() {
		return constantService.getValByName(SystemConstantStore.articleBurnStorePrefixPath);
	}
	
	private Long loadMaxArticleLength() {
		Long maxArticleLength = 0L;
		try {
			String maxLengthStr = constantService.getValByName(SystemConstantStore.maxArticleLength);
			if(maxLengthStr != null) {
				maxArticleLength = Long.parseLong(maxLengthStr);
			}
		} catch (Exception e) {
			return maxArticleLength;
		}
		
		return maxArticleLength;
		
	}
	
	@Override
	public CreatingBurnMessageResult creatingBurnMessage(CreatingBurnMessageDTO dto) {
		CreatingBurnMessageResult r = new CreatingBurnMessageResult();
		if(StringUtils.isBlank(dto.getContent()) || dto.getContent().length() > loadMaxArticleLength()) {
			r.fillWithResult(ResultTypeCX.errorParam);
			return r;
		}
		PolicyFactory filter = textFilter.getFilter();
		String contentAfterSanitize = filter.sanitize(dto.getContent());
		Long userId = baseUtilCustom.getUserId();
		Long newArticleId = snowFlake.getNextId();
		
		ArticleFileSaveResult saveArticleFileResult = null;
		try {
			saveArticleFileResult = articleService.saveArticleFile(getArticleBurnStorePrefixPath(), userId, contentAfterSanitize);
		} catch (Exception e) {
			r.failWithMessage("保存信息异常");
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
		
		r.setReadKey(encryptId(po.getReadId()));
		r.setBurnKey(encryptId(po.getBurnId()));
		r.setReadUri(ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.readBurningMessage + "?readKey=" + r.getReadKey());
		r.setBurnUri(ArticleBurnUrlConstant.root + ArticleBurnUrlConstant.burnMessage + "?burnKey=" + r.getBurnKey());
		r.setIsSuccess();
		
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

		Long readId = decryptPrivateKey(readKey);
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
		r.setBurnKey(encryptId(po.getBurnId()));
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
		
		Long burnId = decryptPrivateKey(burnKey);
		if(burnId != null) {
			articleBurnMapper.burnArticleByBurnId(burnId);
		}
	}

}
