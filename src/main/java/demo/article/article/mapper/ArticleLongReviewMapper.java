package demo.article.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.article.article.pojo.param.controllerParam.InsertNewReviewRecordParam;
import demo.article.article.pojo.po.ArticleLongReview;

public interface ArticleLongReviewMapper {
    int insert(ArticleLongReview record);

    int insertSelective(ArticleLongReview record);
    
    int insertNewReviewRecord(InsertNewReviewRecordParam param);
    
    int wasReview(@Param("articleId")Long articleId, @Param("reviewTypeId")Integer reviewTypeId);
    
    List<ArticleLongReview> findArticleCreatorIdIsNull();
    
    int batchUpdateFillCreatorId(List<ArticleLongReview> articleLongReviewList);
    
    int deleteReviewRecord(Long articleId);
}