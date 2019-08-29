package demo.article.pojo.param.mapperParam;

public class UpdateArticleUserCoefficientParam {

	private Long channelId;
	private Long userId;
	private int coefficient = 0;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public String toString() {
		return "UpdateArticleUserCoefficientParam [channelId=" + channelId + ", userId=" + userId + ", coefficient="
				+ coefficient + "]";
	}

}
