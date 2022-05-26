package demo.base.user.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class ValidUserRegistResult extends CommonResult {

	private String username;
	private String nickname;
	private String pwd;
	private String pwdRepeat;
	private String email;
	private String mobile;
	private String reservationInformation;
	private String qq;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwdRepeat() {
		return pwdRepeat;
	}

	public void setPwdRepeat(String pwdRepeat) {
		this.pwdRepeat = pwdRepeat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReservationInformation() {
		return reservationInformation;
	}

	public void setReservationInformation(String reservationInformation) {
		this.reservationInformation = reservationInformation;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Override
	public String toString() {
		return "ValidUserRegistResult [username=" + username + ", nickname=" + nickname + ", pwd=" + pwd
				+ ", pwdRepeat=" + pwdRepeat + ", email=" + email + ", mobile=" + mobile + ", reservationInformation="
				+ reservationInformation + ", qq=" + qq + ", getCode()=" + getCode() + ", getResult()=" + getResult()
				+ ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
