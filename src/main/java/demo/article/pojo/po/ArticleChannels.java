package demo.article.pojo.po;

import java.util.Date;

public class ArticleChannels {

	private Long channelId;

	private String channelName;

	private Date createTime;

	private Long channelPoint;

	private Integer channelFlashPoint;

	private Integer weights;

	private Integer channelType;

	private Boolean isFlash;

	private String image;
	
	private Boolean isDelete;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName == null ? null : channelName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getChannelPoint() {
		return channelPoint;
	}

	public void setChannelPoint(Long channelPoint) {
		this.channelPoint = channelPoint;
	}

	public Integer getChannelFlashPoint() {
		return channelFlashPoint;
	}

	public void setChannelFlashPoint(Integer channelFlashPoint) {
		this.channelFlashPoint = channelFlashPoint;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Integer getWeights() {
		return weights;
	}

	public void setWeights(Integer weights) {
		this.weights = weights;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Boolean getIsFlash() {
		return isFlash;
	}

	public void setIsFlash(Boolean isFlash) {
		this.isFlash = isFlash;
	}
	
	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "ArticleChannels [channelId=" + channelId + ", channelName=" + channelName + ", createTime=" + createTime
				+ ", channelPoint=" + channelPoint + ", channelFlashPoint=" + channelFlashPoint + ", weights=" + weights
				+ ", channelType=" + channelType + ", isFlash=" + isFlash + ", image=" + image + ", isDelete="
				+ isDelete + "]";
	}

}