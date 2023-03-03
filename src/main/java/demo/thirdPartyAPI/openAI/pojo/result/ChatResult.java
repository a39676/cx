package demo.thirdPartyAPI.openAI.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class ChatResult extends CommonResult {

	private String content;
	private String datetimeStr;
	private String finishReason;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDatetimeStr() {
		return datetimeStr;
	}

	public void setDatetimeStr(String datetimeStr) {
		this.datetimeStr = datetimeStr;
	}

	public String getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(String finishReason) {
		this.finishReason = finishReason;
	}

	@Override
	public String toString() {
		return "ChatResult [content=" + content + ", datetimeStr=" + datetimeStr + ", finishReason=" + finishReason
				+ "]";
	}

}
