package demo.pmemo.pojo.dto;

public class SetPMemoDTO {

	private String content;
	private String validDateStr;
	private String redisKeyValue;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getValidDateStr() {
		return validDateStr;
	}

	public void setValidDateStr(String validDateStr) {
		this.validDateStr = validDateStr;
	}

	public String getRedisKeyValue() {
		return redisKeyValue;
	}

	public void setRedisKeyValue(String redisKeyValue) {
		this.redisKeyValue = redisKeyValue;
	}

	@Override
	public String toString() {
		return "SetPMemoDTO [content=" + content + ", validDateStr=" + validDateStr + ", redisKeyValue=" + redisKeyValue
				+ "]";
	}

}
