package demo.articleComment.mapper;

import java.util.List;

import demo.articleComment.pojo.bo.ArticleCommentCountByArticleIdBO;
import demo.articleComment.pojo.bo.FindCommentByArticleIdBO;
import demo.articleComment.pojo.param.mapperParam.FindCommentByArticleIdParam;
import demo.articleComment.pojo.param.mapperParam.JustCommentParam;
import demo.articleComment.pojo.po.ArticleComment;

public interface ArticleCommentMapper {
    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);
    
    int insertNew(ArticleComment record);
    
    List<FindCommentByArticleIdBO> findCommentByArticleId(FindCommentByArticleIdParam param);
    
    int updatePrimaryKey(ArticleComment po);
    
    int justComment(JustCommentParam param);
    
    int logicDelete(Long id);
    
    int passComment(Long id);
    
    List<Long> findArticleIdWithCommentWaitingForReview(List<Long> articleIdList);
    
    List<ArticleCommentCountByArticleIdBO> findCommentCountByArticleId(List<Long> articleIdList);
}