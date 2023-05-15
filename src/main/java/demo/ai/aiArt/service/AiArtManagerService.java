package demo.ai.aiArt.service;

import org.springframework.web.servlet.ModelAndView;

import ai.aiArt.pojo.result.SendTextToImgJobResult;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.AddToImageWallDTO;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.SetInvalidImageAndRetunTokensDTO;
import demo.ai.aiArt.pojo.result.GetJobResultListForReivew;

public interface AiArtManagerService {

	ModelAndView getManagerView();

	GetJobResultListForReivew __getAiArtJobListForReview(AiArtJobListFilterDTO dto);

	CommonResult setInvalidImageAndRetunTokens(SetInvalidImageAndRetunTokensDTO dto);

	CommonResult addToImageWall(AddToImageWallDTO dto);

	CommonResult removeFromImageWall(String imgPk);

	ModelAndView getImageManagerView();

	CommonResult setHadReview(String jobPk);

	CommonResult setHadReview(Long jobId);

	void setReviewBatchForAdmin();

	SendTextToImgJobResult generateOtherLikeThat(BasePkDTO dto);

}
