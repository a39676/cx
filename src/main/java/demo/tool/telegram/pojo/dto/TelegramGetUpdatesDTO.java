package demo.tool.telegram.pojo.dto;

import java.util.List;

public class TelegramGetUpdatesDTO {

	private Boolean ok;
	private List<TelegramUpdateMessageDTO> result;

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public List<TelegramUpdateMessageDTO> getResult() {
		return result;
	}

	public void setResult(List<TelegramUpdateMessageDTO> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TelegramReceiveMessageResultDTO [ok=" + ok + ", result=" + result + "]";
	}

}
