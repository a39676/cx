package demo.article.article.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.vo.ArticleChannelVO;

public class GetArticleChannelsResult extends CommonResult {

	private List<ArticleChannelVO> channelList;

	public List<ArticleChannelVO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<ArticleChannelVO> channelList) {
		this.channelList = channelList;
	}

	@Override
	public String toString() {
		return "GetArticleChannelsBO [channelList=" + channelList + "]";
	}

}
