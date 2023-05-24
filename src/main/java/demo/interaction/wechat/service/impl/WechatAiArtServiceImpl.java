package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.dto.TextToImageDTO;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.GetJobResultListForUser;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.aiArt.pojo.vo.AiArtGenerateImageBaseVO;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.ai.aiArt.service.AiArtService;
import demo.interaction.wechat.service.WechatAiArtService;
import wechatSdk.pojo.dto.AiArtGenerateOtherLikeThatDTO;

@Service
public class WechatAiArtServiceImpl extends WechatCommonService implements WechatAiArtService {

	@Autowired
	private AiArtService aiArtService;

	@Override
	public EncryptDTO getImageWallForWechat(EncryptDTO encryptedDTO) {
		String randomStr = decryptEncryptDTO(encryptedDTO, String.class);
		if (randomStr == null) {
			return encryptDTO(new AiArtImageWallResult());
		}

		return encryptDTO(aiArtService.getImageWallRandomSub());
	}

	@Override
	public EncryptDTO generateOtherLikeThat(EncryptDTO encryptedDTO) {
		AiArtGenerateOtherLikeThatDTO dto = decryptEncryptDTO(encryptedDTO, AiArtGenerateOtherLikeThatDTO.class);
		if (dto == null) {
			return encryptDTO(new SendTextToImgJobResult());
		}

		return encryptDTO(aiArtService.generateOtherLikeThat(dto));
	}

	@Override
	public EncryptDTO getParameterByJobPk(EncryptDTO encryptedDTO) {
		BasePkDTO dto = decryptEncryptDTO(encryptedDTO, BasePkDTO.class);
		if (dto == null) {
			return encryptDTO(new GetJobResultListForUser());
		}

		try {
			GetJobResultListForUser jobResult = aiArtService.getJobResultVoByJobPk(dto);
			AiArtGenerateImageBaseVO subResult = jobResult.getJobResultList().get(0);
			TextToImageDTO parameterDTO = subResult.getParameter();
			return encryptDTO(parameterDTO);
		} catch (Exception e) {
			return encryptDTO(new TextToImageDTO());
		}
	}
}
