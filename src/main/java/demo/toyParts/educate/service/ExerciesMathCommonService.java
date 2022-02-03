package demo.toyParts.educate.service;

import java.util.concurrent.ThreadLocalRandom;

import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;

public abstract class ExerciesMathCommonService extends EducateCommonService {

	protected static final ExerciesSubjectType SUBJECT_TYPE = ExerciesSubjectType.math;
	
	protected MathBaseSymbolType getRandomMathBaseSymbolType(MathBaseSymbolType start, MathBaseSymbolType end) {
		ThreadLocalRandom t = ThreadLocalRandom.current();
		int mathSymbolTypeCode = t.nextInt(start.getCode(), end.getCode() + 1);
		return MathBaseSymbolType.getType(mathSymbolTypeCode);
	}
	
	protected String replaceCodingSymbolToMathSymbol(String exp) {
		exp = exp.replaceAll("\\*", "×");
		exp = exp.replaceAll("\\/", "÷");
		return exp;
	}
	
	protected MathExerciesDTO reloadExercies(GradeType gradeType, Long userId) {
		StudentExerciesHistory lastExercies = reloadExercies(gradeType, SUBJECT_TYPE, userId);
		if(lastExercies == null) {
			return null;
		} else {
			return buildExerciesFromFile(lastExercies, MathExerciesDTO.class);
		}
	}
	
}
