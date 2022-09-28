package demo.tool.telegram.pojo.dto;

import java.util.List;

public class TelegramGetUpdatesDTO {

	private Boolean ok;
	private List<UpdateMessageDTO> result;

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public List<UpdateMessageDTO> getResult() {
		return result;
	}

	public void setResult(List<UpdateMessageDTO> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TelegramReceiveMessageResultDTO [ok=" + ok + ", result=" + result + "]";
	}

}
