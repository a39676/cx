package demo.articleComment.pojo.param.mapperParam;

import java.util.Date;

public class JustCommentParam {

	private Long userId;
	private Date startTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "JustCommentParam [userId=" + userId + ", startTime=" + startTime + "]";
	}

}
