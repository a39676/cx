package demo.aiArt.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.aiArt.pojo.dto.TextToImageFromWechatDTO;
import demo.aiArt.pojo.result.GetJobResultList;

public interface AiArtService {

	CommonResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto);

	CommonResult txtToImgByJobId(Long jobId);

	void rerun();

	GetJobResultList getJobResultListByTmpKey(String userTmpKey);

	CommonResult sendTextToImgFromApiDtoToMq(TextToImageFromApiDTO dto);

}
