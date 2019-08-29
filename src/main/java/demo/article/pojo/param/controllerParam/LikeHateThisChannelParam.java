package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class LikeHateThisChannelParam implements CommonControllerParam {

	private String uuid;
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

	@Override
	public LikeHateThisChannelParam fromJson(JSONObject json) {
		LikeHateThisChannelParam param = new LikeHateThisChannelParam();
		if (json.containsKey("uuid")) {
			param.setUuid(json.getString("uuid"));
		}
		if (json.containsKey("likeOrHate") && NumericUtilCustom.matchInteger(json.getString("likeOrHate"))) {
			param.setLikeOrHate(json.getInt("likeOrHate"));
		}
		return param;
	}

}
