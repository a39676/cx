package demo.base.user.pojo.vo;

public class UsersDetailForAdminVO extends UsersDetailVO {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UsersDetailForAdminVO [userId=" + userId + ", getHeadImage()=" + getHeadImage() + ", getNickName()="
				+ getNickName() + ", getGender()=" + getGender() + ", getQq()=" + getQq() + ", getEmail()=" + getEmail()
				+ ", getMobile()=" + getMobile() + ", getLastLoginTime()=" + getLastLoginTime() + ", getPrivateLevel()="
				+ getPrivateLevel() + ", getReservationInformation()=" + getReservationInformation()
				+ ", getPrivateMessage()=" + getPrivateMessage() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
