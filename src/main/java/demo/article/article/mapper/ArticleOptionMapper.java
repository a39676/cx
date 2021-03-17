package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleOption;
import demo.article.article.pojo.po.ArticleOptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ArticleOptionMapper {
    long countByExample(ArticleOptionExample example);

    int deleteByExample(ArticleOptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleOption record);

    int insertSelective(ArticleOption record);

    List<ArticleOption> selectByExampleWithRowbounds(ArticleOptionExample example, RowBounds rowBounds);

    List<ArticleOption> selectByExample(ArticleOptionExample example);

    ArticleOption selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleOption record, @Param("example") ArticleOptionExample example);

    int updateByExample(@Param("record") ArticleOption record, @Param("example") ArticleOptionExample example);

    int updateByPrimaryKeySelective(ArticleOption record);

    int updateByPrimaryKey(ArticleOption record);
}