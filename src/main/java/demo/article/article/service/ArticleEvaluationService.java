package demo.article.article.service;

import java.util.List;
import java.util.Map;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.param.controllerParam.InsertArticleLongEvaluationParam;
import demo.article.article.pojo.type.ArticleEvaluationType;
import demo.article.article.pojo.vo.ArticleEvaluationStatisticsVO;

public interface ArticleEvaluationService {

	CommonResult insertArticleLongEvaluationRedis(InsertArticleLongEvaluationParam inputParam);

	Map<Long, ArticleEvaluationStatisticsVO> findEvaluationStatisticsByArticleId(ArticleEvaluationType evaluationType, List<Long> articleIdList);

	void evaluationCacheToStore();

	void evaluationCacheStatistics();

}
