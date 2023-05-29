package demo.ai.aiArt.pojo.result;

import java.util.List;

import ai.aiArt.pojo.dto.ImageToImageDTO;
import ai.aiArt.pojo.vo.ImgVO;
import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public class AiArtGenerateImageQueryResult extends CommonResult {

	/** {@link TextToImageDTO} */
	/** {@link ImageToImageDTO} */
	private JSONObject parameter;
	private List<ImgVO> imgVoList;

	public JSONObject getParameter() {
		return parameter;
	}

	public void setParameter(JSONObject parameter) {
		this.parameter = parameter;
	}

	public List<ImgVO> getImgVoList() {
		return imgVoList;
	}

	public void setImgVoList(List<ImgVO> imgVoList) {
		this.imgVoList = imgVoList;
	}

	@Override
	public String toString() {
		return "AiArtGenerateImageQueryResult [parameter=" + parameter + ", imgVoList=" + imgVoList + "]";
	}

}
