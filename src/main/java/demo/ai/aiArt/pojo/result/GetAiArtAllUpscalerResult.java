package demo.ai.aiArt.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.vo.AiArtUpscalerVO;

public class GetAiArtAllUpscalerResult extends CommonResult {

	private List<AiArtUpscalerVO> upscalerList;

	public List<AiArtUpscalerVO> getUpscalerList() {
		return upscalerList;
	}

	public void setUpscalerList(List<AiArtUpscalerVO> upscalerList) {
		this.upscalerList = upscalerList;
	}

	@Override
	public String toString() {
		return "GetAiArtAllUpscalerResult [upscalerList=" + upscalerList + "]";
	}

}
