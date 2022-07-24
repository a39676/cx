package demo.toyParts.educate.service;

import demo.toyParts.educate.pojo.dto.ExerciseAnswerDTO;
import demo.toyParts.educate.pojo.result.ExerciseAnswerMatchResult;

public interface ExerciseAnswerService {

	ExerciseAnswerMatchResult answerSubmit(ExerciseAnswerDTO dto);

}
