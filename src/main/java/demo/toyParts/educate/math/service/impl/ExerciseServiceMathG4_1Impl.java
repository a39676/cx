package demo.toyParts.educate.math.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.math.service.ExerciseMathCommonService;
import demo.toyParts.educate.math.service.ExerciseServiceMathG4_1;
import demo.toyParts.educate.pojo.dto.MathExerciseDTO;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.result.ExerciseBuildResult;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.pojo.type.MathQuestionType;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciseServiceMathG4_1Impl extends ExerciseMathCommonService implements ExerciseServiceMathG4_1 {

	private static final Integer MAX_CALCULATE = 99999;
	private static final Integer MAX_NUM = 99;
	private static final Integer MIN_NUM = 10;
	private static final Integer SCALE = 4;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_4_1;

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
		int calculateQuestionSize = 10;
		int questionNumber = 1;
		for (; questionNumber <= calculateQuestionSize; questionNumber++) {
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

		question = createWordProblemModule1();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule2();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule3();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule4();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule5();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;
		
		question = createWordProblemModule6();
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
			if(hasBracket) {
				exp = String.valueOf(num3 + MathBaseSymbolType.multiplication.getCodeSymbol() + "(" + num1 + mathSymbolType1.getCodeSymbol() + num2 + ")");
			} else {
				exp = String.valueOf(num3 + MathBaseSymbolType.multiplication.getCodeSymbol() + num1 + mathSymbolType1.getCodeSymbol() + num2);
			}
		}
		
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		BigDecimal resultInBigDecimal = new BigDecimal(result);
		
		exp = exp + MathBaseSymbolType.division.getCodeSymbol() + num4;
		expression = new ExpressionBuilder(exp).build();
		result = expression.evaluate();
		BigDecimal finalResultInBigDecimal = resultInBigDecimal.divide(new BigDecimal(num4), SCALE, RoundingMode.HALF_UP).stripTrailingZeros();;


		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("精确至" + SCALE + "位小数");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(finalResultInBigDecimal.toPlainString()));
		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule1() {
		String moduleStr = " 两个数的积是%d，如果一个因数不变，另一个因数缩小%d倍，则积是(    )。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

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
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

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
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);
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
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int powerCounter = t.nextInt(120, 220 + 1);
		int monthCounter = t.nextInt(3, 8 + 1);

		q.setExpression(String.format(moduleStr, monthCounter, (powerCounter * monthCounter)));
		q.addStandardAnswer(String.valueOf(powerCounter * 12));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule5() {
		String moduleStr = "小东做乘法计算时，把其中一个因数%d看成了%d，<br>" + "结果得到的积比正确的积%s了%d。正确的积应该是(         )。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int i1 = t.nextInt(25, 99 + 1);
		int correctNum = t.nextInt(25, 99 + 1);
		int wrongNum = t.nextInt(25, 99 + 1);

		while (correctNum == wrongNum) {
			wrongNum = t.nextInt(25, 99 + 1);
		}

		int correctResult = i1 * correctNum;
		int wrongResult = i1 * wrongNum;

		String dynamicWord = "多";
		if (wrongNum < correctNum) {
			dynamicWord = "少";
		}

		int gap = Math.abs(correctResult - wrongResult);

		q.setExpression(String.format(moduleStr, correctNum, wrongNum, dynamicWord, gap));
		q.addStandardAnswer(String.valueOf(correctResult));

		return q;
	}
	
	private MathQuestionBaseDTO createWordProblemModule6() {
		String moduleStr = "汽车上山的速度为每小时%d千米, 行驶了%d小时到达山顶, 下山时原路返回用了%d小时,<br>"
				+ " 汽车下山时平均每小时行驶多少千米?";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int speedForClimbing = t.nextInt(40, 80 + 1);
		int takeHourForClimbing = t.nextInt(8, 16 + 1);
		
		int totalKilometers = speedForClimbing * takeHourForClimbing;
		
		int takeHourForDownhill = takeHourForClimbing - 1;
		
		while (totalKilometers % takeHourForDownhill != 0) {
			takeHourForDownhill--;
		}
		int speedForDownhill = totalKilometers / takeHourForDownhill;


		q.setExpression(String.format(moduleStr, speedForClimbing, takeHourForClimbing, takeHourForDownhill));
		q.addStandardAnswer(String.valueOf(speedForDownhill));

		return q;
	}
	
	public static void main(String[] args) {
		ExerciseServiceMathG4_1Impl t = new ExerciseServiceMathG4_1Impl();
		int i = 0;
		while (i < 10) {
			MathQuestionBaseDTO q = t.createWordProblemModule6();
			System.out.println(q.toString());
			i++;
		}
	}
}
