package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class CreatingArticleParam implements CommonControllerParam {

//	private String uuid;

	private Long userId;

	@Override
	public CreatingArticleParam fromJson(JSONObject j) {
		CreatingArticleParam p = new CreatingArticleParam();

//		if (j.containsKey("uuid")) {
//			p.setUuid(j.getString("uuid"));
//		}

		return p;
	}

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
