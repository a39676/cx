package demo.article.article.service;

import javax.servlet.http.HttpServletRequest;

import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.result.GetArticleChannelsResult;

public interface ArticleChannelService {

	ArticleChannels findArticleChannelById(Long channelId);

	/**
	 * 获取公共频道以外， 根据用户ID查询其对应的闪现频道，私有频道
	 * @param param
	 * @return
	 */
	GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request);

	String loadChannelPrefix(Integer channelId);

}
