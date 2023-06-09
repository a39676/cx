package demo.ai.aiArt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.aiArt.pojo.constant.AiArtApiUrlConstant;
import ai.aiArt.pojo.result.GetJobResultListForUser;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import demo.ai.aiArt.pojo.dto.ImageToImageFromApiDTO;
import demo.ai.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.ai.aiArt.pojo.result.GetAiArtAllModelListResult;
import demo.ai.aiArt.pojo.result.GetAiArtAllSamplerResult;
import demo.ai.aiArt.pojo.result.GetAiArtAllUpscalerResult;
import demo.ai.aiArt.service.AiArtService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = AiArtApiUrlConstant.ROOT)
public class AiArtApiController {

	@Autowired
	private AiArtService aiArtService;

	@PostMapping(value = AiArtApiUrlConstant.TEXT_TO_IMAGE)
	@ResponseBody
	public SendTextToImgJobResult sendTextToImgFromApiDtoToMq(@RequestBody TextToImageFromApiDTO dto) {
		return aiArtService.sendTextToImgFromApiDtoToMq(dto);
	}

	@PostMapping(value = AiArtApiUrlConstant.IMAGE_TO_IMAGE)
	@ResponseBody
	public SendTextToImgJobResult sendImgToImgFromApiDtoToMq(@RequestBody ImageToImageFromApiDTO dto) {
		return aiArtService.sendImgToImgFromApiDtoToMq(dto);
	}

	@PostMapping(value = AiArtApiUrlConstant.GET_JOB_RESULT_BY_JOB_PK)
	@ResponseBody
	public GetJobResultListForUser getJobResultVoByJobPk(@RequestBody BasePkDTO dto) {
		return aiArtService.getJobResultVoByJobPk(dto);
	}

	@GetMapping(value = AiArtApiUrlConstant.GET_MODEL_LIST)
	@ResponseBody
	public GetAiArtAllModelListResult getModelList() {
		return aiArtService.getAllModelList();
	}

	@GetMapping(value = AiArtApiUrlConstant.GET_SAMPLER_LIST)
	@ResponseBody
	public GetAiArtAllSamplerResult getAllSamplerList() {
		return aiArtService.getAllSamplerList();
	}

	@GetMapping(value = AiArtApiUrlConstant.GET_UPSCALER_LIST)
	@ResponseBody
	public GetAiArtAllUpscalerResult getAllUpsalerList() {
		return aiArtService.getAllUpsalerList();
	}

	@PostMapping(value = AiArtApiUrlConstant.RECEIVE_IMAGE_JOB_RESULT)
	@ResponseBody
	public String receiveImgJobResultForApi(@RequestBody JSONObject json) {
		aiArtService.receiveImgJobResultForApi(json);
		return "{}";
	}

	@PostMapping(value = AiArtApiUrlConstant.HEART_BEAT)
	@ResponseBody
	public void heartBeatReciver() {
		aiArtService.heartBeatReciver();
	}

	@PostMapping(value = AiArtApiUrlConstant.GET_RERUN_JOB)
	@ResponseBody
	public JSONObject findRerunJobWhenSdkAsk() {
		return aiArtService.findRerunJobWhenSdkAsk();
	}
	
	@PostMapping(value = AiArtApiUrlConstant.RECEIVE_IMAGE_JOB_RESULT_AND_GET_RERUN_JOB)
	@ResponseBody
	public JSONObject receiveImgJobResultAndGetNewJobForApi(@RequestBody JSONObject json) {
		return aiArtService.receiveImgJobResultAndGetNewJobForApi(json);
	}
}
