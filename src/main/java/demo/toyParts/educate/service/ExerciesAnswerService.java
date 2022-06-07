package demo.toyParts.educate.service;

import demo.toyParts.educate.pojo.dto.ExerciesAnswerDTO;
import demo.toyParts.educate.pojo.result.ExerciesAnswerMatchResult;

public interface ExerciesAnswerService {

	ExerciesAnswerMatchResult answerSubmit(ExerciesAnswerDTO dto);

}
