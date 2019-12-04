package demo.article.article.mapper;

import java.util.List;

import demo.article.article.pojo.param.mapperParam.FindArticleChannelsParam;
import demo.article.article.pojo.param.mapperParam.UpdateChannelIsFlashParam;
import demo.article.article.pojo.param.mapperParam.UpdateChannelPointByArticleIdParam;
import demo.article.article.pojo.param.mapperParam.UpdateChannelPointParam;
import demo.article.article.pojo.po.ArticleChannels;

public interface ArticleChannelsMapper {
	int insert(ArticleChannels record);

	int insertSelective(ArticleChannels record);

	List<ArticleChannels> findPublicChannels();
	
	List<ArticleChannels> findIsFlashChannels();
	
	List<ArticleChannels> findFlashChannels();
	
	int updateChannelPointByArticleId(UpdateChannelPointByArticleIdParam param);

	List<ArticleChannels> findArticleChannels(FindArticleChannelsParam param);

	int updateChannelPoint(UpdateChannelPointParam param);
	
	ArticleChannels findArticleChannelById(FindArticleChannelsParam param);

	int updateChannelIsFlash(UpdateChannelIsFlashParam param);
}