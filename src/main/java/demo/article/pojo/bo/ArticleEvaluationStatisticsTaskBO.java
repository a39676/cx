package demo.article.pojo.bo;

import java.util.ArrayList;
import java.util.List;

public class ArticleEvaluationStatisticsTaskBO {

	private Long postObjectId;
	private Integer evaluationCode;
	private Integer evaluationCount;
	private String userIds;
	private String evaluationCacheIds;
	private Long channelId;
	private List<Long> userIdList = new ArrayList<Long>();
	private List<Long> evaluationCacheIdList = new ArrayList<Long>();

	public Integer getEvaluationCount() {
		return evaluationCount;
	}

	public void setEvaluationCount(Integer evaluationCount) {
		this.evaluationCount = evaluationCount;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getPostObjectId() {
		return postObjectId;
	}

	public void setPostObjectId(Long postObjectId) {
		this.postObjectId = postObjectId;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public void setEvaluationCode(Integer evaluationCode) {
		this.evaluationCode = evaluationCode;
	}

	public List<Long> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}

	public List<Long> getEvaluationCacheIdList() {
		return evaluationCacheIdList;
	}

	public void setEvaluationCacheIdList(List<Long> evaluationCacheIdList) {
		this.evaluationCacheIdList = evaluationCacheIdList;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getEvaluationCacheIds() {
		return evaluationCacheIds;
	}

	public void setEvaluationCacheIds(String evaluationCacheIds) {
		this.evaluationCacheIds = evaluationCacheIds;
	}

	public void setUserIdListFromUserIds() {
		String[] ids = this.userIds.split(",");
		for (String id : ids) {
			if (id.matches("\\d+")) {
				this.userIdList.add(Long.parseLong(id));
			}
		}
	}

	public void evaluationCacheIdList() {
		String[] ids = this.evaluationCacheIds.split(",");
		for (String id : ids) {
			if (id.matches("\\d+")) {
				this.evaluationCacheIdList.add(Long.parseLong(id));
			}
		}
	}

	@Override
	public String toString() {
		return "ArticleEvaluationStatisticsTaskBO [postObjectId=" + postObjectId + ", evaluationCode=" + evaluationCode
				+ ", evaluationCount=" + evaluationCount + ", userIds=" + userIds + ", evaluationCacheIds="
				+ evaluationCacheIds + ", channelId=" + channelId + ", userIdList=" + userIdList
				+ ", evaluationCacheIdList=" + evaluationCacheIdList + "]";
	}

}
