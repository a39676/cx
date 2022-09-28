package demo.tool.telegram.pojo.dto;

public class UpdateMessageResponseStoreDTO extends TelegramGetUpdatesDTO {

	private Long lastMsgUpdateId;

	public Long getLastMsgUpdateId() {
		return lastMsgUpdateId;
	}

	public void setLastMsgUpdateId(Long lastMsgUpdateId) {
		this.lastMsgUpdateId = lastMsgUpdateId;
	}

	@Override
	public String toString() {
		return "TelegramGetUpdateMessageResultStoreDTO [lastMsgUpdateId=" + lastMsgUpdateId + ", getOk()=" + getOk()
				+ ", getResult()=" + getResult() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

}
