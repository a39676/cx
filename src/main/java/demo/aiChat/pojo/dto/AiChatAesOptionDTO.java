package demo.aiChat.pojo.dto;

public class AiChatAesOptionDTO {

	private String aesKey;
	private String aesInitVector;

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getAesInitVector() {
		return aesInitVector;
	}

	public void setAesInitVector(String aesInitVector) {
		this.aesInitVector = aesInitVector;
	}

	@Override
	public String toString() {
		return "AiChatAesOptionDTO [aesKey=" + aesKey + ", aesInitVector=" + aesInitVector + "]";
	}

}
