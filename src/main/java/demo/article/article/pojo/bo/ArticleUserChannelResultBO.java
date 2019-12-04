package demo.article.article.pojo.bo;

import java.util.Date;

public class ArticleUserChannelResultBO {

	private Long articleChannelId;

	private Integer weights;

	private Long userId;

	private Integer coefficient;

	private String channelName;

	private Long channelPoint;

	private Integer channelFlashPoint;

	private Date isFlashUpdateTime;

	private Boolean flash = false;

	private Integer channelType;

	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getArticleChannelId() {
		return articleChannelId;
	}

	public void setArticleChannelId(Long articleChannelId) {
		this.articleChannelId = articleChannelId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Integer coefficient) {
		this.coefficient = coefficient;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public Boolean getFlash() {
		return flash;
	}

	public void setFlash(Boolean flash) {
		this.flash = flash;
	}

	public Date getIsFlashUpdateTime() {
		return isFlashUpdateTime;
	}

	public void setIsFlashUpdateTime(Date isFlashUpdateTime) {
		this.isFlashUpdateTime = isFlashUpdateTime;
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

	@Override
	public String toString() {
		return "ArticleUserChannelResultBO [articleChannelId=" + articleChannelId + ", weights=" + weights + ", userId="
				+ userId + ", coefficient=" + coefficient + ", channelName=" + channelName + ", channelPoint="
				+ channelPoint + ", channelFlashPoint=" + channelFlashPoint + ", isFlashUpdateTime=" + isFlashUpdateTime
				+ ", flash=" + flash + ", channelType=" + channelType + ", image=" + image + "]";
	}

}
