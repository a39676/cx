package demo.tool.educate.service;

import demo.tool.educate.pojo.dto.ExerciseAnswerDTO;
import demo.tool.educate.pojo.result.ExerciseAnswerMatchResult;

public interface ExerciseAnswerService {

	ExerciseAnswerMatchResult answerSubmit(ExerciseAnswerDTO dto);

}
