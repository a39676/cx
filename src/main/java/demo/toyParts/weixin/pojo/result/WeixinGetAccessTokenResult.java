package demo.toyParts.weixin.pojo.result;

import net.sf.json.JSONObject;

public class WeixinGetAccessTokenResult extends WeixinCommonResult {

	private String accessToken;
	
	private JSONObject json;

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Override
	public WeixinGetAccessTokenResult buildResult(WeixinCommonResult oldResult) {
		WeixinGetAccessTokenResult result = new WeixinGetAccessTokenResult();
		if(oldResult == null) {
			return result;
		}
		result.setJson(oldResult.getJson());
		result.setMessage(oldResult.getMessage());
		result.setResult(oldResult.getResult());
		return result;
	}

	@Override
	public String toString() {
		return "WeixinGetAccessTokenResult [accessToken=" + accessToken + "]";
	}

}
