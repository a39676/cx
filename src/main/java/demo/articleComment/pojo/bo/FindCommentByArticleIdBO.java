package demo.articleComment.pojo.bo;

import java.util.Date;

public class FindCommentByArticleIdBO {

	private Long articleCommentId;
	private String commentPrivateKey;
	private String path;
	private Date createTime;
	private Boolean isDelete;
	private Boolean isPass;
	private Boolean isReject;
	private String nickName;

	public Long getArticleCommentId() {
		return articleCommentId;
	}

	public void setArticleCommentId(Long articleCommentId) {
		this.articleCommentId = articleCommentId;
	}

	public String getCommentPrivateKey() {
		return commentPrivateKey;
	}

	public void setCommentPrivateKey(String commentPrivateKey) {
		this.commentPrivateKey = commentPrivateKey;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "FindCommentByArticleIdBO [articleCommentId=" + articleCommentId + ", commentPrivateKey="
				+ commentPrivateKey + ", path=" + path + ", createTime=" + createTime + ", isDelete=" + isDelete
				+ ", isPass=" + isPass + ", isReject=" + isReject + ", nickName=" + nickName + "]";
	}

}
