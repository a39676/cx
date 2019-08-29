package demo.articleComment.mapper;

import demo.articleComment.pojo.po.ArticleCommentReview;

public interface ArticleCommentReviewMapper {
    int insert(ArticleCommentReview record);

    int insertSelective(ArticleCommentReview record);
    
    int insertNew(ArticleCommentReview record);
}