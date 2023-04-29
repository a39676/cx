package demo.ai.aiArt.pojo.dto;

import java.util.Map;

public class AiUserDetailInJsonDTO {

	private Long userId;

	/** yyyyMM : 123.456 */
	private Map<String, Double> usedTokenMap;
	
	private String remark;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Map<String, Double> getUsedTokenMap() {
		return usedTokenMap;
	}

	public void setUsedTokenMap(Map<String, Double> usedTokenMap) {
		this.usedTokenMap = usedTokenMap;
	}
	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AiUserDetailInJsonDTO [userId=" + userId + ", usedTokenMap=" + usedTokenMap + ", remark=" + remark
				+ "]";
	}

}
