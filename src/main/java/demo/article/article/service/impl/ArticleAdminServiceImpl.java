package demo.article.article.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.article.article.mapper.ArticleHotMapper;
import demo.article.article.mapper.ArticleLongMapper;
import demo.article.article.mapper.ArticleLongReviewMapper;
import demo.article.article.mapper.ArticleLongSummaryMapper;
import demo.article.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.article.pojo.param.controllerParam.InsertNewReviewRecordParam;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.article.pojo.param.mapperParam.UpdateArticleLongReviewStatuParam;
import demo.article.article.pojo.po.ArticleHot;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.article.pojo.po.ArticleLongSummary;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.article.service.ArticleAdminService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;

@Service
public class ArticleAdminServiceImpl extends ArticleCommonService implements ArticleAdminService {

	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleLongSummaryMapper articleLongSummaryMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;
	@Autowired
	private ArticleHotMapper articleHotMapper;
	
	@Override
	public CommonResultCX batchUpdatePrivateKey(BatchUpdatePrimaryKeyParam param) {
		CommonResultCX result = new CommonResultCX();
		if (param.getStartTime() == null && param.getEndTime() == null) {
			param.setEndTime(new Date());
		}

		if (param.getStartTime() != null && param.getEndTime() != null
				&& (param.getStartTime().after(param.getEndTime())
						|| param.getStartTime().equals(param.getEndTime()))) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		List<Long> articleLongIds = articleLongSummaryMapper.findArticleLongSummaryListIds(param);
		if(articleLongIds == null || articleLongIds.size() < 1) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleLongSummary tmpPo = null;
		List<ArticleLongSummary> summarys = new ArrayList<ArticleLongSummary>();
		String tmpPrivateKey = null;
		for(Long id : articleLongIds) {
			try {
				tmpPrivateKey = URLEncoder.encode(encryptId(id), StandardCharsets.UTF_8.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(tmpPrivateKey == null) {
				continue;
			}
			tmpPo = new ArticleLongSummary();
			tmpPo.setArticleId(id);
			tmpPo.setPrivateKey(tmpPrivateKey);
			summarys.add(tmpPo);
		}
		
		Integer updateCount = articleLongSummaryMapper.batchUpdatePrivateKey(summarys);
		if(updateCount == null || updateCount < 1) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		result.normalSuccess();
		result.setMessage(updateCount.toString());
		return result;
	}
	
	@Override
	public CommonResultCX handelReviewArticle(ReviewArticleLongParam param) throws Exception {
		CommonResultCX result = null;
		if(param.getReviewCode().equals(ArticleReviewType.pass.getReviewCode())) {
			result = passArticle(param.getPk());
			return result;
		} else if(param.getReviewCode().equals(ArticleReviewType.reject.getReviewCode())) {
			result = rejectArticle(param.getPk());
			return result;
		} else if(param.getReviewCode().equals(ArticleReviewType.delete.getReviewCode())) {
			result = deleteArticle(param.getPk());
			return result;
		} else {
			result = new CommonResultCX();
			result.fillWithResult(ResultTypeCX.errorParam);
		}
		return result;
	}
	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	private CommonResultCX passArticle(String privateKey) throws Exception {
		CommonResultCX result = new CommonResultCX();
		Long articleId = decryptPrivateKey(privateKey);
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleLong article = articleLongMapper.selectByPrimaryKey(articleId);
		if(article == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		if(article.getIsPass()) {
			result.fillWithResult(ResultTypeCX.articleWasPass);
			return result;
		}
		
		UpdateArticleLongReviewStatuParam updateArticleReviewStatuParam = new UpdateArticleLongReviewStatuParam();
		updateArticleReviewStatuParam.setArticleId(articleId);
		updateArticleReviewStatuParam.setPass(true);
		int updateCount = articleLongMapper.updateArticleLongReviewStatu(updateArticleReviewStatuParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		InsertNewReviewRecordParam insertNewReviewRecordParam = new InsertNewReviewRecordParam();
		insertNewReviewRecordParam.setArticleId(articleId);
		insertNewReviewRecordParam.setArticleReviewerId(baseUtilCustom.getUserId());
		insertNewReviewRecordParam.setReviewTypeId(ArticleReviewType.pass.getReviewCode());
		updateCount = articleLongReviewMapper.insertNewReviewRecord(insertNewReviewRecordParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}
	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	private CommonResultCX rejectArticle(String privateKey) throws Exception {
		CommonResultCX result = new CommonResultCX();
		
		Long articleId = decryptPrivateKey(privateKey);
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleLong article = articleLongMapper.selectByPrimaryKey(articleId);
		if(article == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		if(article.getIsReject()) {
			result.fillWithResult(ResultTypeCX.articleWasReject);
			return result;
		}
		
		UpdateArticleLongReviewStatuParam updateArticleReviewStatuParam = new UpdateArticleLongReviewStatuParam();
		updateArticleReviewStatuParam.setArticleId(articleId);
		updateArticleReviewStatuParam.setReject(true);
		int updateCount = articleLongMapper.updateArticleLongReviewStatu(updateArticleReviewStatuParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		InsertNewReviewRecordParam insertNewReviewRecordParam = new InsertNewReviewRecordParam();
		insertNewReviewRecordParam.setArticleId(articleId);
		insertNewReviewRecordParam.setArticleReviewerId(baseUtilCustom.getUserId());
		insertNewReviewRecordParam.setReviewTypeId(ArticleReviewType.reject.getReviewCode());
		updateCount = articleLongReviewMapper.insertNewReviewRecord(insertNewReviewRecordParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}
	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public CommonResultCX deleteArticle(String privateKey) throws Exception {
		CommonResultCX result = new CommonResultCX();
		Long articleId = decryptPrivateKey(privateKey);
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleLong article = articleLongMapper.selectByPrimaryKey(articleId);
		if(article == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		if(article.getIsDelete()) {
			result.fillWithResult(ResultTypeCX.articleWasDelete);
			return result;
		}
		
		UpdateArticleLongReviewStatuParam updateArticleReviewStatuParam = new UpdateArticleLongReviewStatuParam();
		updateArticleReviewStatuParam.setArticleId(articleId);
		updateArticleReviewStatuParam.setDelete(true);
		int updateCount = articleLongMapper.updateArticleLongReviewStatu(updateArticleReviewStatuParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		InsertNewReviewRecordParam insertNewReviewRecordParam = new InsertNewReviewRecordParam();
		insertNewReviewRecordParam.setArticleId(articleId);
		insertNewReviewRecordParam.setArticleReviewerId(baseUtilCustom.getUserId());
		insertNewReviewRecordParam.setReviewTypeId(ArticleReviewType.delete.getReviewCode());
		updateCount = articleLongReviewMapper.insertNewReviewRecord(insertNewReviewRecordParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResultCX changeChannel(ChangeChannelParam param) throws Exception {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(param.getPk()) || param.getArticleId() != null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long articleId = decryptPrivateKey(param.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		param.setArticleId(articleId);
		param.setPk(null);
		
		if(articleLongMapper.changeChannel(param) < 1) {
			throw new Exception();
		}
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}
	
	@Override
	public CommonResultCX setArticleHot(SetArticleHotParam controllerParam) {
		/*
		 * TODO
		 * 2019-12-20 发现
		 * what to do?
		 */
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(controllerParam.getPk()) || controllerParam.getHotMinutes() == null || controllerParam.getHotLevel() == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		
		if(controllerParam.getHotMinutes() < 0 || controllerParam.getHotMinutes() > (60L * 24 * 30) || controllerParam.getHotLevel() < 0 || controllerParam.getHotLevel() > 10) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long articleId = decryptPrivateKey(controllerParam.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleLong oldArticleLong = articleLongMapper.selectByPrimaryKey(articleId);
		if(oldArticleLong == null || oldArticleLong.getIsPass() == false || oldArticleLong.getIsDelete() == true || oldArticleLong.getIsReject() == true || oldArticleLong.getIsEdited() == true) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleHot newArticleHot = new ArticleHot();
		newArticleHot.setArticleId(oldArticleLong.getArticleId());
		newArticleHot.setChannelId(oldArticleLong.getChannelId());
		newArticleHot.setHotLevel(controllerParam.getHotLevel());
		Long nowMS = System.currentTimeMillis();
		nowMS = nowMS + (controllerParam.getHotMinutes() * 60 * 1000);
		newArticleHot.setValidTime(new Date(nowMS));
		
		articleHotMapper.insertNew(newArticleHot);
		
		result.fillWithResult(ResultTypeCX.setArticleHotSuccess);
		return result;
	}

}
