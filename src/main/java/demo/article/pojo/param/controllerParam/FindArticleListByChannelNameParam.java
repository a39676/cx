package demo.article.pojo.param.controllerParam;

import org.apache.commons.lang.StringUtils;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class FindArticleListByChannelNameParam implements CommonControllerParam {

	private String channelName;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public FindArticleListByChannelNameParam fromJson(JSONObject json) {
		FindArticleListByChannelNameParam param = new FindArticleListByChannelNameParam();
		if (json.containsKey("channelName") && StringUtils.isNotBlank(json.getString("channelName"))) {
			param.setChannelName(json.getString("channelName"));
		}
		return param;
	}

}
