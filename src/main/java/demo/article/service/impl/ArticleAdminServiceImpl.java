package demo.article.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.article.mapper.ArticleChannelsMapper;
import demo.article.mapper.ArticleHotMapper;
import demo.article.mapper.ArticleLongMapper;
import demo.article.mapper.ArticleLongReviewMapper;
import demo.article.mapper.ArticleLongSummaryMapper;
import demo.article.mapper.ArticleUserDetailMapper;
import demo.article.pojo.bo.ArticleUUIDChannelStoreBO;
import demo.article.pojo.constant.ArticleConstant;
import demo.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.pojo.param.controllerParam.ChangeChannelParam;
import demo.article.pojo.param.controllerParam.InsertNewReviewRecordParam;
import demo.article.pojo.param.controllerParam.ReviewArticleLongParam;
import demo.article.pojo.param.controllerParam.SetArticleHotParam;
import demo.article.pojo.param.mapperParam.FindArticleChannelsParam;
import demo.article.pojo.param.mapperParam.FindArticleLongParam;
import demo.article.pojo.param.mapperParam.UpdateArticleLongReviewStatuParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserCoefficientParam;
import demo.article.pojo.param.mapperParam.UpdateChannelPointByArticleIdParam;
import demo.article.pojo.po.ArticleChannels;
import demo.article.pojo.po.ArticleHot;
import demo.article.pojo.po.ArticleLong;
import demo.article.pojo.po.ArticleLongSummary;
import demo.article.pojo.type.ArticleReviewType;
import demo.article.pojo.vo.ArticleChannelVO;
import demo.article.service.ArticleAdminService;
import demo.article.service.ArticleChannelService;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.baseCommon.pojo.type.ResultTypeCX;
import demo.image.service.ImageService;
import demo.util.BaseUtilCustom;

@Service
public class ArticleAdminServiceImpl extends ArticleCommonService implements ArticleAdminService {

	@Autowired
	private BaseUtilCustom baseUtilCustom;
	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleChannelsMapper articleChannelsMapper;
	@Autowired
	private ArticleLongSummaryMapper articleLongSummaryMapper;
	@Autowired
	private ArticleUserDetailMapper articleUserDetailMapper;
	@Autowired
	private ArticleLongReviewMapper articleLongReviewMapper;
	@Autowired
	private ArticleHotMapper articleHotMapper;
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private ArticleChannelService channelService;
	
	
	
	static {{

	}}
	
	@Override
	public CommonResultCX batchUpdatePrivateKey(BatchUpdatePrimaryKeyParam param) {
		CommonResultCX result = new CommonResultCX();
		if(!loadCustomKey()) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
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
		List<List<Character>> keys = getCustomKey();
		for(Long id : articleLongIds) {
			tmpPrivateKey = encryptArticleId(id, keys);
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
		Long articleId = decryptArticlePrivateKey(privateKey);
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		if(articleLongReviewMapper.wasReview(articleId, ArticleReviewType.pass.getReviewCode()) == 1) {
			result.fillWithResult(ResultTypeCX.articleWasPass);
			return result;
		}
		
		FindArticleLongParam param = new FindArticleLongParam();
		param.setArticleId(articleId);
		ArticleLong article = articleLongMapper.findArticleLong(param);
		if(article == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		UpdateArticleLongReviewStatuParam updateArticleReviewStatuParam = new UpdateArticleLongReviewStatuParam();
		updateArticleReviewStatuParam.setArticleId(articleId);
		updateArticleReviewStatuParam.setPass(true);
		int updateCount = articleLongMapper.updateArticleLongReviewStatu(updateArticleReviewStatuParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		UpdateArticleUserCoefficientParam updateUserCoefficientParam = new UpdateArticleUserCoefficientParam();
		updateUserCoefficientParam.setChannelId(article.getChannelId());
		updateUserCoefficientParam.setCoefficient(ArticleConstant.passArticleCoefficient);
		updateUserCoefficientParam.setUserId(article.getUserId());
		updateCount = articleUserDetailMapper.updateArticleUserCoefficient(updateUserCoefficientParam);
		if(updateCount != 1) {
			throw new Exception();
		}
		
		UpdateChannelPointByArticleIdParam updateChannelPointParam = new UpdateChannelPointByArticleIdParam();
		updateChannelPointParam.setArticleId(articleId);
		updateChannelPointParam.setChannelPoint(ArticleConstant.passArticleCoefficient);
		updateCount = articleChannelsMapper.updateChannelPointByArticleId(updateChannelPointParam);
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
		
		imageService.moveImageCacheToImageStore(articleId, article.getChannelId());
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}
	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	private CommonResultCX rejectArticle(String privateKey) throws Exception {
		CommonResultCX result = new CommonResultCX();
		
		Long articleId = decryptArticlePrivateKey(privateKey);
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		if(articleLongReviewMapper.wasReview(articleId, ArticleReviewType.pass.getReviewCode()) == 1) {
			result.fillWithResult(ResultTypeCX.articleWasReject);
			return result;
		}
		
		FindArticleLongParam param = new FindArticleLongParam();
		param.setArticleId(articleId);
		ArticleLong article = articleLongMapper.findArticleLong(param);
		if(article == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		UpdateArticleLongReviewStatuParam updateArticleReviewStatuParam = new UpdateArticleLongReviewStatuParam();
		updateArticleReviewStatuParam.setArticleId(articleId);
		updateArticleReviewStatuParam.setReject(true);
		int updateCount = articleLongMapper.updateArticleLongReviewStatu(updateArticleReviewStatuParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		UpdateArticleUserCoefficientParam updateUserCoefficientParam = new UpdateArticleUserCoefficientParam();
		updateUserCoefficientParam.setChannelId(article.getChannelId());
		updateUserCoefficientParam.setCoefficient(ArticleConstant.rejectArticleCoefficient);
		updateUserCoefficientParam.setUserId(article.getUserId());
		updateCount = articleUserDetailMapper.updateArticleUserCoefficient(updateUserCoefficientParam);
		if(updateCount != 1) {
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
		Long articleId = decryptArticlePrivateKey(privateKey);
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		if(articleLongReviewMapper.wasReview(articleId, ArticleReviewType.delete.getReviewCode()) == 1) {
			result.fillWithResult(ResultTypeCX.articleWasDelete);
			return result;
		}
		
		FindArticleLongParam param = new FindArticleLongParam();
		param.setArticleId(articleId);
		ArticleLong article = articleLongMapper.findArticleLong(param);
		if(article == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}
		
		UpdateArticleLongReviewStatuParam updateArticleReviewStatuParam = new UpdateArticleLongReviewStatuParam();
		updateArticleReviewStatuParam.setArticleId(articleId);
		updateArticleReviewStatuParam.setDelete(true);
		int updateCount = articleLongMapper.updateArticleLongReviewStatu(updateArticleReviewStatuParam);
		if(updateCount == 0) {
			throw new Exception();
		}
		
		UpdateArticleUserCoefficientParam updateUserCoefficientParam = new UpdateArticleUserCoefficientParam();
		updateUserCoefficientParam.setChannelId(article.getChannelId());
		updateUserCoefficientParam.setCoefficient(0 - ArticleConstant.passArticleCoefficient);
		updateUserCoefficientParam.setUserId(article.getUserId());
		updateCount = articleUserDetailMapper.updateArticleUserCoefficient(updateUserCoefficientParam);
		if(updateCount != 1) {
			throw new Exception();
		}
		
		UpdateChannelPointByArticleIdParam updateChannelPointParam = new UpdateChannelPointByArticleIdParam();
		updateChannelPointParam.setArticleId(articleId);
		updateChannelPointParam.setChannelPoint(0 - ArticleConstant.passArticleCoefficient);
		updateCount = articleChannelsMapper.updateChannelPointByArticleId(updateChannelPointParam);
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
	public List<ArticleChannelVO> findChannel(FindArticleChannelsParam param) {
		List<ArticleChannelVO> channelVOList = new ArrayList<ArticleChannelVO>();
		List<ArticleChannels> channelPOList = articleChannelsMapper.findArticleChannels(param);
		ArticleChannelVO tmpChannelVO = null;
		ArticleUUIDChannelStoreBO uuidChannelStore = channelService.getArticleUUIDChannelStore();
		
		for(ArticleChannels channel : channelPOList) {
			tmpChannelVO = new ArticleChannelVO();
			tmpChannelVO.setChannelName(channel.getChannelName());
			tmpChannelVO.setUuid(uuidChannelStore.getUUID(channel.getChannelId()));
			channelVOList.add(tmpChannelVO);
		}
		
		return channelVOList;
	}
	
	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResultCX changeChannel(ChangeChannelParam param) throws Exception {
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(param.getPk()) || StringUtils.isBlank(param.getUuid()) || param.getArticleId() != null || param.getChannelId() != null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		ArticleUUIDChannelStoreBO uuidChannelStore = channelService.getArticleUUIDChannelStore();
		
		Long channelId = uuidChannelStore.getChannelId(param.getUuid());
		
		Long articleId = decryptArticlePrivateKey(param.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		param.setArticleId(articleId);
		param.setChannelId(channelId);
		param.setPk(null);
		param.setUuid(null);
		
		FindArticleLongParam findArticleParam = new FindArticleLongParam();
		findArticleParam.setArticleId(articleId);
		ArticleLong article = articleLongMapper.findArticleLong(findArticleParam);
		int deleteReviewRecordCount = 0;
		if(!article.getIsEdited()) {
			if(article.getIsPass() || article.getIsDelete() || article.getIsReject()) {
				deleteReviewRecordCount = articleLongReviewMapper.deleteReviewRecord(articleId);
			} else {
				deleteReviewRecordCount = 1;
			}
		}
		
		if(deleteReviewRecordCount < 1 || articleLongMapper.changeChannel(param) < 1) {
			throw new Exception();
		}
		
		result.fillWithResult(ResultTypeCX.success);
		return result;
	}
	
	@Override
	public CommonResultCX setArticleHot(SetArticleHotParam controllerParam) {
//		TODO
		CommonResultCX result = new CommonResultCX();
		if(StringUtils.isBlank(controllerParam.getPk()) || controllerParam.getHotMinutes() == null || controllerParam.getHotLevel() == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		
		if(controllerParam.getHotMinutes() < 0 || controllerParam.getHotMinutes() > (60L * 24 * 30) || controllerParam.getHotLevel() < 0 || controllerParam.getHotLevel() > 10) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		Long articleId = decryptArticlePrivateKey(controllerParam.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}
		
		FindArticleLongParam findArticleParam = new FindArticleLongParam();
		findArticleParam.setArticleId(articleId);
		ArticleLong oldArticleLong = articleLongMapper.findArticleLong(findArticleParam);
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
