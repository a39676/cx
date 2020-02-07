package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleUserDetail;
import demo.article.article.pojo.po.ArticleUserDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleUserDetailMapper {
    long countByExample(ArticleUserDetailExample example);

    int deleteByExample(ArticleUserDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleUserDetail record);

    int insertSelective(ArticleUserDetail record);

    List<ArticleUserDetail> selectByExample(ArticleUserDetailExample example);

    ArticleUserDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleUserDetail record, @Param("example") ArticleUserDetailExample example);

    int updateByExample(@Param("record") ArticleUserDetail record, @Param("example") ArticleUserDetailExample example);

    int updateByPrimaryKeySelective(ArticleUserDetail record);

    int updateByPrimaryKey(ArticleUserDetail record);
}