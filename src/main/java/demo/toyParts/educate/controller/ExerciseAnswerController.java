package demo.toyParts.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.toyParts.educate.pojo.constant.EducateUrl;
import demo.toyParts.educate.pojo.dto.ExerciseAnswerDTO;
import demo.toyParts.educate.pojo.result.ExerciseAnswerMatchResult;
import demo.toyParts.educate.service.ExerciseAnswerService;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class ExerciseAnswerController {

	@Autowired
	private ExerciseAnswerService answerService;
	
	@PostMapping(value = EducateUrl.ANSWER_SUBMIT)
	@ResponseBody
	public ExerciseAnswerMatchResult answerSubmit(@RequestBody ExerciseAnswerDTO dto) {
		return answerService.answerSubmit(dto);
	}
}