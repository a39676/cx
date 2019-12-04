package demo.article.article.mapper;

import java.util.List;

import demo.article.article.pojo.param.mapperParam.FindEvaluationStatisticsByIdListParam;
import demo.article.article.pojo.po.ArticleEvaluationStore;

public interface ArticleEvaluationStoreMapper {
    int insert(ArticleEvaluationStore record);

    int insertSelective(ArticleEvaluationStore record);
    
    List<ArticleEvaluationStore> findEvaluationStatisticsByIdList(FindEvaluationStatisticsByIdListParam param);
    
    int batchInert(List<ArticleEvaluationStore> evakyatuibStoreList);

	void insertOrUpdateEvaluationCount(ArticleEvaluationStore po);
}