package demo.aiArt.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface AiArtManagerService {

	CommonResult setRunningColab(BaseStrDTO dto);

	CommonResult setStopColab();

	ModelAndView getManagerView();

}
