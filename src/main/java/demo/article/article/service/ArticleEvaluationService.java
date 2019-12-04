package demo.article.article.service;

import java.util.List;
import java.util.Map;

import demo.article.article.pojo.param.controllerParam.InsertArticleLongEvaluationParam;
import demo.article.article.pojo.type.ArticleEvaluationType;
import demo.article.article.pojo.vo.ArticleEvaluationStatisticsVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleEvaluationService {

	CommonResultCX insertArticleLongEvaluationRedis(InsertArticleLongEvaluationParam inputParam);

	Map<Long, ArticleEvaluationStatisticsVO> findEvaluationStatisticsByArticleId(ArticleEvaluationType evaluationType, List<Long> articleIdList);

	void evaluationCacheToStore();

	void evaluationCacheStatistics();

}
