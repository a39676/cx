package demo.toyParts.educate.math.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.math.service.ExerciseMathCommonService;
import demo.toyParts.educate.math.service.ExerciseServiceMathG4_2;
import demo.toyParts.educate.pojo.dto.MathExerciseDTO;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.result.ExerciseBuildResult;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.pojo.type.MathQuestionType;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciseServiceMathG4_2Impl extends ExerciseMathCommonService implements ExerciseServiceMathG4_2 {

	@Autowired
	private SimpleAlgorithmGenerator simpleAlgorithmGenerator;

	private static final Integer MAX_CALCULATE = 99999;
	private static final Integer MAX_NUM = 999;
	private static final Integer MIN_NUM = 30;
	private static final Integer SCALE = 6;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_4_2;

	@Override
	public ModelAndView getExercise() {
		return buildExerciseView(buildExercise());
	}

	@Override
	public ExerciseBuildResult buildExercise() {
		MathExerciseDTO exerciseDTO = reloadExercise(GRADE_TYPE);

		boolean newExerciseFlag = (exerciseDTO == null);
		if (newExerciseFlag) {
			exerciseDTO = exerciseGenerator();
		}
		return buildExercise(exerciseDTO, newExerciseFlag);
	}

	private MathExerciseDTO exerciseGenerator() {
		MathExerciseDTO exerciseDTO = new MathExerciseDTO();
		exerciseDTO.setExerciseID(snowFlake.getNextId());
		exerciseDTO.setUserId(baseUtilCustom.getUserId());
		exerciseDTO.setSubjectType(SUBJECT_TYPE);
		exerciseDTO.setQuestionList(new ArrayList<>());
		exerciseDTO.setGradeType(GRADE_TYPE);

		MathQuestionBaseDTO question = null;
		int normalCalculateQuestionSize = 10;
		int questionNumber = 1;
		for (; questionNumber < normalCalculateQuestionSize; questionNumber++) {
			question = null;
			Double standardAnswer = -1D;
			while (standardAnswer < 0 || standardAnswer > MAX_CALCULATE) {
				question = createQuestion();
				try {
					standardAnswer = Double.valueOf(question.getStandardAnswer().get(0));
				} catch (Exception e) {
				}
			}
			question.setQuestionNumber(questionNumber);
			question.setMathQuestionType(MathQuestionType.CALCULATE);
			exerciseDTO.getQuestionList().add(question);
		}

		int simpleAlgorithmQuestionSize = 10;
		for (; (questionNumber - normalCalculateQuestionSize) < simpleAlgorithmQuestionSize; questionNumber++) {
			question = null;
			Double standardAnswer = -1D;
			while (standardAnswer < 0 || standardAnswer > MAX_CALCULATE) {
				question = simpleAlgorithmGenerator.createSimpleAlgorithmQuestion();
				try {
					standardAnswer = Double.valueOf(question.getStandardAnswer().get(0));
				} catch (Exception e) {
				}
			}
			question.setQuestionNumber(questionNumber);
			question.setMathQuestionType(MathQuestionType.CALCULATE);
			exerciseDTO.getQuestionList().add(question);
		}

		question = createWordProblemModule1();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;


		return exerciseDTO;
	}

	private MathQuestionBaseDTO createQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(MIN_NUM + 1, MAX_NUM + 1);
		int num2 = t.nextInt(MIN_NUM, num1 + 1);
		int num3 = t.nextInt(MIN_NUM, MAX_NUM + 1);
		int num4 = t.nextInt(MIN_NUM, MAX_NUM + 1);

		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.subtraction);

		boolean hasBracket = t.nextBoolean();

		String exp = null;

		if (MathBaseSymbolType.subtraction.equals(mathSymbolType1)) {
			exp = String.valueOf("(" + num1 + mathSymbolType1.getCodeSymbol() + num2 + ")"
					+ MathBaseSymbolType.multiplication.getCodeSymbol() + num3);
		} else {
			if (hasBracket) {
				exp = String.valueOf(num3 + MathBaseSymbolType.multiplication.getCodeSymbol() + "(" + num1
						+ mathSymbolType1.getCodeSymbol() + num2 + ")");
			} else {
				exp = String.valueOf(num3 + MathBaseSymbolType.multiplication.getCodeSymbol() + num1
						+ mathSymbolType1.getCodeSymbol() + num2);
			}
		}

		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		BigDecimal resultInBigDecimal = new BigDecimal(result);

		exp = exp + MathBaseSymbolType.division.getCodeSymbol() + num4;
		expression = new ExpressionBuilder(exp).build();
		result = expression.evaluate();
		BigDecimal finalResultInBigDecimal = resultInBigDecimal
				.divide(new BigDecimal(num4), SCALE, RoundingMode.HALF_UP).stripTrailingZeros();
		;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("精确至" + SCALE + "位小数");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(finalResultInBigDecimal.toPlainString()));
		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule1() {
		String moduleStr = "在一个三角形中，∠1＝%d°，∠2＝%d°，∠3＝（ ）<br>" + "在一个等腰三角形中，一个底角是%d°，顶角是（ ）。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int c1 = t.nextInt(1, 178 + 1);
		int c2 = t.nextInt(1, c1);
		int c3 = 180 - c1 - c2;

		int buttomConer = t.nextInt(1, 89 + 1);

		int r2 = 180 - buttomConer * 2;

		q.setExpression(String.format(moduleStr, c1, c2, buttomConer));
		q.addStandardAnswer(String.valueOf(c3));
		q.addStandardAnswer(String.valueOf(r2));

		return q;
	}

	public static void main(String[] args) {
		ExerciseServiceMathG4_2Impl t = new ExerciseServiceMathG4_2Impl();
		int i = 0;
		while (i < 10) {
			MathQuestionBaseDTO q = t.createWordProblemModule1();
			System.out.println(q.toString());
			i++;
		}
	}
}
