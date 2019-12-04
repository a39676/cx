package demo.article.article.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.article.article.pojo.bo.ArticleUUIDChannelStoreBO;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.result.GetArticleChannelsResult;

public interface ArticleChannelService {

	boolean loadArticleUUIDChannel(boolean mustRefresh);

	boolean loadArticleUUIDChannel();

	ArticleUUIDChannelStoreBO getArticleUUIDChannelStore();

	ArticleChannels findArticleChannelById(Long channelId);

	/**
	 * 获取公共频道以外， 根据用户ID查询其对应的闪现频道，私有频道
	 * @param param
	 * @return
	 */
	GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request);

	/**
	 * 随机刷新闪现频道是否出现
	 */
	void refreshArticleChannelIsFlash();

	boolean showLikeOrHate(Long channelId, Long userId);

	String loadChannelPrefix(Integer channelId);

	/** 以频道uuid获取对应频道id */
	Long getChannelIdByUUID(String uuid);

	/** 以频道id获取对应频道uuid */
	String getChannelUUIDById(Long channelId);

	/** 以用户id查找当前其可以访问的频道ID列表 */
	List<Integer> findChannelIdListByUserId(Long userId);

}
