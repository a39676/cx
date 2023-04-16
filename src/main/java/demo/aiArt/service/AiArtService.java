package demo.aiArt.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.pojo.dto.TextToImageDTO;
import demo.aiArt.pojo.result.TextToImageFromApiResult;

public interface AiArtService {

	CommonResult sendDtoToMq(TextToImageDTO dto);

	TextToImageFromApiResult txtToImgByJobId(Long jobId);

}
