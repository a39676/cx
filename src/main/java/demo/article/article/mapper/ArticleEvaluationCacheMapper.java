package demo.article.article.mapper;

import java.util.List;

import demo.article.article.pojo.bo.ArticleEvaluationStatisticsBO;
import demo.article.article.pojo.bo.ArticleEvaluationStatisticsByArticleIdBO;
import demo.article.article.pojo.bo.ArticleEvaluationStatisticsTaskBO;
import demo.article.article.pojo.param.mapperParam.ArticleEvaluationCacheDeleteByIdParam;
import demo.article.article.pojo.param.mapperParam.ArticleEvaluationCacheUpdateIsDeleteParam;
import demo.article.article.pojo.param.mapperParam.ArticleEvaluationCacheUpdateWasStatisticsByIdParam;
import demo.article.article.pojo.param.mapperParam.FindArticleEvaluationCacheListParam;
import demo.article.article.pojo.param.mapperParam.FindEvaluationCacheStatisticsByConditionParam;
import demo.article.article.pojo.param.mapperParam.FindEvaluationStatisticsByIdListParam;
import demo.article.article.pojo.param.mapperParam.HasOldEvaluationParam;
import demo.article.article.pojo.param.mapperParam.InsertEvaluationDaoParam;
import demo.article.article.pojo.po.ArticleEvaluationCache;

public interface ArticleEvaluationCacheMapper {
    int insert(ArticleEvaluationCache record);

    int insertSelective(ArticleEvaluationCache record);
    
    List<ArticleEvaluationStatisticsByArticleIdBO> findEvaluationStatisticsByIdList(FindEvaluationStatisticsByIdListParam param);
    
    int insertEvaluation(InsertEvaluationDaoParam param);

    int hasOldEvaluation(HasOldEvaluationParam param);
    
    int hasNotStatistics();
    
    List<ArticleEvaluationStatisticsBO> findEvaluationCacheStatisticsByCondition(FindEvaluationCacheStatisticsByConditionParam param);
    
    int deleteById(ArticleEvaluationCacheDeleteByIdParam param);
    
    int updateIsDelete(ArticleEvaluationCacheUpdateIsDeleteParam param);
    
    int updateWasStatistics(ArticleEvaluationCacheUpdateWasStatisticsByIdParam param);
    
    List<ArticleEvaluationStatisticsTaskBO> findEvaluationStatisticsTaskList(FindArticleEvaluationCacheListParam param);
}