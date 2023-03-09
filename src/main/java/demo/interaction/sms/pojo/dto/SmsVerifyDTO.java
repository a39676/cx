package demo.interaction.sms.pojo.dto;

import java.time.LocalDateTime;

public class SmsVerifyDTO {

	private String code;

	private String phoneNumber;

	private LocalDateTime createTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SmsVerifyDTO [code=" + code + ", phoneNumber=" + phoneNumber + ", createTime=" + createTime + "]";
	}

}
