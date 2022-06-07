package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleValid;
import demo.article.article.pojo.po.ArticleValidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ArticleValidMapper {
    long countByExample(ArticleValidExample example);

    int deleteByExample(ArticleValidExample example);

    int deleteByPrimaryKey(Long articleId);

    int insert(ArticleValid record);

    int insertSelective(ArticleValid record);

    List<ArticleValid> selectByExampleWithRowbounds(ArticleValidExample example, RowBounds rowBounds);

    List<ArticleValid> selectByExample(ArticleValidExample example);

    ArticleValid selectByPrimaryKey(Long articleId);

    int updateByExampleSelective(@Param("record") ArticleValid record, @Param("example") ArticleValidExample example);

    int updateByExample(@Param("record") ArticleValid record, @Param("example") ArticleValidExample example);

    int updateByPrimaryKeySelective(ArticleValid record);

    int updateByPrimaryKey(ArticleValid record);
}