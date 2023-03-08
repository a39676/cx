package demo.thirdPartyAPI.openAI.pojo.dto;

import java.util.List;

import demo.thirdPartyAPI.openAI.pojo.type.OpenAiModelType;

public class OpanAiChatCompletionResponseDTO {

	private List<OpanAiChatCompletionResponseChoiceDTO> choices;
	private OpanAiChatCompletionResponseUsageDTO usage;
	/** seconds of date */
	private Long created;
	private String id;
	/** {@link OpenAiModelType} */
	private String model;
	/** example: chat.completion */
	private String object;

	public List<OpanAiChatCompletionResponseChoiceDTO> getChoices() {
		return choices;
	}

	public void setChoices(List<OpanAiChatCompletionResponseChoiceDTO> choices) {
		this.choices = choices;
	}

	public OpanAiChatCompletionResponseUsageDTO getUsage() {
		return usage;
	}

	public void setUsage(OpanAiChatCompletionResponseUsageDTO usage) {
		this.usage = usage;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "OpanAiChatCompletionResponseDTO [choices=" + choices + ", usage=" + usage + ", created=" + created
				+ ", id=" + id + ", model=" + model + ", object=" + object + "]";
	}

}
