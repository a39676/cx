package demo.article.mapper;

import java.util.List;

import demo.article.pojo.bo.ArticleLongSummaryBO;
import demo.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.pojo.param.mapperParam.FindArticleHotSummaryListMapperParam;
import demo.article.pojo.param.mapperParam.FindArticleLongSummaryListMapperParam;
import demo.article.pojo.po.ArticleLongSummary;

public interface ArticleLongSummaryMapper {
    int insert(ArticleLongSummary record);

    int insertSelective(ArticleLongSummary record);
    
    ArticleLongSummaryBO findArticleLongSummary(Long articleId);
    
    List<ArticleLongSummaryBO> findArticleLongSummaryList(FindArticleLongSummaryListMapperParam param);
    
    List<ArticleLongSummaryBO> findArticleHotSummaryList(FindArticleHotSummaryListMapperParam param);
    
    int batchUpdatePrivateKey(List<ArticleLongSummary> articleLongSummarys);
    
    List<Long> findArticleLongSummaryListIds(BatchUpdatePrimaryKeyParam param);
}