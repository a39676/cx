package demo.article.article.mapper;

import java.util.List;

import demo.article.article.pojo.param.mapperParam.FindArticleHotOrderByHotLevelParam;
import demo.article.article.pojo.po.ArticleHot;

public interface ArticleHotMapper {
    int insert(ArticleHot record);

    int insertSelective(ArticleHot record);
    
    int insertNew(ArticleHot record);
    
    int batchUpdateDeleteMark(List<Long> articleIdList);
    
    int updateDeleteMark(Long articleId);
    
    int deleteArticleHotByDeleteMark();
    
    List<Long> findInvalidArticleHotId();
    
    List<ArticleHot> findArticleHotOrderByHotLevel(FindArticleHotOrderByHotLevelParam param);
}