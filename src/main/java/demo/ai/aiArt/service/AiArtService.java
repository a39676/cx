package demo.ai.aiArt.service;

import java.util.List;

import ai.aiArt.pojo.dto.TextToImageDTO;
import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.GetJobResultListForUser;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.pojo.vo.AiArtModelVO;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.pojo.result.GetAiArtAllModelListResult;
import demo.ai.aiArt.pojo.result.GetAiArtAllSamplerResult;
import demo.ai.aiArt.pojo.result.GetAiArtAllUpscalerResult;
import net.sf.json.JSONObject;
import wechatSdk.pojo.dto.AiArtGenerateOtherLikeThatDTO;

public interface AiArtService {

	SendTextToImgJobResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto);

	void rerunAllWaitingJobs();

	GetJobResultListForUser getJobResultListByTmpKey(String userTmpKey);

	SendTextToImgJobResult sendTextToImgFromApiDtoToMq(TextToImageFromApiDTO dto);

	GetJobResultListForUser getJobResultVoByJobPk(BasePkDTO dto);

	void loadingCache();

	AiArtImageWallResult getImageWallRandomSub();

	AiArtImageWallResult getImageWallFull(Boolean refresh);

	SendTextToImgJobResult generateOtherLikeThat(AiArtGenerateOtherLikeThatDTO dto);

	List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto, Long aiUserId);

	List<AiArtTextToImageJobRecord> getJobResultPageForWechatUser(String lastJobPk, String tmpKey);

	List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto);

	void sendNoticeIfAnyJobsWaitingForReview();

	void receiveImgJobResultForMQ(String txtToImgResultStr);

	List<AiArtModelVO> getAiArtModelVoList();

	GetAiArtAllModelListResult getAllModelList();

	GetAiArtAllSamplerResult getAllSamplerList();

	GetAiArtAllUpscalerResult getAllUpsalerList();

	void heartBeatReciver();

	void receiveImgJobResultForApi(JSONObject json);

	TextToImageDTO findRerunJobWhenSdkAsk();

}
