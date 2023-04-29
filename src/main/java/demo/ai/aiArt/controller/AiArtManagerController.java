package demo.ai.aiArt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.constant.AiArtMangerUrl;
import demo.ai.aiArt.pojo.dto.AddToImageWallDTO;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.SetInvalidImageAndRetunTokensDTO;
import demo.ai.aiArt.pojo.result.GetJobResultListForReivew;
import demo.ai.aiArt.service.AiArtManagerService;

@Controller
@RequestMapping(value = AiArtMangerUrl.ROOT)
public class AiArtManagerController {

	@Autowired
	private AiArtManagerService aiArtManagerService;

	@GetMapping(value = AiArtMangerUrl.VIEW)
	public ModelAndView getManagerView() {
		return aiArtManagerService.getManagerView();
	}

	@PostMapping(value = AiArtMangerUrl.GET_JOB_RESULT_LIST)
	@ResponseBody
	public GetJobResultListForReivew getAiArtJobList(@RequestBody AiArtJobListFilterDTO dto) {
		return aiArtManagerService.__getAiArtJobListForReview(dto);
	}

	@PostMapping(value = AiArtMangerUrl.SET_INVALID_IMAGE_AND_RETURN_TOKENS)
	@ResponseBody
	public CommonResult setImgInvalid(@RequestBody SetInvalidImageAndRetunTokensDTO dto) {
		return aiArtManagerService.setInvalidImageAndRetunTokens(dto);
	}

	@GetMapping(value = AiArtMangerUrl.IMAGE_WALL_MANAGER)
	public ModelAndView getImageManagerView() {
		return aiArtManagerService.getImageManagerView();
	}

	@PostMapping(value = AiArtMangerUrl.ADD_TO_IMAGE_WALL)
	@ResponseBody
	public CommonResult setImgInvalid(@RequestBody AddToImageWallDTO dto) {
		return aiArtManagerService.addToImageWall(dto);
	}

	@PostMapping(value = AiArtMangerUrl.REMVOE_FROM_IMAGE_WALL)
	@ResponseBody
	public CommonResult removeFromImageWall(@RequestBody BasePkDTO dto) {
		return aiArtManagerService.removeFromImageWall(dto.getPk());
	}

	@PostMapping(value = AiArtMangerUrl.SET_HAD_REVIEW)
	@ResponseBody
	public CommonResult setHadReview(@RequestBody BasePkDTO dto) {
		return aiArtManagerService.setHadReview(dto.getPk());
	}

}
