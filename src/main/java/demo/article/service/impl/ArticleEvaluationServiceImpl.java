package demo.article.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dateTimeHandle.DateUtilCustom;
import demo.article.mapper.ArticleChannelsMapper;
import demo.article.mapper.ArticleEvaluationCacheMapper;
import demo.article.mapper.ArticleEvaluationStoreMapper;
import demo.article.mapper.ArticleLongMapper;
import demo.article.mapper.ArticleUserDetailMapper;
import demo.article.pojo.bo.ArticleEvaluationEstimateBO;
import demo.article.pojo.bo.ArticleEvaluationStatisticsBO;
import demo.article.pojo.bo.ArticleEvaluationStatisticsByArticleIdBO;
import demo.article.pojo.bo.ArticleEvaluationStatisticsTaskBO;
import demo.article.pojo.constant.ArticleConstant;
import demo.article.pojo.param.controllerParam.InsertArticleCommentEvaluationParam;
import demo.article.pojo.param.controllerParam.InsertArticleLongEvaluationParam;
import demo.article.pojo.param.mapperParam.ArticleEvaluationCacheDeleteByIdParam;
import demo.article.pojo.param.mapperParam.ArticleEvaluationCacheUpdateIsDeleteParam;
import demo.article.pojo.param.mapperParam.ArticleEvaluationCacheUpdateWasStatisticsByIdParam;
import demo.article.pojo.param.mapperParam.FindArticleEvaluationCacheListParam;
import demo.article.pojo.param.mapperParam.FindArticleLongParam;
import demo.article.pojo.param.mapperParam.FindEvaluationCacheStatisticsByConditionParam;
import demo.article.pojo.param.mapperParam.FindEvaluationStatisticsByIdListParam;
import demo.article.pojo.param.mapperParam.HasOldEvaluationParam;
import demo.article.pojo.param.mapperParam.InsertEvaluationDaoParam;
import demo.article.pojo.param.mapperParam.UpdateArticleUserCoefficientParam;
import demo.article.pojo.param.mapperParam.UpdateChannelPointParam;
import demo.article.pojo.po.ArticleEvaluationStore;
import demo.article.pojo.po.ArticleLong;
import demo.article.pojo.type.ArticleEvaluationCodeType;
import demo.article.pojo.type.ArticleEvaluationType;
import demo.article.pojo.vo.ArticleEvaluationCounterVO;
import demo.article.pojo.vo.ArticleEvaluationStatisticsVO;
import demo.article.service.ArticleEvaluationService;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import numericHandel.NumericUtilCustom;

@Service
public class ArticleEvaluationServiceImpl extends ArticleCommonService implements ArticleEvaluationService {
	

	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleChannelsMapper articleChannelsMapper;
	@Autowired
	private ArticleUserDetailMapper articleUserDetailMapper;
	@Autowired
	private ArticleEvaluationCacheMapper articleEvaluationCacheMapper;
	@Autowired
	private ArticleEvaluationStoreMapper articleEvaluationStoreMapper;
	
	
	@Override
	public Map<Long, ArticleEvaluationStatisticsVO> findEvaluationStatisticsByArticleId(ArticleEvaluationType evaluationType, List<Long> articleIdList) {
		Map<Long, ArticleEvaluationStatisticsVO> voMap = new HashMap<Long, ArticleEvaluationStatisticsVO>();
		if(articleIdList == null || articleIdList.size() < 1) {
			return voMap;
		}
		
		HashMap<Long, String> encryptIdMap = new HashMap<Long, String>();
		List<List<Character>> keys = getCustomKey();
		articleIdList.stream().forEach(id -> encryptIdMap.put(id, encryptArticleId(id, keys)));
		if(encryptIdMap.size() != articleIdList.size()) {
			return voMap;
		}
		
		FindEvaluationStatisticsByIdListParam findEvaluationStatisticsParam = new FindEvaluationStatisticsByIdListParam();
		findEvaluationStatisticsParam.setEvaluationType(evaluationType.getEvaluationCode());
		findEvaluationStatisticsParam.setPostObjectIdList(articleIdList);
		
		List<ArticleEvaluationStatisticsByArticleIdBO> evaluationStatisticsCacheBOList = articleEvaluationCacheMapper.findEvaluationStatisticsByIdList(findEvaluationStatisticsParam);
		List<ArticleEvaluationStore> evaluationStatisticsStoreList = articleEvaluationStoreMapper.findEvaluationStatisticsByIdList(findEvaluationStatisticsParam);
		
		ArticleEvaluationStatisticsVO tmpVO = null;
		for(ArticleEvaluationStatisticsByArticleIdBO bo : evaluationStatisticsCacheBOList) {
			if(voMap.containsKey(bo.getPostObjectId())) {
				tmpVO = voMap.get(bo.getPostObjectId());
				tmpVO.addEvaluationCount(bo.getEvaluationCode(), bo.getEvaluationCount());
			} else {
				tmpVO = new ArticleEvaluationStatisticsVO();
				tmpVO.setPk(encryptIdMap.get(bo.getPostObjectId()));
				tmpVO.addEvaluationCount(bo.getEvaluationCode(), bo.getEvaluationCount());
				voMap.put(bo.getPostObjectId(), tmpVO);
			}
		}
		
		for(ArticleEvaluationStore evaluationStore : evaluationStatisticsStoreList) {
			if(voMap.containsKey(evaluationStore.getPostObjectId())) {
				tmpVO = voMap.get(evaluationStore.getPostObjectId());
				tmpVO.addEvaluationCount(evaluationStore.getEvaluationCode(), evaluationStore.getEvaluationCount());
			} else {
				tmpVO = new ArticleEvaluationStatisticsVO();
				tmpVO.setPk(encryptIdMap.get(evaluationStore.getPostObjectId()));
				tmpVO.addEvaluationCount(evaluationStore.getEvaluationCode(), evaluationStore.getEvaluationCount());
				voMap.put(evaluationStore.getPostObjectId(), tmpVO);
			}
		}
		
		ArticleEvaluationCounterVO tmpCounter = null;
		HashMap<Integer, ArticleEvaluationCounterVO> tmpCounterMap = null;
		for(Long articleId : articleIdList) {
			if(voMap.containsKey(articleId)) {
				for(ArticleEvaluationCodeType evaluationCodeType : ArticleEvaluationCodeType.values()) {
					if(!voMap.get(articleId).getEvaluationCodeAndCount().containsKey(evaluationCodeType.getEvaluationCode())) {
						tmpCounter = new ArticleEvaluationCounterVO();
						tmpCounter.setEvaluationCode(evaluationCodeType.getEvaluationCode());
						tmpCounter.setEvaluationName(evaluationCodeType.getEvaluationName());
						tmpCounter.setEvaluationCount(0);
						voMap.get(articleId).getEvaluationCodeAndCount().put(evaluationCodeType.getEvaluationCode(), tmpCounter);
					}
				}
			} else {
				tmpCounterMap = new HashMap<Integer, ArticleEvaluationCounterVO>();
				for(ArticleEvaluationCodeType evaluationCodeType : ArticleEvaluationCodeType.values()) {
					tmpCounter = new ArticleEvaluationCounterVO();
					tmpCounter.setEvaluationCode(evaluationCodeType.getEvaluationCode());
					tmpCounter.setEvaluationName(evaluationCodeType.getEvaluationName());
					tmpCounter.setEvaluationCount(0);
					tmpCounterMap.put(evaluationCodeType.getEvaluationCode(), tmpCounter);
				}
				tmpVO = new ArticleEvaluationStatisticsVO();
				tmpVO.setPk(encryptIdMap.get(articleId));
				tmpVO.setEvaluationCodeAndCount(tmpCounterMap);
				voMap.put(articleId, tmpVO);
			}
		}
		
		return voMap;
	}
	
	@Override
	public CommonResult insertArticleLongEvaluation(InsertArticleLongEvaluationParam inputParam, Long evaluationVoterId) {
		CommonResult result = new CommonResult();
		if(evaluationVoterId == null) {
			result.fillWithResult(ResultType.notLoginUser);
			return result;
		}
		
		if(StringUtils.isBlank(inputParam.getPk()) || inputParam.getEvaluationCode() == null || inputParam.getEvaluationType() == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		if(ArticleEvaluationType.getType(inputParam.getEvaluationType()) == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		ArticleEvaluationCodeType evaluationType = ArticleEvaluationCodeType.getType(inputParam.getEvaluationCode());
		if(evaluationType == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		Long articleId = decryptArticlePrivateKey(inputParam.getPk());
		if(articleId == null) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		InsertEvaluationDaoParam daoParam = new InsertEvaluationDaoParam();
		daoParam.setPostObjectId(articleId);
		daoParam.setEvaluationType(inputParam.getEvaluationType());
		daoParam.setUserId(evaluationVoterId);
		daoParam.setEvaluationCode(inputParam.getEvaluationCode());
		
		int insertCount = articleEvaluationCacheMapper.insertEvaluation(daoParam);
		if(insertCount != 1) {
			result.fillWithResult(ResultType.hadEvaluationVoted);
			return result;
		} 
		
		CommonResult updateResult = updateChannelCoefficientByInsertEvaluation(articleId, evaluationVoterId, evaluationType);
		if(!updateResult.isSuccess()) {
			return updateResult;
		}
		
		result.fillWithResult(ResultType.evaluationVoteSuccess);
		return result;
	}
	
	
	public CommonResult insertArticleCommentEvaluation(InsertArticleCommentEvaluationParam inputParam, Long evaluationVoterId) {
//		TODO
		CommonResult result = new CommonResult();
		if(evaluationVoterId == null) {
			result.fillWithResult(ResultType.notLoginUser);
			return result;
		}
		
		if(inputParam.getCommentId() == null || inputParam.getEvaluationCode() == null || inputParam.getEvaluationType() == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		if(ArticleEvaluationType.getType(inputParam.getEvaluationType()) == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		ArticleEvaluationCodeType evaluationType = ArticleEvaluationCodeType.getType(inputParam.getEvaluationCode());
		if(evaluationType == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		InsertEvaluationDaoParam daoParam = new InsertEvaluationDaoParam();
		daoParam.setPostObjectId(inputParam.getCommentId());
		daoParam.setEvaluationType(inputParam.getEvaluationType());
		daoParam.setUserId(evaluationVoterId);
		daoParam.setEvaluationCode(inputParam.getEvaluationCode());
		
		int insertCount = articleEvaluationCacheMapper.insertEvaluation(daoParam);
		if(insertCount != 1) {
			result.fillWithResult(ResultType.hadEvaluationVoted);
			return result;
		} 
		
		CommonResult updateResult = updateChannelCoefficientByInsertEvaluation(inputParam.getCommentId(), evaluationVoterId, evaluationType);
		if(!updateResult.isSuccess()) {
			return updateResult;
		}
		
		result.fillWithResult(ResultType.evaluationVoteSuccess);
		return result;
	}
	
	private CommonResult updateChannelCoefficientByInsertEvaluation(Long articleId, Long evaluationVoterId, ArticleEvaluationCodeType evaluationType) {
		CommonResult result = new CommonResult();
		if(articleId == null || evaluationVoterId == null || evaluationType == null) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		
		int updateCount = 0;
		
		FindArticleLongParam param = new FindArticleLongParam();
		param.setArticleId(articleId);
		ArticleLong article = articleLongMapper.findArticleLong(param);
		if(article == null || article.getChannelId() == null || article.getUserId() == null) {
			result.fillWithResult(ResultType.serviceError);
			return result;
		}
		
//		// update article creator coe
//		UpdateArticleUserCoefficientParam updateUserCoefficientParam = new UpdateArticleUserCoefficientParam();
//		updateUserCoefficientParam.setChannelId(article.getChannelId());
//		updateUserCoefficientParam.setUserId(article.getUserId());
//		if(evaluationType.equals(ArticleEvaluationType.up)) {
//			updateUserCoefficientParam.setCoefficient(ArticleConstant.clickUpCoefficient);
//		} else {
//			updateUserCoefficientParam.setCoefficient(ArticleConstant.clickDownCoefficient);
//		}
//		updateCount = articleUserDetailMapper.updateArticleUserCoefficient(updateUserCoefficientParam);
//		if(updateCount != 1) {
//			result.fillWithResult(ResultType.serviceError);
//			return result;
//		}
		
		// if not old article, update voter coefficient
//		if(System.currentTimeMillis() - article.getCreateTime().getTime() < ArticleConstant.evaluationCacheLivingTime) {
//			updateUserCoefficientParam = new UpdateArticleUserCoefficientParam();
//			updateUserCoefficientParam.setChannelId(article.getChannelId());
//			updateUserCoefficientParam.setUserId(evaluationVoterId);
//			if(evaluationType.equals(ArticleEvaluationType.up)) {
//				updateUserCoefficientParam.setCoefficient(ArticleConstant.clickUpCoefficient);
//			} else {
//				updateUserCoefficientParam.setCoefficient(ArticleConstant.clickDownCoefficient);
//			}
//			updateCount = articleUserDetailMapper.updateArticleUserCoefficient(updateUserCoefficientParam);
//			if(updateCount != 1) {
//				result.fillWithResult(ResultType.serviceError);
//				return result;
//			}
//		}
		
		UpdateChannelPointParam updateChannelPointParam = new UpdateChannelPointParam();
		updateChannelPointParam.setChannelId(article.getChannelId());
		if(evaluationType.equals(ArticleEvaluationCodeType.up)) {
			updateChannelPointParam.setChannelPoint(ArticleConstant.clickUpCoefficient);
		} else {
			updateChannelPointParam.setChannelPoint(ArticleConstant.clickDownCoefficient);
		}
		updateCount = articleChannelsMapper.updateChannelPoint(updateChannelPointParam);
		if(updateCount != 1) {
			result.fillWithResult(ResultType.serviceError);
			return result;
		}
		
		result.fillWithResult(ResultType.success);
		return result;
	}

	@Override
	public void evaluationCacheToStore() {
		Date endTime = new Date(System.currentTimeMillis() - ArticleConstant.evaluationCacheLivingTime);
		evaluationCacheToStore(endTime);
	}
	
	@Override
	public void evaluationCacheToStore(Date endTime) {
		// TODO
		HasOldEvaluationParam hasOldEvaluationParam = new HasOldEvaluationParam();
		hasOldEvaluationParam.setCreateTime(endTime);
		if(articleEvaluationCacheMapper.hasOldEvaluation(hasOldEvaluationParam) < 1) {
			return;
		}
		
		FindEvaluationCacheStatisticsByConditionParam findCacheParam = new FindEvaluationCacheStatisticsByConditionParam();
		findCacheParam.setEndTime(endTime);
		List<ArticleEvaluationStatisticsBO> evaluationCacheStatisticsList = articleEvaluationCacheMapper.findEvaluationCacheStatisticsByCondition(findCacheParam);
		if(evaluationCacheStatisticsList == null || evaluationCacheStatisticsList.size() < 1) {
			return; 
		}
		
		ArticleEvaluationStore tmpEvaluationStore = null;
		List<ArticleEvaluationStore> evaluationStoreList = new ArrayList<ArticleEvaluationStore>();
		String evaluationCacheIdStr = null;
		List<String> evaluationCacheIdStrList = null;
		List<Long> cacheIdList = new ArrayList<Long>();
		for(ArticleEvaluationStatisticsBO bo : evaluationCacheStatisticsList) {
			tmpEvaluationStore = new ArticleEvaluationStore();
			tmpEvaluationStore.setEvaluationType(bo.getEvaluationType());
			tmpEvaluationStore.setPostObjectId(bo.getPostObjectId());
			tmpEvaluationStore.setEvaluationCode(bo.getEvaluationCode());
			tmpEvaluationStore.setEvaluationCount(bo.getEvaluationCount());
			evaluationStoreList.add(tmpEvaluationStore);
			evaluationCacheIdStr = bo.getEvaluationCacheIdStr();
			evaluationCacheIdStrList = Arrays.asList(evaluationCacheIdStr.split(","));
			for(String idStr : evaluationCacheIdStrList) {
				if(NumericUtilCustom.matchInteger(idStr)) {
					cacheIdList.add(Long.parseLong(idStr));
				}
			}
			
		}
		articleEvaluationStoreMapper.batchInert(evaluationStoreList);
		
		ArticleEvaluationCacheUpdateIsDeleteParam updateIsDeleteparam = new ArticleEvaluationCacheUpdateIsDeleteParam();
		updateIsDeleteparam.setDelete(true);
		updateIsDeleteparam.setIdList(cacheIdList);
		articleEvaluationCacheMapper.updateIsDelete(updateIsDeleteparam);
		
		ArticleEvaluationCacheDeleteByIdParam deleteCacheParam = new ArticleEvaluationCacheDeleteByIdParam();
		deleteCacheParam.setIdList(cacheIdList);
		articleEvaluationCacheMapper.deleteById(deleteCacheParam);
	}
	
	@Override
	public void evaluationCacheStatistics() {
		int hasNotStatistics = articleEvaluationCacheMapper.hasNotStatistics();
		if(hasNotStatistics < 1) {
			return;
		}
		
		FindArticleEvaluationCacheListParam findArticleEvaluationCacheListParam = new FindArticleEvaluationCacheListParam();
		// 未满3天的不归入统计
		findArticleEvaluationCacheListParam.setEndTime(DateUtilCustom.dateDiffDays(-3));
		List<ArticleEvaluationStatisticsTaskBO> evaluationStatisticsList = articleEvaluationCacheMapper.findEvaluationStatisticsTaskList(findArticleEvaluationCacheListParam);
		if(evaluationStatisticsList == null || evaluationStatisticsList.size() < 1) {
			return;
		}
		
		List<Long> evaluationCacheIdList = new ArrayList<Long>();
		for(ArticleEvaluationStatisticsTaskBO bo : evaluationStatisticsList) {
			bo.setUserIdListFromUserIds();
			bo.evaluationCacheIdList();
			evaluationCacheIdList.addAll(bo.getEvaluationCacheIdList());
		}
		
		if(evaluationCacheIdList.size() < 1) {
			return;
		}
		ArticleEvaluationCacheUpdateWasStatisticsByIdParam updateWasStatisticsParam = new ArticleEvaluationCacheUpdateWasStatisticsByIdParam();
		updateWasStatisticsParam.setIdList(evaluationCacheIdList);
		
		List<ArticleEvaluationStatisticsTaskBO> goodEvaluationBOList = new ArrayList<ArticleEvaluationStatisticsTaskBO>();
		List<ArticleEvaluationStatisticsTaskBO> badEvaluationBOList = new ArrayList<ArticleEvaluationStatisticsTaskBO>();
		Map<Long, ArticleEvaluationEstimateBO> evaluationCounterMap = buildArticleEvaluationEstimateMap(evaluationStatisticsList);
		filterGoodEvaluationBO(evaluationCounterMap, evaluationStatisticsList, goodEvaluationBOList, badEvaluationBOList);
		List<Long> goodArticleIdList = new ArrayList<Long>();
		List<Long> badArticleIdList = new ArrayList<Long>();
		goodEvaluationBOList.stream().forEach(bo -> goodArticleIdList.add(bo.getPostObjectId()));
		badEvaluationBOList.stream().forEach(bo -> badArticleIdList.add(bo.getPostObjectId()));
		updateArticleCreatorChannelCoefficient(ArticleEvaluationCodeType.up, goodArticleIdList);
		updateArticleCreatorChannelCoefficient(ArticleEvaluationCodeType.down, badArticleIdList);
		
		List<UpdateArticleUserCoefficientParam> goodEvaluationUpdateParamList = new ArrayList<UpdateArticleUserCoefficientParam>();
		List<UpdateArticleUserCoefficientParam> badEvaluationUpdateParamList = new ArrayList<UpdateArticleUserCoefficientParam>();
		fillEvaluationUpdateParam(ArticleEvaluationCodeType.up, goodEvaluationBOList, goodEvaluationUpdateParamList);
		fillEvaluationUpdateParam(ArticleEvaluationCodeType.down, badEvaluationBOList, badEvaluationUpdateParamList);
		
		
		if(goodEvaluationUpdateParamList.size() > 0) {
			articleUserDetailMapper.batchUpdateArticleUserCoefficient(goodEvaluationUpdateParamList);
		}
		if(badEvaluationUpdateParamList.size() > 0) {
			articleUserDetailMapper.batchUpdateArticleUserCoefficient(badEvaluationUpdateParamList);
		}
		articleEvaluationCacheMapper.updateWasStatistics(updateWasStatisticsParam);
		
		return;
	}
	
	private Map<Long, ArticleEvaluationEstimateBO> buildArticleEvaluationEstimateMap(List<ArticleEvaluationStatisticsTaskBO> inputEvaluationStatisticsList) {
		Map<Long, ArticleEvaluationEstimateBO> evaluationCounterMap = new HashMap<Long, ArticleEvaluationEstimateBO>();
		if(inputEvaluationStatisticsList == null || inputEvaluationStatisticsList.size() < 1) {
			return evaluationCounterMap;
		}
		
		ArticleEvaluationEstimateBO tmpEstimater = null;
		for(ArticleEvaluationStatisticsTaskBO inputBO : inputEvaluationStatisticsList) {
			if(evaluationCounterMap.containsKey(inputBO.getPostObjectId())) {
				tmpEstimater = evaluationCounterMap.get(inputBO.getPostObjectId());
				if(ArticleEvaluationCodeType.up.getEvaluationCode().equals(inputBO.getEvaluationCode())) {
					tmpEstimater.addGoodEvaluationCounter(inputBO.getEvaluationCount());
				} else if(ArticleEvaluationCodeType.down.getEvaluationCode().equals(inputBO.getEvaluationCode())){
					tmpEstimater.addBadEvaluationCounter(inputBO.getEvaluationCount());
				}
			} else {
				tmpEstimater = new ArticleEvaluationEstimateBO();
				if(ArticleEvaluationCodeType.up.getEvaluationCode().equals(inputBO.getEvaluationCode())) {
					tmpEstimater.addGoodEvaluationCounter(inputBO.getEvaluationCount());
				} else if(ArticleEvaluationCodeType.down.getEvaluationCode().equals(inputBO.getEvaluationCode())){
					tmpEstimater.addBadEvaluationCounter(inputBO.getEvaluationCount());
				}
				tmpEstimater.setArticleId(inputBO.getPostObjectId());
				evaluationCounterMap.put(inputBO.getPostObjectId(), tmpEstimater);
			}
		}
		
		for(ArticleEvaluationEstimateBO estimateBO : evaluationCounterMap.values()) {
			estimateBO.initIsGoodResult();
		}
		
		return evaluationCounterMap;
	}

	private void filterGoodEvaluationBO(Map<Long, ArticleEvaluationEstimateBO> evaluationCounterMap, List<ArticleEvaluationStatisticsTaskBO> inputEvaluationStatisticsList, List<ArticleEvaluationStatisticsTaskBO> goodEvaluationBOList, List<ArticleEvaluationStatisticsTaskBO> badEvaluationBOList) {
		ArticleEvaluationEstimateBO tmpEstimater = null;
		ArticleEvaluationStatisticsTaskBO tmpBO = null;
		for(int i = 0; i < inputEvaluationStatisticsList.size(); i++) {
			tmpBO = inputEvaluationStatisticsList.get(i);
			if(evaluationCounterMap.get(tmpBO.getPostObjectId()) != null) {
				tmpEstimater = evaluationCounterMap.get(tmpBO.getPostObjectId());
			}
			if(tmpEstimater.getIsGoodResult() == null) {
				inputEvaluationStatisticsList.remove(i);
				i--;
			// 视文章是否受欢迎.  区分有效评价
			} else if(tmpEstimater.getIsGoodResult()) {
				if(ArticleEvaluationCodeType.up.getEvaluationCode().equals(tmpBO.getEvaluationCode())) {
					goodEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				} else if(ArticleEvaluationCodeType.down.getEvaluationCode().equals(tmpBO.getEvaluationCode())) {
					badEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				}
				i--;
			} else {
				if(ArticleEvaluationCodeType.down.getEvaluationCode().equals(tmpBO.getEvaluationCode())) {
					goodEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				} else if(ArticleEvaluationCodeType.up.getEvaluationCode().equals(tmpBO.getEvaluationCode())) {
					badEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				}
				badEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				i--;
			}
		}
	}
	
	private void fillEvaluationUpdateParam(
			ArticleEvaluationCodeType evaluationType,
			List<ArticleEvaluationStatisticsTaskBO> evaluationBOList, 
			List<UpdateArticleUserCoefficientParam> evaluationUpdateParamList) {
		if(evaluationBOList == null || evaluationBOList.size() < 1) {
			return;
		}
		
		UpdateArticleUserCoefficientParam tmpParam = null;
		int coefficient = 0;
		for(ArticleEvaluationStatisticsTaskBO bo : evaluationBOList) {
			if(ArticleEvaluationCodeType.up.equals(evaluationType)) {
				coefficient = ArticleConstant.clickUpCoefficient;
			} else if(ArticleEvaluationCodeType.down.equals(evaluationType)) {
				coefficient = ArticleConstant.clickDownCoefficient;
			}
			
			for(Long userId : bo.getUserIdList()) {
				tmpParam = new UpdateArticleUserCoefficientParam();
				tmpParam.setUserId(userId);
				tmpParam.setChannelId(bo.getChannelId());
				tmpParam.setCoefficient(coefficient);
				evaluationUpdateParamList.add(tmpParam);
			}
		}
	}
	
	private void updateArticleCreatorChannelCoefficient(ArticleEvaluationCodeType evaluationType, List<Long> articleIdList) {
		if(evaluationType == null || articleIdList == null || articleIdList.size() < 1) {
			return;
		}
		List<ArticleLong> articleList = articleLongMapper.findArticleLongList(articleIdList);
		if(articleList == null || articleList.size() < 1) {
			return;
		}
		
		int changeCoefficient = 0;
		if(evaluationType.equals(ArticleEvaluationCodeType.up)) {
			changeCoefficient = ArticleConstant.clickUpCoefficient;
		} else if(evaluationType.equals(ArticleEvaluationCodeType.down)) {
			changeCoefficient = ArticleConstant.clickDownCoefficient;
		}
		
		UpdateArticleUserCoefficientParam tmpUpdateCoefficientParam = null;
		List<UpdateArticleUserCoefficientParam> updateCoefficientParamList = new ArrayList<UpdateArticleUserCoefficientParam>();
		for(ArticleLong article : articleList) {
			tmpUpdateCoefficientParam = new UpdateArticleUserCoefficientParam();
			tmpUpdateCoefficientParam.setCoefficient(changeCoefficient);
			tmpUpdateCoefficientParam.setChannelId(article.getChannelId());
			tmpUpdateCoefficientParam.setUserId(article.getUserId());
			updateCoefficientParamList.add(tmpUpdateCoefficientParam);
		}
		
		articleUserDetailMapper.batchUpdateArticleUserCoefficient(updateCoefficientParamList);
	}
}
