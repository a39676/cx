package demo.article.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class SetArticleHotParam implements CommonControllerParam {

	private String pk;

	private Integer hotLevel = 0;

	private Long hotMinutes;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getHotLevel() {
		return hotLevel;
	}

	public void setHotLevel(Integer hotLevel) {
		this.hotLevel = hotLevel;
	}

	public Long getHotMinutes() {
		return hotMinutes;
	}

	public void setHotMinutes(Long hotMinutes) {
		this.hotMinutes = hotMinutes;
	}

	@Override
	public String toString() {
		return "SetArticleHotParam [pk=" + pk + "]";
	}

	@Override
	public SetArticleHotParam fromJson(JSONObject json) {
		if (json.containsKey("pk")) {
			this.pk = json.getString("pk");
		}
		if (json.containsKey("hotLevel") && NumericUtilCustom.matchInteger(json.getString("hotLevel"))) {
			this.hotLevel = json.getInt("hotLevel");
		}
		if (json.containsKey("hotMinutes") && NumericUtilCustom.matchInteger(json.getString("hotMinutes"))) {
			this.hotMinutes = json.getLong("hotMinutes");
		}
		return this;
	}

}
