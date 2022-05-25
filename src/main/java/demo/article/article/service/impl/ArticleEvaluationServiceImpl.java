package demo.article.article.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.article.article.mapper.ArticleEvaluationCacheMapper;
import demo.article.article.mapper.ArticleEvaluationStoreMapper;
import demo.article.article.mapper.ArticleLongMapper;
import demo.article.article.pojo.bo.ArticleEvaluationEstimateBO;
import demo.article.article.pojo.bo.ArticleEvaluationStatisticsByArticleIdBO;
import demo.article.article.pojo.bo.ArticleEvaluationStatisticsTaskBO;
import demo.article.article.pojo.constant.ArticleEvaluationConstant;
import demo.article.article.pojo.param.controllerParam.InsertArticleCommentEvaluationParam;
import demo.article.article.pojo.param.controllerParam.InsertArticleLongEvaluationParam;
import demo.article.article.pojo.param.mapperParam.ArticleEvaluationCacheUpdateWasStatisticsByIdParam;
import demo.article.article.pojo.param.mapperParam.FindArticleEvaluationCacheListParam;
import demo.article.article.pojo.param.mapperParam.FindEvaluationStatisticsByIdListParam;
import demo.article.article.pojo.param.mapperParam.InsertEvaluationDaoParam;
import demo.article.article.pojo.po.ArticleEvaluationCache;
import demo.article.article.pojo.po.ArticleEvaluationStore;
import demo.article.article.pojo.po.ArticleLong;
import demo.article.article.pojo.type.ArticleEvaluationCodeType;
import demo.article.article.pojo.type.ArticleEvaluationType;
import demo.article.article.pojo.vo.ArticleEvaluationCounterVO;
import demo.article.article.pojo.vo.ArticleEvaluationStatisticsVO;
import demo.article.article.service.ArticleEvaluationService;
import demo.common.pojo.result.CommonResultCX;
import demo.common.pojo.type.ResultTypeCX;
import net.sf.json.JSONObject;

@Service
public class ArticleEvaluationServiceImpl extends ArticleCommonService implements ArticleEvaluationService {

	@Autowired
	private ArticleLongMapper articleLongMapper;
	@Autowired
	private ArticleEvaluationCacheMapper articleEvaluationCacheMapper;
	@Autowired
	private ArticleEvaluationStoreMapper articleEvaluationStoreMapper;

	@Autowired
//	private RedisTemplate<String, ArticleEvaluationCache> articleEvaluationCacheRedisTemplate;
	private RedisTemplate<String, String> articleEvaluationCacheRedisTemplate;

	@Override
	public Map<Long, ArticleEvaluationStatisticsVO> findEvaluationStatisticsByArticleId(
			ArticleEvaluationType evaluationType, List<Long> articleIdList) {
		Map<Long, ArticleEvaluationStatisticsVO> voMap = new HashMap<Long, ArticleEvaluationStatisticsVO>();
		if (articleIdList == null || articleIdList.size() < 1) {
			return voMap;
		}

		HashMap<Long, String> encryptIdMap = new HashMap<Long, String>();
		articleIdList.stream().forEach(id -> encryptIdMap.put(id, systemOptionService.encryptId(id)));
		if (encryptIdMap.size() != articleIdList.size()) {
			return voMap;
		}

		FindEvaluationStatisticsByIdListParam findEvaluationStatisticsParam = new FindEvaluationStatisticsByIdListParam();
		findEvaluationStatisticsParam.setEvaluationType(evaluationType.getCode());
		findEvaluationStatisticsParam.setPostObjectIdList(articleIdList);

		List<ArticleEvaluationStatisticsByArticleIdBO> evaluationStatisticsCacheBOList = articleEvaluationCacheMapper
				.findEvaluationStatisticsByIdList(findEvaluationStatisticsParam);
		List<ArticleEvaluationStore> evaluationStatisticsStoreList = articleEvaluationStoreMapper
				.findEvaluationStatisticsByIdList(findEvaluationStatisticsParam);

		ArticleEvaluationStatisticsVO tmpVO = null;
		for (ArticleEvaluationStatisticsByArticleIdBO bo : evaluationStatisticsCacheBOList) {
			if (voMap.containsKey(bo.getPostObjectId())) {
				tmpVO = voMap.get(bo.getPostObjectId());
				tmpVO.addEvaluationCount(bo.getEvaluationCode(), bo.getEvaluationCount());
			} else {
				tmpVO = new ArticleEvaluationStatisticsVO();
				tmpVO.setPk(encryptIdMap.get(bo.getPostObjectId()));
				tmpVO.addEvaluationCount(bo.getEvaluationCode(), bo.getEvaluationCount());
				voMap.put(bo.getPostObjectId(), tmpVO);
			}
		}

		for (ArticleEvaluationStore evaluationStore : evaluationStatisticsStoreList) {
			if (voMap.containsKey(evaluationStore.getPostObjectId())) {
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
		for (Long articleId : articleIdList) {
			if (voMap.containsKey(articleId)) {
				for (ArticleEvaluationCodeType evaluationCodeType : ArticleEvaluationCodeType.values()) {
					if (!voMap.get(articleId).getEvaluationCodeAndCount().containsKey(evaluationCodeType.getCode())) {
						tmpCounter = new ArticleEvaluationCounterVO();
						tmpCounter.setEvaluationCode(evaluationCodeType.getCode());
						tmpCounter.setEvaluationName(evaluationCodeType.getName());
						tmpCounter.setEvaluationCount(0);
						voMap.get(articleId).getEvaluationCodeAndCount().put(evaluationCodeType.getCode(), tmpCounter);
					}
				}
			} else {
				tmpCounterMap = new HashMap<Integer, ArticleEvaluationCounterVO>();
				for (ArticleEvaluationCodeType evaluationCodeType : ArticleEvaluationCodeType.values()) {
					tmpCounter = new ArticleEvaluationCounterVO();
					tmpCounter.setEvaluationCode(evaluationCodeType.getCode());
					tmpCounter.setEvaluationName(evaluationCodeType.getName());
					tmpCounter.setEvaluationCount(0);
					tmpCounterMap.put(evaluationCodeType.getCode(), tmpCounter);
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
	public CommonResultCX insertArticleLongEvaluationRedis(InsertArticleLongEvaluationParam inputParam) {
		CommonResultCX result = new CommonResultCX();
		Long evaluationVoterId = baseUtilCustom.getUserId();
		if (evaluationVoterId == null) {
			result.fillWithResult(ResultTypeCX.notLoginUser);
			return result;
		}
		if (StringUtils.isBlank(inputParam.getPk()) || inputParam.getEvaluationCode() == null
				|| inputParam.getEvaluationType() == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}
		inputParam.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
		ArticleEvaluationCodeType evaluationType = ArticleEvaluationCodeType.getType(inputParam.getEvaluationCode());
		if (evaluationType == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}

		Long articleId = systemOptionService.decryptPrivateKey(inputParam.getPk());
		if (articleId == null) {
			result.fillWithResult(ResultTypeCX.errorParam);
			return result;
		}

		List<String> evaluationCacheList = articleEvaluationCacheRedisTemplate.opsForList()
				.range(ArticleEvaluationConstant.evaluationCacheRedisKeyNamePreffix + articleId, 0, -1);
		for (String i : evaluationCacheList) {
			if (StringUtils.isNotEmpty(i) && i.contains(evaluationVoterId.toString())) {
				result.fillWithResult(ResultTypeCX.hadEvaluationVoted);
				return result;
			}
		}

		ArticleEvaluationCache cache = createArticlEvaluationCacheFrom(inputParam, articleId, evaluationVoterId);
		articleEvaluationCacheRedisTemplate.opsForList().leftPush(
				ArticleEvaluationConstant.evaluationCacheRedisKeyNamePreffix + articleId,
				JSONObject.fromObject(cache).toString());

		CommonResultCX updateResult = updateChannelCoefficientByInsertEvaluation(articleId, evaluationVoterId,
				evaluationType);
		if (!updateResult.isSuccess()) {
			return updateResult;
		}

		result.fillWithResult(ResultTypeCX.evaluationVoteSuccess);
		return result;
	}

	private ArticleEvaluationCache createArticlEvaluationCacheFrom(InsertArticleLongEvaluationParam inputParam,
			Long articleId, Long evaluationVoterId) {
		ArticleEvaluationCache cache = new ArticleEvaluationCache();
		cache.setEvaluationCacheId(snowFlake.getNextId());
		cache.setCreateTime(new Date());
		cache.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
		cache.setEvaluationCode(inputParam.getEvaluationCode());
		cache.setPostObjectId(articleId);
		cache.setUserId(evaluationVoterId);
		cache.setWasStatistics(false);
		return cache;
	}

	/*
	 * 从redis中查找
	 */
	public CommonResultCX insertArticleCommentEvaluation(InsertArticleCommentEvaluationParam inputParam,
			Long evaluationVoterId) {
//		TODO
		CommonResultCX result = new CommonResultCX();
		if (evaluationVoterId == null) {
			result.fillWithResult(ResultTypeCX.notLoginUser);
			return result;
		}

		if (inputParam.getCommentId() == null || inputParam.getEvaluationCode() == null
				|| inputParam.getEvaluationType() == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}

		if (ArticleEvaluationType.getType(inputParam.getEvaluationType()) == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}

		ArticleEvaluationCodeType evaluationType = ArticleEvaluationCodeType.getType(inputParam.getEvaluationCode());
		if (evaluationType == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}

		InsertEvaluationDaoParam daoParam = new InsertEvaluationDaoParam();
		daoParam.setPostObjectId(inputParam.getCommentId());
		daoParam.setEvaluationType(inputParam.getEvaluationType());
		daoParam.setUserId(evaluationVoterId);
		daoParam.setEvaluationCode(inputParam.getEvaluationCode());

		int insertCount = articleEvaluationCacheMapper.insertEvaluation(daoParam);
		if (insertCount != 1) {
			result.fillWithResult(ResultTypeCX.hadEvaluationVoted);
			return result;
		}

		CommonResultCX updateResult = updateChannelCoefficientByInsertEvaluation(inputParam.getCommentId(),
				evaluationVoterId, evaluationType);
		if (!updateResult.isSuccess()) {
			return updateResult;
		}

		result.fillWithResult(ResultTypeCX.evaluationVoteSuccess);
		return result;
	}

	private CommonResultCX updateChannelCoefficientByInsertEvaluation(Long articleId, Long evaluationVoterId,
			ArticleEvaluationCodeType evaluationType) {
		CommonResultCX result = new CommonResultCX();
		if (articleId == null || evaluationVoterId == null || evaluationType == null) {
			result.fillWithResult(ResultTypeCX.nullParam);
			return result;
		}

		ArticleLong article = articleLongMapper.selectByPrimaryKey(articleId);
		if (article == null || article.getChannelId() == null || article.getUserId() == null) {
			result.fillWithResult(ResultTypeCX.serviceError);
			return result;
		}

		result.fillWithResult(ResultTypeCX.success);
		return result;
	}

	@Override
	public void evaluationCacheToStore() {

		Set<String> keys = articleEvaluationCacheRedisTemplate
				.keys(ArticleEvaluationConstant.evaluationCacheRedisKeyNamePreffix + ".*");
		if (keys == null || keys.size() < 1) {
			return;
		}

		for (String key : keys) {
			evaluationCacheToStore(key);
		}

	}

	private void evaluationCacheToStore(String redisKey) {
		Long evaluationCacheListSize = articleEvaluationCacheRedisTemplate.opsForList().size(redisKey);

		if (evaluationCacheListSize == null || evaluationCacheListSize < 1) {
			return;
		}

		ArticleEvaluationCache cache = null;
		List<ArticleEvaluationCache> cacheList = new ArrayList<ArticleEvaluationCache>();
		String tmpStr = null;
		for (; evaluationCacheListSize > 0; evaluationCacheListSize--) {
			tmpStr = articleEvaluationCacheRedisTemplate.opsForList().rightPop(redisKey);
			Gson gson = new Gson();
			cache = gson.fromJson(tmpStr, ArticleEvaluationCache.class);
			cacheList.add(cache);
		}

		ArticleEvaluationStore storePOUp = new ArticleEvaluationStore();
		storePOUp.setEvaluationStoreId(snowFlake.getNextId());
		storePOUp.setCreateTime(new Date());
		storePOUp.setUpdateTime(new Date());
		storePOUp.setPostObjectId(cache.getPostObjectId());
		storePOUp.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
		storePOUp.setEvaluationCode(ArticleEvaluationCodeType.up.getCode());
		storePOUp.setEvaluationCount(0);

		ArticleEvaluationStore storePODown = new ArticleEvaluationStore();
		storePODown.setEvaluationStoreId(snowFlake.getNextId());
		storePODown.setCreateTime(new Date());
		storePODown.setUpdateTime(new Date());
		storePODown.setPostObjectId(cache.getPostObjectId());
		storePODown.setEvaluationType(ArticleEvaluationType.articleLongEvaluation.getCode());
		storePODown.setEvaluationCode(ArticleEvaluationCodeType.down.getCode());
		storePODown.setEvaluationCount(0);

		for (ArticleEvaluationCache i : cacheList) {
			if (ArticleEvaluationCodeType.up.getCode().equals(i.getEvaluationCode())) {
				storePOUp.setEvaluationCount(storePOUp.getEvaluationCount() + 1);
			} else {
				storePODown.setEvaluationCount(storePODown.getEvaluationCount() + 1);
			}
		}

		articleEvaluationStoreMapper.insertOrUpdateEvaluationCount(storePOUp);
		articleEvaluationStoreMapper.insertOrUpdateEvaluationCount(storePODown);
	}

	/**
	 * 2019年8月27日 暂时搁置此功能 本应实现, 根据评价缓存中的数据, 动态更改用户与频道的关联参数
	 */
	@Override
	public void evaluationCacheStatistics() {
		int hasNotStatistics = articleEvaluationCacheMapper.hasNotStatistics();
		if (hasNotStatistics < 1) {
			return;
		}

		FindArticleEvaluationCacheListParam findArticleEvaluationCacheListParam = new FindArticleEvaluationCacheListParam();
		// 未满3天的不归入统计
		findArticleEvaluationCacheListParam.setEndTime(dateHandler.dateDiffDays(-3));
		List<ArticleEvaluationStatisticsTaskBO> evaluationStatisticsList = articleEvaluationCacheMapper
				.findEvaluationStatisticsTaskList(findArticleEvaluationCacheListParam);
		if (evaluationStatisticsList == null || evaluationStatisticsList.size() < 1) {
			return;
		}

		List<Long> evaluationCacheIdList = new ArrayList<Long>();
		for (ArticleEvaluationStatisticsTaskBO bo : evaluationStatisticsList) {
			bo.setUserIdListFromUserIds();
			bo.evaluationCacheIdList();
			evaluationCacheIdList.addAll(bo.getEvaluationCacheIdList());
		}

		if (evaluationCacheIdList.size() < 1) {
			return;
		}
		ArticleEvaluationCacheUpdateWasStatisticsByIdParam updateWasStatisticsParam = new ArticleEvaluationCacheUpdateWasStatisticsByIdParam();
		updateWasStatisticsParam.setIdList(evaluationCacheIdList);

		List<ArticleEvaluationStatisticsTaskBO> goodEvaluationBOList = new ArrayList<ArticleEvaluationStatisticsTaskBO>();
		List<ArticleEvaluationStatisticsTaskBO> badEvaluationBOList = new ArrayList<ArticleEvaluationStatisticsTaskBO>();
		Map<Long, ArticleEvaluationEstimateBO> evaluationCounterMap = buildArticleEvaluationEstimateMap(
				evaluationStatisticsList);
		filterGoodEvaluationBO(evaluationCounterMap, evaluationStatisticsList, goodEvaluationBOList,
				badEvaluationBOList);
		List<Long> goodArticleIdList = new ArrayList<Long>();
		List<Long> badArticleIdList = new ArrayList<Long>();
		goodEvaluationBOList.stream().forEach(bo -> goodArticleIdList.add(bo.getPostObjectId()));
		badEvaluationBOList.stream().forEach(bo -> badArticleIdList.add(bo.getPostObjectId()));

		articleEvaluationCacheMapper.updateWasStatistics(updateWasStatisticsParam);

		return;
	}

	private Map<Long, ArticleEvaluationEstimateBO> buildArticleEvaluationEstimateMap(
			List<ArticleEvaluationStatisticsTaskBO> inputEvaluationStatisticsList) {
		Map<Long, ArticleEvaluationEstimateBO> evaluationCounterMap = new HashMap<Long, ArticleEvaluationEstimateBO>();
		if (inputEvaluationStatisticsList == null || inputEvaluationStatisticsList.size() < 1) {
			return evaluationCounterMap;
		}

		ArticleEvaluationEstimateBO tmpEstimater = null;
		for (ArticleEvaluationStatisticsTaskBO inputBO : inputEvaluationStatisticsList) {
			if (evaluationCounterMap.containsKey(inputBO.getPostObjectId())) {
				tmpEstimater = evaluationCounterMap.get(inputBO.getPostObjectId());
				if (ArticleEvaluationCodeType.up.getCode().equals(inputBO.getEvaluationCode())) {
					tmpEstimater.addGoodEvaluationCounter(inputBO.getEvaluationCount());
				} else if (ArticleEvaluationCodeType.down.getCode().equals(inputBO.getEvaluationCode())) {
					tmpEstimater.addBadEvaluationCounter(inputBO.getEvaluationCount());
				}
			} else {
				tmpEstimater = new ArticleEvaluationEstimateBO();
				if (ArticleEvaluationCodeType.up.getCode().equals(inputBO.getEvaluationCode())) {
					tmpEstimater.addGoodEvaluationCounter(inputBO.getEvaluationCount());
				} else if (ArticleEvaluationCodeType.down.getCode().equals(inputBO.getEvaluationCode())) {
					tmpEstimater.addBadEvaluationCounter(inputBO.getEvaluationCount());
				}
				tmpEstimater.setArticleId(inputBO.getPostObjectId());
				evaluationCounterMap.put(inputBO.getPostObjectId(), tmpEstimater);
			}
		}

		for (ArticleEvaluationEstimateBO estimateBO : evaluationCounterMap.values()) {
			initIsGoodResult(estimateBO);
		}

		return evaluationCounterMap;
	}

	private void initIsGoodResult(ArticleEvaluationEstimateBO bo) {
		// 如果评价过于接近. 不作coefficient变动

		if (bo.getGoodEvaluationCounter()
				+ bo.getBadEvaluationCounter() < ArticleEvaluationConstant.miniEvaluationCount) {
			if (bo.getGoodEvaluationCounter() > bo.getBadEvaluationCounter()) {
				bo.setIsGoodResult(true);
			} else {
				bo.setIsGoodResult(false);
			}
		} else {
			if (bo.getGoodEvaluationCounter() == 0 && bo.getBadEvaluationCounter() > 0) {
				bo.setIsGoodResult(false);
			} else if (bo.getBadEvaluationCounter() == 0 && bo.getGoodEvaluationCounter() > 0) {
				bo.setIsGoodResult(true);
			} else if (bo.getGoodEvaluationCounter() > bo.getBadEvaluationCounter()
					&& ((bo.getGoodEvaluationCounter() + 0D)
							/ bo.getBadEvaluationCounter() > ArticleEvaluationConstant.balanceEvaluationRatio)) {
				bo.setIsGoodResult(true);
			} else if (bo.getBadEvaluationCounter() > bo.getGoodEvaluationCounter()
					&& ((bo.getBadEvaluationCounter() + 0D)
							/ bo.getGoodEvaluationCounter() > ArticleEvaluationConstant.balanceEvaluationRatio)) {
				bo.setIsGoodResult(false);
			}
		}
	}

	private void filterGoodEvaluationBO(Map<Long, ArticleEvaluationEstimateBO> evaluationCounterMap,
			List<ArticleEvaluationStatisticsTaskBO> inputEvaluationStatisticsList,
			List<ArticleEvaluationStatisticsTaskBO> goodEvaluationBOList,
			List<ArticleEvaluationStatisticsTaskBO> badEvaluationBOList) {
		ArticleEvaluationEstimateBO tmpEstimater = null;
		ArticleEvaluationStatisticsTaskBO tmpBO = null;
		for (int i = 0; i < inputEvaluationStatisticsList.size(); i++) {
			tmpBO = inputEvaluationStatisticsList.get(i);
			if (evaluationCounterMap.get(tmpBO.getPostObjectId()) != null) {
				tmpEstimater = evaluationCounterMap.get(tmpBO.getPostObjectId());
			}
			if (tmpEstimater.getIsGoodResult() == null) {
				inputEvaluationStatisticsList.remove(i);
				i--;
				// 视文章是否受欢迎. 区分有效评价
			} else if (tmpEstimater.getIsGoodResult()) {
				if (ArticleEvaluationCodeType.up.getCode().equals(tmpBO.getEvaluationCode())) {
					goodEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				} else if (ArticleEvaluationCodeType.down.getCode().equals(tmpBO.getEvaluationCode())) {
					badEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				}
				i--;
			} else {
				if (ArticleEvaluationCodeType.down.getCode().equals(tmpBO.getEvaluationCode())) {
					goodEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				} else if (ArticleEvaluationCodeType.up.getCode().equals(tmpBO.getEvaluationCode())) {
					badEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				}
				badEvaluationBOList.add(inputEvaluationStatisticsList.remove(i));
				i--;
			}
		}
	}

}
