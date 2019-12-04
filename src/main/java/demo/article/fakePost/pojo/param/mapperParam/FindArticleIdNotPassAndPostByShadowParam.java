package demo.article.fakePost.pojo.param.mapperParam;

import java.util.List;

public class FindArticleIdNotPassAndPostByShadowParam {

	private Integer limit = 1;
	private Integer channelId;
	private List<Long> userIdList;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public List<Long> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}

	@Override
	public String toString() {
		return "FindArticleIdNotPassAndPostByShadowParam [limit=" + limit + ", channelId=" + channelId + "]";
	}

}
