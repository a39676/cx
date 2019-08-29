package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class FindArticleUserDetailParam implements CommonControllerParam {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public FindArticleUserDetailParam fromJson(JSONObject json) {
		if(json.containsKey("userId") && NumericUtilCustom.matchInteger(json.getString("userId"))) {
			this.userId = json.getLong("userId");
		}
		return this;
	}

}
