package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleHot;
import demo.article.article.pojo.po.ArticleHotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ArticleHotMapper {
    long countByExample(ArticleHotExample example);

    int deleteByExample(ArticleHotExample example);

    int deleteByPrimaryKey(Long articleId);

    int insert(ArticleHot record);

    int insertSelective(ArticleHot record);

    List<ArticleHot> selectByExampleWithRowbounds(ArticleHotExample example, RowBounds rowBounds);

    List<ArticleHot> selectByExample(ArticleHotExample example);

    ArticleHot selectByPrimaryKey(Long articleId);

    int updateByExampleSelective(@Param("record") ArticleHot record, @Param("example") ArticleHotExample example);

    int updateByExample(@Param("record") ArticleHot record, @Param("example") ArticleHotExample example);

    int updateByPrimaryKeySelective(ArticleHot record);

    int updateByPrimaryKey(ArticleHot record);

	int insertNew(ArticleHot newArticleHot);
}