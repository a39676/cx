package demo.ai.aiArt.service;

import org.springframework.web.servlet.ModelAndView;

import ai.aiArt.pojo.result.GetJobResultList;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.AddToImageWallDTO;
import demo.ai.aiArt.pojo.dto.SetInvalidImageAndRetunTokensDTO;

public interface AiArtManagerService {

	CommonResult setRunningColab(BaseStrDTO dto);

	CommonResult setStopColab();

	ModelAndView getManagerView();

	GetJobResultList getAiArtJobList(BasePkDTO dto);

	CommonResult setInvalidImageAndRetunTokens(SetInvalidImageAndRetunTokensDTO dto);

	CommonResult addToImageWall(AddToImageWallDTO dto);

	CommonResult removeFromImageWall(String imgPk);

	ModelAndView getImageManagerView();

}
