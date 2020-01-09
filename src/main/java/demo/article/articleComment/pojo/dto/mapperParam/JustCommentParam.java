package demo.article.articleComment.pojo.dto.mapperParam;

import java.util.Date;

/**
 * 
 * 查询是否刚刚评论过
 * TODO
 * 2019-12-19 
 * 发现, 应该转移到 redis
 *
 */
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
