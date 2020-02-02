package demo.article.article.pojo.param.controllerParam;

public class CreatingArticleParam {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CreatingArticleParam [userId=" + userId + "]";
	}

}
