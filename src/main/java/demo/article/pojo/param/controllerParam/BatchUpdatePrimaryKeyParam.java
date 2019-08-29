package demo.article.pojo.param.controllerParam;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class BatchUpdatePrimaryKeyParam implements CommonControllerParam {

	private Date startTime;
	private Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "BatchUpdatePrimaryKeyParam [startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	@Override
	public BatchUpdatePrimaryKeyParam fromJson(JSONObject j) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(j.toString(), BatchUpdatePrimaryKeyParam.class);
	}

}
