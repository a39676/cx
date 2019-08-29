package demo.article.pojo.param.mapperParam;

public class InsertOrUpdateUserCoefficientParam {

	private Long articleChannelId;
	private Long userId;
	private Integer coefficient;
	
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
}
