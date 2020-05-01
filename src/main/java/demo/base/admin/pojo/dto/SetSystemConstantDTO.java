package demo.base.admin.pojo.dto;

public class SetSystemConstantDTO {

	private String key;
	private String value;
	private Long validTimeSeconds;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getValidTimeSeconds() {
		return validTimeSeconds;
	}

	public void setValidTimeSeconds(Long validTimeSeconds) {
		this.validTimeSeconds = validTimeSeconds;
	}

	@Override
	public String toString() {
		return "SetSystemConstantDTO [key=" + key + ", value=" + value + ", validTimeSeconds=" + validTimeSeconds + "]";
	}

}
