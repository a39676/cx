package demo.article.mapper;

import java.util.List;

import demo.article.pojo.param.mapperParam.FindEvaluationStatisticsByIdListParam;
import demo.article.pojo.po.ArticleEvaluationStore;

public interface ArticleEvaluationStoreMapper {
    int insert(ArticleEvaluationStore record);

    int insertSelective(ArticleEvaluationStore record);
    
    List<ArticleEvaluationStore> findEvaluationStatisticsByIdList(FindEvaluationStatisticsByIdListParam param);
    
    int batchInert(List<ArticleEvaluationStore> evakyatuibStoreList);

	void insertOrUpdateEvaluationCount(ArticleEvaluationStore storePODown);
}