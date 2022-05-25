package demo.article.article.service.impl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.article.article.mapper.ArticleLongMapper;
import demo.article.article.mapper.ArticleLongReviewMapper;
import demo.article.article.mapper.ArticleLongSummaryMapper;
import demo.article.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.article.pojo.param.controllerParam.InsertNewReviewRecordParam;
import demo.article.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.article.pojo.param.mapperParam.UpdateArticleLongReviewStatuParam;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.article.pojo.po.ArticleLongSummary;
import demo.article.article.pojo.type.ArticleReviewType;
import demo.article.article.service.ArticleAdminService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;

@Service
public class ArticleAdminServiceImpl extends ArticleCommonService implements ArticleAdminService {

	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleLongSummaryMapper articleLongSummaryMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;
	
	
	@Override
	public CommonResultCX handelReviewArticle(ReviewArticleLongParam param) throws Exception {
		CommonResultCX result = null;
		param.setPk(URLDecoder.decode(param.getPk(), StandardCharsets.UTF_8));
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
	
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	private CommonResultCX passArticle(String privateKey) throws Exception {
		CommonResultCX result = new CommonResultCX();
		Long articleId = systemOptionService.decryptPrivateKey(privateKey);
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
	
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	private CommonResultCX rejectArticle(String privateKey) throws Exception {
		CommonResultCX result = new CommonResultCX();
		
		Long articleId = systemOptionService.decryptPrivateKey(privateKey);
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
	
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	@Override
	public CommonResultCX deleteArticle(String privateKey) throws Exception {
		CommonResultCX result = new CommonResultCX();
		Long articleId = systemOptionService.decryptPrivateKey(privateKey);
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
	@Transactional(value = "cxTransactionManager", rollbackFor = Exception.class)
	public CommonResultCX changeChannel(ChangeChannelParam param) throws Exception {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(param.getPk())) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		param.setPk(URLDecoder.decode(param.getPk(), StandardCharsets.UTF_8));
		Long articleId = systemOptionService.decryptPrivateKey(param.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		
		ArticleLong record = new ArticleLong();
		record.setArticleId(articleId);
		record.setChannelId(param.getChannelId());
		record.setIsPass(isBigUser());
		articleLongMapper.updateByPrimaryKeySelective(record);
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}
	
	@Override
	public CommonResultCX setArticleHot(SetArticleHotParam controllerParam) {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(controllerParam.getPk()) || controllerParam.getHotMinutes() == null || controllerParam.getHotLevel() == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		
		if(controllerParam.getHotMinutes() < 0 || controllerParam.getHotMinutes() > (60L * 24 * 30) || controllerParam.getHotLevel() < 0 || controllerParam.getHotLevel() > 10) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		controllerParam.setPk(URLDecoder.decode(controllerParam.getPk(), StandardCharsets.UTF_8));
		Long articleId = systemOptionService.decryptPrivateKey(controllerParam.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		ArticleLong oldArticleLong = articleLongMapper.selectByPrimaryKey(articleId);
		if(oldArticleLong == null || oldArticleLong.getIsPass() == false || oldArticleLong.getIsDelete() == true || oldArticleLong.getIsReject() == true || oldArticleLong.getIsEdited() == true) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		
		ArticleLongSummary summaryPO = new ArticleLongSummary();
		summaryPO.setArticleId(articleId);
		summaryPO.setIsHot(true);
		summaryPO.setHotLevel(controllerParam.getHotLevel());
		summaryPO.setHotValidTime(LocalDateTime.now().plusMinutes(controllerParam.getHotMinutes()));
		articleLongSummaryMapper.updateByPrimaryKeySelective(summaryPO);
		
		
		result.fillWithResult(ResultTypeCX.setArticleHotSuccess);
		return result;
	}

}
