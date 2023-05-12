package demo.ai.aiArt.pojo.result;

import java.util.List;

import ai.automatic1111.pojo.type.AiArtUpscalerType;
import auxiliaryCommon.pojo.result.CommonResult;

public class GetAiArtAllUpscalerResult extends CommonResult {

	private List<AiArtUpscalerType> upscalerList;

	public List<AiArtUpscalerType> getUpscalerList() {
		return upscalerList;
	}

	public void setUpscalerList(List<AiArtUpscalerType> upscalerList) {
		this.upscalerList = upscalerList;
	}

	@Override
	public String toString() {
		return "GetAiArtAllUpscalerResult [upscalerList=" + upscalerList + "]";
	}

}
