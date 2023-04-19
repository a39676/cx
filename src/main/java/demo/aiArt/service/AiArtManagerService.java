package demo.aiArt.service;

import org.springframework.web.servlet.ModelAndView;

import ai.aiArt.pojo.result.GetJobResultList;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface AiArtManagerService {

	CommonResult setRunningColab(BaseStrDTO dto);

	CommonResult setStopColab();

	ModelAndView getManagerView();

	GetJobResultList getAiArtJobList(BasePkDTO dto);

	CommonResult setImgInvalid(BasePkDTO dto);

}
