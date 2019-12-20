package demo.article.article.mapper;

import java.util.List;

import demo.article.article.pojo.param.controllerParam.InsertNewReviewRecordParam;
import demo.article.article.pojo.po.ArticleLongReview;

public interface ArticleLongReviewMapper {
    int insert(ArticleLongReview record);

    int insertSelective(ArticleLongReview record);
    
    int insertNewReviewRecord(InsertNewReviewRecordParam param);
    
    List<ArticleLongReview> findArticleCreatorIdIsNull();
    
    int batchUpdateFillCreatorId(List<ArticleLongReview> articleLongReviewList);
    
    int deleteReviewRecord(Long articleId);
}