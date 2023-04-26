package demo.ai.aiArt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.aiArt.pojo.constant.AiArtApiUrlConstant;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import demo.ai.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.ai.aiArt.service.AiArtService;
import demo.common.pojo.dto.BaseDTO;

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
	
	@PostMapping(value = AiArtApiUrlConstant.GET_JOB_RESULT_BY_JOB_PK)
	@ResponseBody
	public GetJobResultList getJobResultVoByJobPk(@RequestBody BaseDTO dto) {
		return aiArtService.getJobResultVoByJobPk(dto);
	}
	
	
}
