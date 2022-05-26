package demo.toyParts.weixin.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.weixin.pojo.type.WeixinResultType;
import net.sf.json.JSONObject;

public class WeixinCommonResult extends CommonResult {

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
