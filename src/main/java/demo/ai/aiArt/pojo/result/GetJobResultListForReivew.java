package demo.ai.aiArt.pojo.result;

import java.util.Map;

import ai.aiArt.pojo.result.GetJobResultListForAdmin;
import demo.ai.aiArt.pojo.dto.AiUserDetailInRedisDTO;

public class GetJobResultListForReivew extends GetJobResultListForAdmin {

	private Map<String, AiUserDetailInRedisDTO> userDetailInRedisMap;

	public Map<String, AiUserDetailInRedisDTO> getUserDetailInRedisMap() {
		return userDetailInRedisMap;
	}

	public void setUserDetailInRedisMap(Map<String, AiUserDetailInRedisDTO> userDetailInRedisMap) {
		this.userDetailInRedisMap = userDetailInRedisMap;
	}

	@Override
	public String toString() {
		return "GetJobResultListForReivew [userDetailInRedisMap=" + userDetailInRedisMap + "]";
	}

}
