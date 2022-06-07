package demo.article.articleComment.mapper;

import demo.article.articleComment.pojo.po.ArticleCommentReview;

public interface ArticleCommentReviewMapper {
    int insert(ArticleCommentReview record);

    int insertSelective(ArticleCommentReview record);
    
    int insertOrUpdate(ArticleCommentReview record);
}