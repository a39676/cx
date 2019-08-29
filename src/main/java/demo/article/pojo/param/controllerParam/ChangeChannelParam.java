package demo.article.pojo.param.controllerParam;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class ChangeChannelParam implements CommonControllerParam {

	private String pk;
	private String uuid;
	private Long channelId;
	private Long articleId;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "ChangeChannelParam [pk=" + pk + ", uuid=" + uuid + ", channelId=" + channelId + ", articleId="
				+ articleId + "]";
	}

	@Override
	public ChangeChannelParam fromJson(JSONObject j) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(j.toString(), ChangeChannelParam.class);
	}

}