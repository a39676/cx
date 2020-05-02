package demo.article.articleComment.mapper;

import demo.article.articleComment.pojo.po.ArticleCommentCount;
import demo.article.articleComment.pojo.po.ArticleCommentCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleCommentCountMapper {
    long countByExample(ArticleCommentCountExample example);

    int deleteByExample(ArticleCommentCountExample example);

    int deleteByPrimaryKey(Long articleId);

    int insert(ArticleCommentCount record);

    int insertSelective(ArticleCommentCount record);

    List<ArticleCommentCount> selectByExample(ArticleCommentCountExample example);

    ArticleCommentCount selectByPrimaryKey(Long articleId);

    int updateByExampleSelective(@Param("record") ArticleCommentCount record, @Param("example") ArticleCommentCountExample example);

    int updateByExample(@Param("record") ArticleCommentCount record, @Param("example") ArticleCommentCountExample example);

    int updateByPrimaryKeySelective(ArticleCommentCount record);

    int updateByPrimaryKey(ArticleCommentCount record);
}