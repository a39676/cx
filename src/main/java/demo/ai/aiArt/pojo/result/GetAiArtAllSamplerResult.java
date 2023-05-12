package demo.ai.aiArt.pojo.result;

import java.util.List;

import ai.aiArt.pojo.type.AiArtSamplerType;
import auxiliaryCommon.pojo.result.CommonResult;

public class GetAiArtAllSamplerResult extends CommonResult {

	private List<AiArtSamplerType> samplerList;

	public List<AiArtSamplerType> getSamplerList() {
		return samplerList;
	}

	public void setSamplerList(List<AiArtSamplerType> samplerList) {
		this.samplerList = samplerList;
	}

	@Override
	public String toString() {
		return "GetAiArtAllSamplerResult [samplerList=" + samplerList + "]";
	}

}
