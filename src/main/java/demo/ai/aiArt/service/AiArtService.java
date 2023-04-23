package demo.ai.aiArt.service;

import java.util.List;

import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.common.pojo.dto.BaseDTO;
import wechatSdk.pojo.dto.AiArtGenerateOtherLikeThatDTO;

public interface AiArtService {

	SendTextToImgJobResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto);

	CommonResult txtToImgByJobId(Long jobId);

	void rerun();

	GetJobResultList getJobResultListByTmpKey(String userTmpKey);

	SendTextToImgJobResult sendTextToImgFromApiDtoToMq(TextToImageFromApiDTO dto);

	GetJobResultList getJobResultVoByJobPk(BaseDTO dto);

	void deleteParameterFile();

	void refreshImageWallJsonFile();

	void loadImageWallToCache();

	AiArtImageWallResult getImageWall();

	AiArtImageWallResult getImageWall(Boolean refresh);

	SendTextToImgJobResult generateOtherLikeThat(AiArtGenerateOtherLikeThatDTO dto);

	void __sendRandomGenerateJob();

	List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto, Long aiUserId);

	List<AiArtTextToImageJobRecord> getJobResultPageForWechatUser(String lastJobPk, String tmpKey);

	List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto);

	void sendNoticeIfAnyJobsWaitingForReview();

}
