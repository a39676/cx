package demo.toyParts.educate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.result.ExerciesBuildResult;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.service.ExerciesMathCommonService;
import demo.toyParts.educate.service.ExerciesServiceMathG4_1;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG4_1Impl extends ExerciesMathCommonService implements ExerciesServiceMathG4_1 {

	private static final Integer MAX_CALCULATE = 99999;
	private static final Integer MAX_ADDITION_NUM = 33300;
	private static final Integer MAX_DECIMAL_ADDITION_NUM = 333;
	private static final Integer MIN_ADDITION_NUM = 0;
	private static final Integer MAX_MULTIPLICATION1_NUM = 999;
	private static final Integer MIN_MULTIPLICATION1_NUM = 100;
	private static final Integer MAX_MULTIPLICATION2_NUM = 99;
	private static final Integer MIN_MULTIPLICATION2_NUM = 10;
	private static final Integer MAX_DIVISION_NUM = 99;
	private static final Integer MIN_DIVISION_NUM = 6;
	private static final Integer SCALE = 4;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_4_1;

	@Override
	public ModelAndView getExercies() {
		return buildExerciesView(buildExercies());
	}

	@Override
	public ExerciesBuildResult buildExercies() {
		MathExerciesDTO exerciesDTO = reloadExercies(GRADE_TYPE);

		boolean newExerciesFlag = (exerciesDTO == null);
		if (newExerciesFlag) {
			exerciesDTO = exerciesGenerator();
		}
		return buildExercies(exerciesDTO, newExerciesFlag);
	}

	private MathExerciesDTO exerciesGenerator() {
		MathExerciesDTO exerciesDTO = new MathExerciesDTO();
		exerciesDTO.setExerciesID(snowFlake.getNextId());
		exerciesDTO.setUserId(baseUtilCustom.getUserId());
		exerciesDTO.setSubjectType(SUBJECT_TYPE);
		exerciesDTO.setQuestionList(new ArrayList<>());
		exerciesDTO.setGradeType(GRADE_TYPE);

		MathQuestionBaseDTO question = null;
		for (int questionNumber = 1; questionNumber <= optionService.getQuestionListSize(); questionNumber++) {
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
			exerciesDTO.getQuestionList().add(question);
		}

		return exerciesDTO;
	}

	private MathQuestionBaseDTO createQuestion() {
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int rate = t.nextInt(1, 11);
		if (rate > 3) {
			return createIntegerQuestion();
		} else {
			return createDecimalQuestion();
		}
	}

	private MathQuestionBaseDTO createDecimalQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		BigDecimal num1 = new BigDecimal(t.nextDouble(MIN_ADDITION_NUM, MAX_DECIMAL_ADDITION_NUM)).setScale(SCALE,
				RoundingMode.HALF_UP);
		BigDecimal num2 = new BigDecimal(t.nextDouble(MIN_ADDITION_NUM, MAX_DECIMAL_ADDITION_NUM)).setScale(SCALE,
				RoundingMode.HALF_UP);
		BigDecimal num3 = new BigDecimal(t.nextDouble(MIN_ADDITION_NUM, MAX_DECIMAL_ADDITION_NUM)).setScale(SCALE,
				RoundingMode.HALF_UP);

		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.subtraction);

		MathBaseSymbolType mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.subtraction);

		String exp = String
				.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2 + mathSymbolType2.getCodeSymbol() + num3);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();

		while (result < 0 || result > MAX_CALCULATE) {
			num1 = new BigDecimal(t.nextDouble(MIN_ADDITION_NUM, MAX_DECIMAL_ADDITION_NUM)).setScale(SCALE,
					RoundingMode.HALF_UP);
			num2 = new BigDecimal(t.nextDouble(MIN_ADDITION_NUM, MAX_DECIMAL_ADDITION_NUM)).setScale(SCALE,
					RoundingMode.HALF_UP);
			num3 = new BigDecimal(t.nextDouble(MIN_ADDITION_NUM, MAX_DECIMAL_ADDITION_NUM)).setScale(SCALE,
					RoundingMode.HALF_UP);

			mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.subtraction);

			mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.subtraction);

			if (MathBaseSymbolType.subtraction.equals(mathSymbolType1)) {
				if (num1.compareTo(num2) < 0) {
					continue;
				}
			}

			exp = String
					.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2 + mathSymbolType2.getCodeSymbol() + num3);
			expression = new ExpressionBuilder(exp).build();
			result = expression.evaluate();
		}

		BigDecimal fromDouble = new BigDecimal(result).setScale(SCALE, RoundingMode.HALF_UP);
		if (String.valueOf(fromDouble).endsWith("0")) {
			fromDouble = new BigDecimal(result).setScale(1, RoundingMode.HALF_UP);
		}

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(fromDouble));
		return q;
	}

	private MathQuestionBaseDTO createIntegerQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = 0;
		int num2 = 0;
		int num3 = 0;

		MathBaseSymbolType mathSymbolType1 = null;
		MathBaseSymbolType mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.division);

		boolean hasBrackets = (0 == t.nextInt(0, 1 + 1));
		boolean bracketOnLeft = (0 == t.nextInt(0, 1 + 1));
		String resultExp = null;

		if (MathBaseSymbolType.division.equals(mathSymbolType2)) {
			MathQuestionBaseDTO questionRightPart = createSimpleDivision();
			if (MathBaseSymbolType.division.equals(mathSymbolType1)) {
				MathQuestionBaseDTO question = createSimpleDivisionWithInputExp(questionRightPart.getExpression());
				resultExp = question.getExpression();
			} else {
				mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
						MathBaseSymbolType.subtraction);
				num3 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
				num2 = t.nextInt(MIN_MULTIPLICATION2_NUM * 3, MAX_MULTIPLICATION2_NUM * 3 + 1);
				num1 = num3 * num2;
				String expLeftPart = "(" + createExpressionBySymbol(mathSymbolType1, num1).getExpression() + ")";
				resultExp = expLeftPart + mathSymbolType2.getCodeSymbol() + num3;
			}
		} else if (MathBaseSymbolType.multiplication.equals(mathSymbolType2)) {
			mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
					MathBaseSymbolType.multiplication);
			if (mathSymbolType1.getCode() < MathBaseSymbolType.multiplication.getCode()) {
				num3 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);
				if (hasBrackets) {
					if (mathSymbolType1.getCode() < MathBaseSymbolType.multiplication.getCode()) {
						Integer leftPartExpectResult = t.nextInt(MIN_MULTIPLICATION1_NUM, MAX_MULTIPLICATION1_NUM);
						MathQuestionBaseDTO questLeftPart = createExpressionBySymbol(mathSymbolType1,
								leftPartExpectResult);
						String expLeftPart = questLeftPart.getExpression();
						resultExp = String.valueOf("(" + expLeftPart + ")" + mathSymbolType2.getCodeSymbol() + num3);
					} else if (MathBaseSymbolType.multiplication.equals(mathSymbolType1)) {
						num1 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
						num2 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
						resultExp = String.valueOf("(" + num1 + mathSymbolType1.getCodeSymbol() + num2 + ")"
								+ mathSymbolType2.getCodeSymbol() + num3);
					} else {
						MathQuestionBaseDTO questLeftPart = createExpressionBySymbol(mathSymbolType1, null);
						String expLeftPart = questLeftPart.getExpression();
						resultExp = String.valueOf("(" + expLeftPart + ")" + mathSymbolType2.getCodeSymbol() + num3);
					}
				} else {
					if (MathBaseSymbolType.addition.equals(mathSymbolType1)) {
						num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
						num2 = t.nextInt(MIN_MULTIPLICATION1_NUM, MAX_MULTIPLICATION1_NUM + 1);
					} else {
						num2 = t.nextInt(MIN_MULTIPLICATION1_NUM, MAX_MULTIPLICATION1_NUM + 1);
						num1 = t.nextInt(num2 * num3, MAX_CALCULATE);
					}
					resultExp = String.valueOf(
							num1 + mathSymbolType1.getCodeSymbol() + num2 + mathSymbolType2.getCodeSymbol() + num3);
				}
			} else {
				num1 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
				num2 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
				num3 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);

				if (hasBrackets) {
					if (bracketOnLeft) {
						resultExp = String.valueOf("(" + num1 + mathSymbolType1.getCodeSymbol() + num2 + ")"
								+ mathSymbolType2.getCodeSymbol() + num3);
					} else {
						resultExp = String.valueOf(num3 + mathSymbolType1.getCodeSymbol() + "(" + num2
								+ mathSymbolType2.getCodeSymbol() + num1 + ")");
					}
				} else {
					resultExp = String.valueOf(
							num1 + mathSymbolType1.getCodeSymbol() + num2 + mathSymbolType2.getCodeSymbol() + num3);
				}
			}
		} else {
			if (MathBaseSymbolType.addition.equals(mathSymbolType2)) {
				num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
				mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.division);
				MathQuestionBaseDTO questionLeftPart = createExpressionBySymbol(mathSymbolType1, null);
				if (hasBrackets) {
					if (bracketOnLeft) {
						resultExp = String.valueOf(
								"(" + questionLeftPart.getExpression() + ")" + mathSymbolType2.getCodeSymbol() + num3);
					} else {
						if (MathBaseSymbolType.addition.equals(mathSymbolType1)) {
							num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
							num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "(" + num2
									+ mathSymbolType2.getCodeSymbol() + num3 + ")");
						} else if (MathBaseSymbolType.subtraction.equals(mathSymbolType1)) {
							num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
							num1 = t.nextInt(num2 + num3, MAX_CALCULATE + 1);
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "(" + num2
									+ mathSymbolType2.getCodeSymbol() + num3 + ")");
						} else if (MathBaseSymbolType.multiplication.equals(mathSymbolType1)) {
							num1 = t.nextInt(MIN_MULTIPLICATION1_NUM, MAX_MULTIPLICATION1_NUM + 1);
							int expectAddtionResult = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);
							MathQuestionBaseDTO addtionQuestion = createExpressionBySymbol(mathSymbolType2,
									expectAddtionResult);
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "("
									+ addtionQuestion.getExpression() + ")");
						} else {
							int rightPartResult = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
							MathQuestionBaseDTO questionRightPart = createExpressionBySymbol(mathSymbolType2,
									rightPartResult);
							num2 = t.nextInt(MIN_MULTIPLICATION2_NUM * 3, MAX_MULTIPLICATION2_NUM * 3 + 1);
							num1 = rightPartResult * num2;
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "("
									+ questionRightPart.getExpression() + ")");
						}
					}
				} else {
					resultExp = String
							.valueOf(questionLeftPart.getExpression() + mathSymbolType2.getCodeSymbol() + num3);
				}
			} else {
				num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
				mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition, MathBaseSymbolType.division);
				if (hasBrackets) {
					if (bracketOnLeft) {
						int expectLeftPartResult = t.nextInt(num3, MAX_CALCULATE);
						MathQuestionBaseDTO questionLeftPart = createExpressionBySymbol(mathSymbolType1,
								expectLeftPartResult);
						resultExp = String.valueOf(
								"(" + questionLeftPart.getExpression() + ")" + mathSymbolType2.getCodeSymbol() + num3);
					} else {
						if (MathBaseSymbolType.addition.equals(mathSymbolType1)) {
							num2 = t.nextInt(num3, MAX_CALCULATE + 1);
							num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "(" + num2
									+ mathSymbolType2.getCodeSymbol() + num3 + ")");
						} else if (MathBaseSymbolType.subtraction.equals(mathSymbolType1)) {
							num2 = t.nextInt(num3, MAX_ADDITION_NUM + 1);
							num1 = t.nextInt(num2, MAX_CALCULATE + 1);
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "(" + num2
									+ mathSymbolType2.getCodeSymbol() + num3 + ")");
						} else if (MathBaseSymbolType.multiplication.equals(mathSymbolType1)) {
							num1 = t.nextInt(MIN_MULTIPLICATION1_NUM, MAX_MULTIPLICATION1_NUM + 1);
							int expectRightPartResult = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);
							MathQuestionBaseDTO questionRightPart = createExpressionBySymbol(mathSymbolType2,
									expectRightPartResult);
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "("
									+ questionRightPart.getExpression() + ")");
						} else {
							int expectRightPartResult = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
							MathQuestionBaseDTO questionRightPart = createExpressionBySymbol(mathSymbolType2,
									expectRightPartResult);
							num2 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM * 3 + 1);
							num1 = expectRightPartResult * num2;
							resultExp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + "("
									+ questionRightPart.getExpression() + ")");
						}
					}
				} else {
					int expectLeftPartResult = t.nextInt(num3, MAX_CALCULATE);
					MathQuestionBaseDTO questionLeftPart = createExpressionBySymbol(mathSymbolType1,
							expectLeftPartResult);
					resultExp = String
							.valueOf(questionLeftPart.getExpression() + mathSymbolType2.getCodeSymbol() + num3);
				}

			}
		}

		Expression expression = new ExpressionBuilder(resultExp).build();
		Double result = expression.evaluate();
		resultExp = replaceCodingSymbolToMathSymbol(resultExp);
		q.setExpression(resultExp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result.intValue()));
		return q;
	}

	private MathQuestionBaseDTO createExpressionBySymbol(MathBaseSymbolType symbol, Integer inputResult) {
		if (MathBaseSymbolType.addition.equals(symbol)) {
			return createSimpleAddtionByResult(inputResult);
		} else if (MathBaseSymbolType.subtraction.equals(symbol)) {
			return createSimpleSubtractionByResult(inputResult);
		} else if (MathBaseSymbolType.multiplication.equals(symbol)) {
			if (inputResult == null) {
				return createSimpleMultiplication();
			} else {
				return createSimpleMultiplicationWithInpuMinResult(inputResult);
			}
		} else {
			if (inputResult == null) {
				return createSimpleDivision();
			} else {
				return createSimpleDivisionWithInputMinResult(inputResult);
			}
		}
	}

	private MathQuestionBaseDTO createSimpleDivision() {
		return createSimpleDivisionWithInputExp(null);
	}

	private MathQuestionBaseDTO createSimpleDivisionWithInputExp(String inputRightPartExp) {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathBaseSymbolType mathSymbolType1 = MathBaseSymbolType.division;

		Integer inputResult = null;
		if (inputRightPartExp == null) {
			inputResult = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		} else {
			Expression expression = new ExpressionBuilder(inputRightPartExp).build();
			Double result = expression.evaluate();
			inputResult = result.intValue();
		}
		Integer num1 = inputResult * t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM * 3 + 1);

		String exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + inputResult);

		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		q.setExpression(exp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result.intValue()));
		return q;
	}

	private MathQuestionBaseDTO createSimpleDivisionWithInputMinResult(Integer minResult) {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathBaseSymbolType mathSymbolType1 = MathBaseSymbolType.division;

		Integer num2 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		Integer tmpResult = t.nextInt(minResult, MAX_CALCULATE + 1);
		Integer num1 = tmpResult / num2;

		String exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);

		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		q.setExpression(exp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result.intValue()));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAddtionByResult(Integer inputResult) {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathBaseSymbolType mathSymbolType1 = MathBaseSymbolType.addition;

		if (inputResult == null) {
			inputResult = MAX_ADDITION_NUM;
		}
		Integer num1 = t.nextInt(MIN_ADDITION_NUM, inputResult + 1);
		Integer num2 = inputResult - num1;

		String exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		q.setExpression(exp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result.intValue()));
		return q;
	}

	private MathQuestionBaseDTO createSimpleSubtractionByResult(Integer inputResult) {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathBaseSymbolType mathSymbolType1 = MathBaseSymbolType.subtraction;

		if (inputResult == null) {
			inputResult = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_CALCULATE);
		}
		Integer num1 = t.nextInt(inputResult, MAX_CALCULATE + 1);
		Integer num2 = num1 - inputResult;

		String exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		q.setExpression(exp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result.intValue()));
		return q;
	}

	private MathQuestionBaseDTO createSimpleMultiplication() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathBaseSymbolType mathSymbolType1 = MathBaseSymbolType.multiplication;

		Integer num1 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);
		Integer num2 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);

		String exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		q.setExpression(exp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result.intValue()));
		return q;
	}

	private MathQuestionBaseDTO createSimpleMultiplicationWithInpuMinResult(Integer inputMinResult) {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathBaseSymbolType mathSymbolType1 = MathBaseSymbolType.multiplication;

		Integer num1 = t.nextInt(MIN_MULTIPLICATION1_NUM, MAX_MULTIPLICATION1_NUM + 1);
		Integer num2 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);
		while (num1 * num2 < inputMinResult) {
			num1 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);
			num2 = t.nextInt(MIN_MULTIPLICATION2_NUM, MAX_MULTIPLICATION2_NUM + 1);
		}

		String exp = String.valueOf(num1 + mathSymbolType1.getCodeSymbol() + num2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		q.setExpression(exp);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result.intValue()));
		return q;
	}
}
