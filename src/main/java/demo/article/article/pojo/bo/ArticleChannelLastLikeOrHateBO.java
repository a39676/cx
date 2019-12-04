package demo.article.article.pojo.bo;

import java.util.Date;

public class ArticleChannelLastLikeOrHateBO {

	private Date lastChooseDate;

	private Boolean isLike;

	private Integer likeOrHateLevel;

	public Date getLastChooseDate() {
		return lastChooseDate;
	}

	public void setLastChooseDate(Date lastChooseDate) {
		this.lastChooseDate = lastChooseDate;
	}

	public Boolean getIsLike() {
		return isLike;
	}

	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}

	public Integer getLikeOrHateLevel() {
		return likeOrHateLevel;
	}

	public void setLikeOrHateLevel(Integer likeOrHateLevel) {
		this.likeOrHateLevel = likeOrHateLevel;
	}

	@Override
	public String toString() {
		return "ArticleChannelLastLikeOrHateBO [lastChooseDate=" + lastChooseDate + ", isLike=" + isLike
				+ ", likeOrHateLevel=" + likeOrHateLevel + "]";
	}

}
