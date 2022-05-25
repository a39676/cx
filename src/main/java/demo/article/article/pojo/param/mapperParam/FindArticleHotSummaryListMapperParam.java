package demo.article.article.pojo.param.mapperParam;

import java.util.ArrayList;
import java.util.List;

public class FindArticleHotSummaryListMapperParam {

	private List<Long> channelIdList;

	public List<Long> getChannelIdList() {
		return channelIdList;
	}

	public void setChannelIdList(List<Long> channelIdList) {
		this.channelIdList = channelIdList;
	}
	
	public void addChannelId(Long channelId) {
		if(channelId == null) {
			return;
		}
		if(channelIdList == null) {
			channelIdList = new ArrayList<>();
		}
		channelIdList.add(channelId);
	}

	@Override
	public String toString() {
		return "FindArticleHotSummaryListMapperParam []";
	}

}
