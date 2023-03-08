package demo.thirdPartyAPI.openAI.pojo.dto;

public class SendChatingMessageFromWechatDTO {

	private String uid;
	private String msg;
	private String phoneNumber;
	private String headImage;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@Override
	public String toString() {
		return "SendChatingMessageDTO [uid=" + uid + ", msg=" + msg + ", phoneNumber=" + phoneNumber + ", headImage="
				+ headImage + "]";
	}

}
