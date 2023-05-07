package demo.ai.aiArt.service;

import java.util.List;

import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.pojo.vo.AiArtModelVO;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import wechatSdk.pojo.dto.AiArtGenerateOtherLikeThatDTO;

public interface AiArtService {

	SendTextToImgJobResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto);

	void rerun();

	GetJobResultList getJobResultListByTmpKey(String userTmpKey);

	SendTextToImgJobResult sendTextToImgFromApiDtoToMq(TextToImageFromApiDTO dto);

	GetJobResultList getJobResultVoByJobPk(BasePkDTO dto);

	void deleteParameterFile();

	void refreshImageWallJsonFile();

	void loadImageWallToCache();

	AiArtImageWallResult getImageWallRandomSub();

	AiArtImageWallResult getImageWallFull(Boolean refresh);

	SendTextToImgJobResult generateOtherLikeThat(AiArtGenerateOtherLikeThatDTO dto);

	List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto, Long aiUserId);

	List<AiArtTextToImageJobRecord> getJobResultPageForWechatUser(String lastJobPk, String tmpKey);

	List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto);

	void sendNoticeIfAnyJobsWaitingForReview();

	void receiveImgJobResult(String txtToImgResultStr);

	List<AiArtModelVO> getAiArtModelVoList();

}
