package demo.weixin.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;
import demo.weixin.pojo.type.WeixinResultType;
import net.sf.json.JSONObject;

public class WeixinCommonResult extends CommonResultCX {

	private JSONObject json;

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public void fillWithResult(WeixinResultType resultType) {
		this.setResult(resultType.getCode());
		this.setMessage(resultType.getName());
	}

	protected WeixinCommonResult buildResult(WeixinCommonResult oldResult) {
		return oldResult;
	}
}
