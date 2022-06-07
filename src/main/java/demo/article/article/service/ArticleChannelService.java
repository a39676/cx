package demo.article.article.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.dto.ArticleChannelKeyHostnameIdDTO;
import demo.article.article.pojo.dto.ArticleChannelManagerDTO;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.result.GetArticleChannelsResult;
import demo.article.article.pojo.vo.ArticleChannelVO;

public interface ArticleChannelService {

	ArticleChannels findArticleChannelById(Long channelId);

	/**
	 * 获取公共频道以外,  根据用户ID查询其对应的闪现频道, 私有频道
	 * @param param
	 * @return
	 */
	GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request);

	String loadChannelPrefix(Integer channelId);

	List<ArticleChannelVO> findArticleChannel();

	CommonResult articleChannelManager(ArticleChannelManagerDTO dto);

	ModelAndView articleChannelManagerView();

	boolean containThisChannel(HttpServletRequest request, Long channelId);

	CommonResult editChannelKeyHostname(ArticleChannelKeyHostnameIdDTO dto);

	void loadPublicChannels();

	boolean canVisitThisChannel(Long userId, Long channelId);

	List<ArticleChannelVO> getPrivateChannels(Long userId);

}
