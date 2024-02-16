package demo.tool.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.tool.educate.pojo.constant.EducateUrl;
import demo.tool.educate.pojo.dto.ExerciseAnswerDTO;
import demo.tool.educate.pojo.result.ExerciseAnswerMatchResult;
import demo.tool.educate.service.ExerciseAnswerService;

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
