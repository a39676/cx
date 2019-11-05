package demo.base.user.pojo.dto;

public class OtherUserInfoDTO {

	private String nickName;
	
	private String pk;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "OtherUserInfoParam [nickName=" + nickName + ", pk=" + pk + "]";
	}

}
