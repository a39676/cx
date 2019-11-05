package demo.article.pojo.param.controllerParam;

import io.swagger.annotations.ApiModelProperty;

public class LikeHateThisChannelParam {

	private String uuid;
	@ApiModelProperty(hidden = true)
	private Long userId;
	private Integer likeOrHate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getLikeOrHate() {
		return likeOrHate;
	}

	public void setLikeOrHate(Integer likeOrHate) {
		this.likeOrHate = likeOrHate;
	}

	@Override
	public String toString() {
		return "LikeHateThisChannelParam [uuid=" + uuid + ", userId=" + userId + ", likeOrHate=" + likeOrHate + "]";
	}

}
