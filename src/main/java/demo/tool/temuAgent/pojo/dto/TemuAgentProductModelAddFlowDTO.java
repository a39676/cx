package demo.tool.temuAgent.pojo.dto;

import java.math.BigDecimal;

import demo.tool.temuAgent.pojo.type.TemuAgentProductFlowType;

public class TemuAgentProductModelAddFlowDTO {

	private Long modelId;
	/** {@link TemuAgentProductFlowType} */
	private Integer flowTypeCode;
	private Integer counting;
	private BigDecimal price;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Integer getFlowTypeCode() {
		return flowTypeCode;
	}

	public void setFlowTypeCode(Integer flowTypeCode) {
		this.flowTypeCode = flowTypeCode;
	}

	public Integer getCounting() {
		return counting;
	}

	public void setCounting(Integer counting) {
		this.counting = counting;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "TemuAgentProductModelAddFlowDTO [modelId=" + modelId + ", flowTypeCode=" + flowTypeCode + ", counting="
				+ counting + ", price=" + price + "]";
	}

}
