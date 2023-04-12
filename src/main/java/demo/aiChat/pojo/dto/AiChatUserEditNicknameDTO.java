package demo.aiChat.pojo.dto;

public class AiChatUserEditNicknameDTO {

	private String userPk;
	private String nickname;

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "AiChatUserEditNicknameDTO [userPk=" + userPk + ", nickname=" + nickname + "]";
	}

}
