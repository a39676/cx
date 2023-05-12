package demo.ai.aiArt.pojo.result;

import java.util.List;

import ai.pojo.vo.AiArtModelVO;
import auxiliaryCommon.pojo.result.CommonResult;

public class GetAiArtAllModelListResult extends CommonResult {

	private List<AiArtModelVO> modelList;

	public List<AiArtModelVO> getModelList() {
		return modelList;
	}

	public void setModelList(List<AiArtModelVO> modelList) {
		this.modelList = modelList;
	}

	@Override
	public String toString() {
		return "GetAiArtAllModelListForPublicUserResult [modelList=" + modelList + "]";
	}

}
