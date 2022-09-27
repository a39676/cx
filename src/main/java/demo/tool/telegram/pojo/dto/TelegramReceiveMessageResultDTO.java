package demo.tool.telegram.pojo.dto;

import java.util.List;

public class TelegramReceiveMessageResultDTO {

	private Boolean ok;
	private List<TelegramGetUpdateMessageDTO> result;

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public List<TelegramGetUpdateMessageDTO> getResult() {
		return result;
	}

	public void setResult(List<TelegramGetUpdateMessageDTO> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TelegramReceiveMessageResultDTO [ok=" + ok + ", result=" + result + "]";
	}

}
