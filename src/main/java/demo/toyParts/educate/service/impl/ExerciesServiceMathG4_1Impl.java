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
import demo.toyParts.educate.pojo.type.MathQuestionType;
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
		int questionNumber = 1;
		for (; questionNumber <= optionService.getCalculateQuestionListSize(); questionNumber++) {
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
			question.setMathQuestionType(MathQuestionType.calculate);
			exerciesDTO.getQuestionList().add(question);
		}

		question = createWordProblemModule1();
		question.setQuestionNumber(questionNumber);
		exerciesDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule2();
		question.setQuestionNumber(questionNumber);
		exerciesDTO.getQuestionList().add(question);
		questionNumber++;
		
		question = createWordProblemModule3();
		question.setQuestionNumber(questionNumber);
		exerciesDTO.getQuestionList().add(question);
		questionNumber++;
		
		question = createWordProblemModule4();
		question.setQuestionNumber(questionNumber);
		exerciesDTO.getQuestionList().add(question);
		questionNumber++;
		
		question = createWordProblemModule5();
		question.setQuestionNumber(questionNumber);
		exerciesDTO.getQuestionList().add(question);
		questionNumber++;

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
		q.setMathQuestionType(MathQuestionType.wordProblem);
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
		q.setMathQuestionType(MathQuestionType.wordProblem);
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
		q.setMathQuestionType(MathQuestionType.wordProblem);
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
		q.setMathQuestionType(MathQuestionType.wordProblem);
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
		q.setMathQuestionType(MathQuestionType.wordProblem);
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
		q.setMathQuestionType(MathQuestionType.wordProblem);
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
		q.setMathQuestionType(MathQuestionType.wordProblem);
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
		q.setMathQuestionType(MathQuestionType.calculate);
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

	private MathQuestionBaseDTO createWordProblemModule1() {
		String moduleStr = " 两个数的积是%d，如果一个因数不变，另一个因数缩小%d倍，则积是(    )。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.wordProblem);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int l1 = t.nextInt(3, 9 + 1);
		int l2 = t.nextInt(3, 9 + 1);
		int l3 = t.nextInt(3, 9 + 1);

		int r1 = l1 * l2;
		int r2 = l1 * l2 * l3;

		q.setExpression(String.format(moduleStr, r2, l3));
		q.addStandardAnswer(String.valueOf(r1));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule2() {
		String moduleStr = "在A÷%d=%d……B中，余数B最大可取(     )，这时被除数A是(     )。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.wordProblem);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int l1 = t.nextInt(11, 19 + 1);
		int l2 = t.nextInt(11, 19 + 1);

		int r1 = l1 * l2 + l1 - 1;

		q.setExpression(String.format(moduleStr, l1, l2));
		q.addStandardAnswer(String.valueOf(l1 - 1));
		q.addStandardAnswer(String.valueOf(r1));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule3() {
		String moduleStr = "如图，已知∠1＝%d°，<br>" + "∠2＝（    ），<br>" + "∠3＝（   ）。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.wordProblem);
		q.addImgUrlList("/image/getImage/?imgPK=GgF2AtKXoNbMF7SGEXUt%2BbG5vzDVyNE%2FyxNy3ntkHO0%3D");

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int l1 = t.nextInt(30, 89 + 1);

		int r1 = 90 - l1;
		int r2 = 180 - r1;

		q.setExpression(String.format(moduleStr, l1));
		q.addStandardAnswer(String.valueOf(r1));
		q.addStandardAnswer(String.valueOf(r2));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule4() {
		String moduleStr = "某校开展节约用电活动，前%d个月共节约用电%d度。照这样计算，一年（12月）能节约用电多少度？";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.wordProblem);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int powerCounter = t.nextInt(120, 220 + 1);
		int monthCounter = t.nextInt(3, 8 + 1);
		
		q.setExpression(String.format(moduleStr, monthCounter, (powerCounter * monthCounter)));
		q.addStandardAnswer(String.valueOf(powerCounter * 12));

		return q;
	}
	
	private MathQuestionBaseDTO createWordProblemModule5() {
		String moduleStr = "小东做乘法计算时，把其中一个因数%d看成了%d，<br>"
				+ "结果得到的积比正确的积%s了%d。正确的积应该是(         )。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.wordProblem);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int i1 = t.nextInt(25, 99 + 1);
		int correctNum = t.nextInt(25, 99 + 1);
		int wrongNum = t.nextInt(25, 99 + 1);
		
		while(correctNum == wrongNum) {
			wrongNum = t.nextInt(25, 99 + 1);
		}
		
		int correctResult = i1 * correctNum;
		int wrongResult = i1 * wrongNum;
		
		String dynamicWord = "多";
		if(wrongNum < correctNum) {
			dynamicWord = "少";
		}
		
		int gap = Math.abs(correctResult - wrongResult);
		
		q.setExpression(String.format(moduleStr, correctNum, wrongNum, dynamicWord, gap));
		q.addStandardAnswer(String.valueOf(correctResult));

		return q;
	}
}
