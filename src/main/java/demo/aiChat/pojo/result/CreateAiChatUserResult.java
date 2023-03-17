package demo.aiChat.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class CreateAiChatUserResult extends CommonResult {

	private Long aiChatUserId;

	private Long tmpKey;

	public Long getAiChatUserId() {
		return aiChatUserId;
	}

	public void setAiChatUserId(Long aiChatUserId) {
		this.aiChatUserId = aiChatUserId;
	}

	public Long getTmpKey() {
		return tmpKey;
	}

	public void setTmpKey(Long tmpKey) {
		this.tmpKey = tmpKey;
	}

	@Override
	public String toString() {
		return "CreateAiChatUserResult [aiChatUserId=" + aiChatUserId + ", tmpKey=" + tmpKey + "]";
	}

}
