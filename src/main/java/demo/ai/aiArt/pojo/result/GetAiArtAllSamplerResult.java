package demo.ai.aiArt.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.vo.AiArtSamplerVO;

public class GetAiArtAllSamplerResult extends CommonResult {

	private List<AiArtSamplerVO> samplerList;

	public List<AiArtSamplerVO> getSamplerList() {
		return samplerList;
	}

	public void setSamplerList(List<AiArtSamplerVO> samplerList) {
		this.samplerList = samplerList;
	}

	@Override
	public String toString() {
		return "GetAiArtAllSamplerResult [samplerList=" + samplerList + "]";
	}

}
