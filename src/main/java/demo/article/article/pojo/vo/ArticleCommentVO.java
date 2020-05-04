package demo.article.article.pojo.vo;

import java.util.HashMap;

public class ArticleCommentVO {

	private Long articleCommentId;
	private String createTimeStr;
	private String nickName;
	private String content;
	private Boolean isDelete;
	private Boolean isPass;
	private Boolean isReject;
	private HashMap<Integer, ArticleEvaluationCounterVO> evaluationCodeAndCount = new HashMap<Integer, ArticleEvaluationCounterVO>();

	public Long getArticleCommentId() {
		return articleCommentId;
	}

	public void setArticleCommentId(Long articleCommentId) {
		this.articleCommentId = articleCommentId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public HashMap<Integer, ArticleEvaluationCounterVO> getEvaluationCodeAndCount() {
		return evaluationCodeAndCount;
	}

	public void setEvaluationCodeAndCount(HashMap<Integer, ArticleEvaluationCounterVO> evaluationCodeAndCount) {
		this.evaluationCodeAndCount = evaluationCodeAndCount;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	@Override
	public String toString() {
		return "ArticleCommentVO [articleCommentId=" + articleCommentId + ", createTimeStr=" + createTimeStr
				+ ", nickName=" + nickName + ", content=" + content + ", isDelete=" + isDelete + ", isPass=" + isPass
				+ ", isReject=" + isReject + ", evaluationCodeAndCount=" + evaluationCodeAndCount + "]";
	}

}
