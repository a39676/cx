package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleChannelKeyHostname;
import demo.article.article.pojo.po.ArticleChannelKeyHostnameExample;
import demo.article.article.pojo.po.ArticleChannelKeyHostnameKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleChannelKeyHostnameMapper {
    long countByExample(ArticleChannelKeyHostnameExample example);

    int deleteByExample(ArticleChannelKeyHostnameExample example);

    int deleteByPrimaryKey(ArticleChannelKeyHostnameKey key);

    int insert(ArticleChannelKeyHostname record);

    int insertSelective(ArticleChannelKeyHostname record);

    List<ArticleChannelKeyHostname> selectByExample(ArticleChannelKeyHostnameExample example);

    ArticleChannelKeyHostname selectByPrimaryKey(ArticleChannelKeyHostnameKey key);

    int updateByExampleSelective(@Param("record") ArticleChannelKeyHostname record, @Param("example") ArticleChannelKeyHostnameExample example);

    int updateByExample(@Param("record") ArticleChannelKeyHostname record, @Param("example") ArticleChannelKeyHostnameExample example);

    int updateByPrimaryKeySelective(ArticleChannelKeyHostname record);

    int updateByPrimaryKey(ArticleChannelKeyHostname record);
}