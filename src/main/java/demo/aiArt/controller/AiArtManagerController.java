package demo.aiArt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ai.aiArt.pojo.result.GetJobResultList;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiArt.pojo.constant.AiArtMangerUrl;
import demo.aiArt.pojo.dto.SetInvalidImageAndRetunTokensDTO;
import demo.aiArt.service.AiArtManagerService;

@Controller
@RequestMapping(value = AiArtMangerUrl.ROOT)
public class AiArtManagerController {

	@Autowired
	private AiArtManagerService aiArtManagerService;

	@GetMapping(value = AiArtMangerUrl.VIEW)
	public ModelAndView getManagerView() {
		return aiArtManagerService.getManagerView();
	}

	@PostMapping(value = AiArtMangerUrl.SET_RUNNING_COLAB)
	@ResponseBody
	public CommonResult setRunningColab(@RequestBody BaseStrDTO dto) {
		return aiArtManagerService.setRunningColab(dto);
	}

	@PostMapping(value = AiArtMangerUrl.SET_STOP_COLAB)
	@ResponseBody
	public CommonResult setStopColab() {
		return aiArtManagerService.setStopColab();
	}

	@PostMapping(value = AiArtMangerUrl.GET_JOB_RESULT_LIST)
	@ResponseBody
	public GetJobResultList setStopColab(@RequestBody BasePkDTO dto) {
		return aiArtManagerService.getAiArtJobList(dto);
	}
	
	@PostMapping(value = AiArtMangerUrl.SET_INVALID_IMAGE_AND_RETURN_TOKENS)
	@ResponseBody
	public CommonResult setImgInvalid(@RequestBody SetInvalidImageAndRetunTokensDTO dto) {
		return aiArtManagerService.setInvalidImageAndRetunTokens(dto);
	}

}
