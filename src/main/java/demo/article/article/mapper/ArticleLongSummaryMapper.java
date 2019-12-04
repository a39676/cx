package demo.article.article.mapper;

import java.util.List;

import demo.article.article.pojo.bo.ArticleLongSummaryBO;
import demo.article.article.pojo.param.controllerParam.BatchUpdatePrimaryKeyParam;
import demo.article.article.pojo.param.mapperParam.FindArticleHotSummaryListMapperParam;
import demo.article.article.pojo.param.mapperParam.FindArticleLongSummaryListMapperParam;
import demo.article.article.pojo.po.ArticleLongSummary;

public interface ArticleLongSummaryMapper {
    int insert(ArticleLongSummary record);

    int insertSelective(ArticleLongSummary record);
    
    ArticleLongSummaryBO findArticleLongSummary(Long articleId);
    
    List<ArticleLongSummaryBO> findArticleLongSummaryList(FindArticleLongSummaryListMapperParam param);
    
    List<ArticleLongSummaryBO> findArticleHotSummaryList(FindArticleHotSummaryListMapperParam param);
    
    int batchUpdatePrivateKey(List<ArticleLongSummary> articleLongSummarys);
    
    List<Long> findArticleLongSummaryListIds(BatchUpdatePrimaryKeyParam param);
}