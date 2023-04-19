package demo.aiArt.service;

import java.util.List;

import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.GetJobResultList;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.aiArt.pojo.result.SendTextToImgJobResult;
import demo.common.pojo.dto.BaseDTO;

public interface AiArtService {

	CommonResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto);

	CommonResult txtToImgByJobId(Long jobId);

	void rerun();

	GetJobResultList getJobResultListByTmpKey(String userTmpKey);

	SendTextToImgJobResult sendTextToImgFromApiDtoToMq(TextToImageFromApiDTO dto);

	GetJobResultList getJobResultVoByJobPk(BaseDTO dto);

	void deleteParameterFile();

	List<AiArtTextToImageJobRecord> __getJobResultPage(String lastJobPk);

}
