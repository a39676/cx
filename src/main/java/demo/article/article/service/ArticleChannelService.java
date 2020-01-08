package demo.article.article.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import demo.article.article.pojo.dto.ArticleChannelKeyHostnameIdDTO;
import demo.article.article.pojo.dto.ArticleChannelManagerDTO;
import demo.article.article.pojo.po.ArticleChannels;
import demo.article.article.pojo.result.GetArticleChannelsResult;
import demo.article.article.pojo.vo.ArticleChannelVO;
import demo.baseCommon.pojo.result.CommonResultCX;

public interface ArticleChannelService {

	ArticleChannels findArticleChannelById(Long channelId);

	/**
	 * 获取公共频道以外， 根据用户ID查询其对应的闪现频道，私有频道
	 * @param param
	 * @return
	 */
	GetArticleChannelsResult getArticleChannelsDynamic(HttpServletRequest request);

	String loadChannelPrefix(Integer channelId);

	List<ArticleChannelVO> findArticleChannel();

	CommonResultCX articleChannelManager(ArticleChannelManagerDTO dto);

	ModelAndView articleChannelManagerView();

	boolean containThisChannel(HttpServletRequest request, Long channelId);

	CommonResultCX editChannelKeyHostname(ArticleChannelKeyHostnameIdDTO dto);

}
