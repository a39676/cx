package demo.aiArt.pojo.dto;

import demo.aiArt.pojo.type.AiArtSamplerType;

public class TextToImageDTO {

	private String prompts;
	private String negativePrompts;
	/** {@link AiArtSamplerType} */
	private String sampler = "DPM++ 2M Karras";
	private Integer wedith = 300;
	private Integer height = 600;
	private Integer cfgScale = 7;
	private Integer steps = 30;
	private Integer batchSize = 1;
	private Long seed = Long.parseLong("-1");

	public String getPrompts() {
		return prompts;
	}

	public void setPrompts(String prompts) {
		this.prompts = prompts;
	}

	public String getNegativePrompts() {
		return negativePrompts;
	}

	public void setNegativePrompts(String negativePrompts) {
		this.negativePrompts = negativePrompts;
	}

	public String getSampler() {
		return sampler;
	}

	public void setSampler(String sampler) {
		this.sampler = sampler;
	}

	public Integer getWedith() {
		return wedith;
	}

	public void setWedith(Integer wedith) {
		this.wedith = wedith;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getCfgScale() {
		return cfgScale;
	}

	public void setCfgScale(Integer cfgScale) {
		this.cfgScale = cfgScale;
	}

	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public Long getSeed() {
		return seed;
	}

	public void setSeed(Long seed) {
		this.seed = seed;
	}

	@Override
	public String toString() {
		return "TextToImageDTO [prompts=" + prompts + ", negativePrompts=" + negativePrompts + ", sampler=" + sampler
				+ ", wedith=" + wedith + ", height=" + height + ", cfgScale=" + cfgScale + ", steps=" + steps
				+ ", batchSize=" + batchSize + ", seed=" + seed + "]";
	}

}
