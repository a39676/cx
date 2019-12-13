package demo.article.article.mapper;

import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.po.ArticleChannelsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleChannelsMapper {
    long countByExample(ArticleChannelsExample example);

    int deleteByExample(ArticleChannelsExample example);

    int deleteByPrimaryKey(Long channelId);

    int insert(ArticleChannels record);

    int insertSelective(ArticleChannels record);

    List<ArticleChannels> selectByExample(ArticleChannelsExample example);

    ArticleChannels selectByPrimaryKey(Long channelId);

    int updateByExampleSelective(@Param("record") ArticleChannels record, @Param("example") ArticleChannelsExample example);

    int updateByExample(@Param("record") ArticleChannels record, @Param("example") ArticleChannelsExample example);

    int updateByPrimaryKeySelective(ArticleChannels record);

    int updateByPrimaryKey(ArticleChannels record);
}