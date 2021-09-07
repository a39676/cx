package demo.article.articleComment.pojo.type;

public enum ArticleCommentResultType {
	
	evaluationVoteSuccess("已评~", "0"),
	feedbackReciveSuccess("感谢您的反馈!", "0"),
	articleCommentDeleteSuccess("评论已删除", "0"),
	articleCommentDeleteError("评论删除异常", "0"),
	articleCommentPassSuccess("评论已通过", "0"),
	articleCommentPassError("评论审核异常", "0"),
	articleCommentRejectSuccess("评论已拒绝", "0"),
	articleCommentRejectError("评论拒绝异常", "0"),
		
	hadEvaluationVoted ("人海中已留下你的足迹...", "-7-7"),
	justComment("刚刚才发过言...要不坐下先喝杯咖啡...", "-7-8"),
	
	;
	
	
	private String resultName;
	private String resultCode;
	
	ArticleCommentResultType(String name, String code) {
		this.resultName = name;
		this.resultCode = code;
	}

	public String getName() {
		return this.resultName;
	}
	
	public String getCode() {
		return this.resultCode;
	}
}
