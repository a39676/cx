package demo.toyParts.educate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.toyParts.educate.pojo.constant.EducateUrl;
import demo.toyParts.educate.pojo.dto.AnswerDTO;
import demo.toyParts.educate.pojo.result.ExerciesAnswerMatchResult;
import demo.toyParts.educate.service.ExerciesAnswerService;

@Controller
@RequestMapping(value = EducateUrl.ROOT)
public class ExerciesAnswerController {

	@Autowired
	private ExerciesAnswerService answerService;
	
	@PostMapping(value = EducateUrl.ANSWER_SUBMIT)
	@ResponseBody
	public ExerciesAnswerMatchResult answerSubmit(@RequestBody AnswerDTO dto) {
		return answerService.answerSubmit(dto);
	}
}
