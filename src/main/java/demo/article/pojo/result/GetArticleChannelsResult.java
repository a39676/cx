package demo.article.pojo.result;

import java.util.List;

import demo.article.pojo.vo.ArticleChannelVO;
import demo.baseCommon.pojo.result.CommonResult;

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
