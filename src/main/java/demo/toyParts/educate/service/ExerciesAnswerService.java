package demo.toyParts.educate.service;

import demo.toyParts.educate.pojo.dto.AnswerDTO;
import demo.toyParts.educate.pojo.result.ExerciesAnswerMatchResult;

public interface ExerciesAnswerService {

	ExerciesAnswerMatchResult answerSubmit(AnswerDTO dto);

}
