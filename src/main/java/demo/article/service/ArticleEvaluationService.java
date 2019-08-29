package demo.article.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import demo.article.pojo.param.controllerParam.InsertArticleLongEvaluationParam;
import demo.article.pojo.type.ArticleEvaluationType;
import demo.article.pojo.vo.ArticleEvaluationStatisticsVO;
import demo.baseCommon.pojo.result.CommonResult;

public interface ArticleEvaluationService {

	CommonResult insertArticleLongEvaluation(InsertArticleLongEvaluationParam inputParam, Long userId);

	Map<Long, ArticleEvaluationStatisticsVO> findEvaluationStatisticsByArticleId(ArticleEvaluationType evaluationType, List<Long> articleIdList);

	void evaluationCacheToStore();

	void evaluationCacheToStore(Date startTime);

	void evaluationCacheStatistics();

}
