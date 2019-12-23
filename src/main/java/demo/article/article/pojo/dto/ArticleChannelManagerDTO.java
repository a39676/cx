package demo.article.article.pojo.dto;

import demo.article.article.pojo.type.ArticleChannelOperationalType;
import demo.article.article.pojo.type.ArticleChannelType;

public class ArticleChannelManagerDTO {

	/** {@link ArticleChannelOperationalType} */
	private Integer operationalType;
	private Long channelId;
	private String channelName;
	/** {@link ArticleChannelType } */
	private Integer channelType;
	private Integer weights;
	private boolean isDelete = false;

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getWeights() {
		return weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getOperationalType() {
		return operationalType;
	}

	public void setOperationalType(Integer operationalType) {
		this.operationalType = operationalType;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	@Override
	public String toString() {
		return "ArticleChannelManagerDTO [operationalType=" + operationalType + ", channelId=" + channelId
				+ ", channelName=" + channelName + ", channelType=" + channelType + ", weights=" + weights
				+ ", isDelete=" + isDelete + "]";
	}

}
