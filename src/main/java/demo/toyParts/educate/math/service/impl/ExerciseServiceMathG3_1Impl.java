package demo.toyParts.educate.math.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.math.service.ExerciseMathCommonService;
import demo.toyParts.educate.math.service.ExerciseServiceMathG3_1;
import demo.toyParts.educate.pojo.dto.MathExerciseDTO;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.result.ExerciseBuildResult;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.pojo.type.MathQuestionType;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciseServiceMathG3_1Impl extends ExerciseMathCommonService implements ExerciseServiceMathG3_1 {

	private static final Integer MIN_RESULT = 0;
	private static final Integer MAX_RESULT = 10000;
	private static final Integer MAX_ADDITION_NUM = 5000;
	private static final Integer MIN_ADDITION_NUM = 110;
	private static final Integer MAX_MULTIPLICATION_NUM = 9;
	private static final Integer MIN_MULTIPLICATION_NUM = 3;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_3_1;

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
		int questionNumber = 1;
		for (; questionNumber <= optionService.getCalculateQuestionListSize(); questionNumber++) {
			question = null;
			Integer standardAnswer = -1;
			while (standardAnswer < MIN_RESULT || standardAnswer > MAX_RESULT) {
				question = createQuestion();
				try {
					standardAnswer = Integer.parseInt(question.getStandardAnswer().get(0));
				} catch (Exception e) {
				}
			}
			question.setQuestionNumber(questionNumber);
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
		
		question = createWordProblemModule7();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		return exerciseDTO;
	}

	private MathQuestionBaseDTO createQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		int num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		int num3 = t.nextInt(MIN_MULTIPLICATION_NUM, MAX_MULTIPLICATION_NUM + 1);

		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.subtraction);
		MathBaseSymbolType multiplicationSymbol = MathBaseSymbolType.multiplication;

		boolean multiplecationFromLeft = (0 == t.nextInt(0, 1 + 1));
		String exp1 = String.valueOf("(" + num1 + mathSymbolType1.getCodeSymbol() + num2 + ")");

		Expression expression1 = new ExpressionBuilder(exp1).build();
		Double result1 = expression1.evaluate();

		if (result1 < MIN_RESULT) {
			if (multiplecationFromLeft) {
				exp1 = String.valueOf("(" + num2 + mathSymbolType1.getCodeSymbol() + num1 + ")");
			}
		}
		expression1 = new ExpressionBuilder(exp1).build();
		result1 = expression1.evaluate();

		String exp2 = null;
		if (multiplecationFromLeft) {
			exp2 = String.valueOf(num3 + multiplicationSymbol.getCodeSymbol() + exp1);
		} else {
			exp2 = String.valueOf(exp1 + multiplicationSymbol.getCodeSymbol() + num3);
		}

		Expression expression2 = new ExpressionBuilder(exp2).build();
		Double result2 = expression2.evaluate();

		exp2 = replaceCodingSymbolToMathSymbol(exp2);
		q.setExpression(exp2);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result2.intValue()));
		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule1() {
		String moduleStr = "跑道长%d米，同学们跑接力，每组%d人，每人跑一个往返，每组要跑（　 ）米。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int trackBase = t.nextInt(1, 10 + 1);
		int studentCount = t.nextInt(5, 10 + 1);

		int trackSize = trackBase * 100;

		int result = trackSize * studentCount * 2;

		q.setExpression(String.format(moduleStr, trackSize, studentCount));
		q.addStandardAnswer(String.valueOf(result));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule2() {
		String moduleStr = "商店里有%s、%s、%s三种%s，买%d%s%s%s的钱可以买%d%s%s%s和%d%s%s%s，<br>"
				+ "买%d%s%s%s的钱可以买%d%s%s%s。<br>"
				+ "买%d%s%s%s的钱可以买几%s%s%s？";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "个", "花瓶", "大", "中", "小" });
		dynamicKeyWord.add(new String[] { "条", "丝带", "粉", "白", "蓝" });
		dynamicKeyWord.add(new String[] { "件", "衣服", "红", "黄", "绿" });
		dynamicKeyWord.add(new String[] { "条", "裤子", "花", "黑", "棕" });
		dynamicKeyWord.add(new String[] { "顶", "帽子", "橙", "紫", "黑" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int i1 = t.nextInt(30, 40 + 1);
		int i2 = t.nextInt(20, 30);
		int i3 = t.nextInt(10, 20);

		int r = t.nextInt(2, 5 + 1);

		Integer lmcOfCodition1 = findLowestCommonMultiple(i2, (i1 * r + i2));
		int c1_i2_count = lmcOfCodition1 / i2;
		int c1_group_count = lmcOfCodition1 / (i1 * r + i2);

		Integer lmcOfCodition2 = findLowestCommonMultiple(i2, i3);
		int c2_i2_count = lmcOfCodition2 / i2;
		int c2_i3_count = lmcOfCodition2 / i3;

		Integer lmcOfCodition3 = findLowestCommonMultiple(i1, i3);
		int c3_i1_count = lmcOfCodition3 / i1;
		int c3_i3_count = lmcOfCodition3 / i3;

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);
		q.setExpression(String.format(moduleStr, keyWord[2], keyWord[3], keyWord[4], keyWord[1], // 商店里有%s、%s、%s三种%s
				c1_i2_count, keyWord[0], keyWord[3], keyWord[1], (c1_group_count * r), keyWord[0], keyWord[2],
				keyWord[1], c1_group_count, keyWord[0], keyWord[3], keyWord[1], // 买%d%s%s的钱可以买%d%s%s和d%s%s
				c2_i2_count, keyWord[0], keyWord[3], keyWord[1], c2_i3_count, keyWord[0], keyWord[4], keyWord[1], // 买%d%s%s的钱可以买%d%s%s
				c3_i1_count, keyWord[0], keyWord[2], keyWord[1], keyWord[0], keyWord[4], keyWord[1]// 买%d%s%s的钱可以买几%s%s？
		));
		q.addStandardAnswer(String.valueOf(c3_i3_count));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule3() {
		String moduleStr = "1个正方形被分成%d个相等的长方形，每个长方形的周长是%d厘米，正方形的周长是多少厘米？ ";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int rectangleCount = t.nextInt(3, 7 + 1);
		int rectangleShortSide = t.nextInt(10, 30 + 1);
		int rectanglePerimeter = rectangleShortSide * (1 + rectangleCount) * 2;
		int squarePreimeter = rectangleShortSide * rectangleCount * 4;

		q.setExpression(String.format(moduleStr, rectangleCount, rectanglePerimeter));
		q.addStandardAnswer(String.valueOf(squarePreimeter));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule4() {
		String moduleStr = "小明和%s的年龄加在一起是%d岁，%s今年的年龄是小明%d倍，%s今年多少岁？小明今年几岁？";

		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "妈妈" });
		dynamicKeyWord.add(new String[] { "爸爸" });
		dynamicKeyWord.add(new String[] { "表哥" });
		dynamicKeyWord.add(new String[] { "表姐" });
		dynamicKeyWord.add(new String[] { "姑姑" });
		dynamicKeyWord.add(new String[] { "舅舅" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int mingAge = t.nextInt(7, 12 + 1);
		int multiple = t.nextInt(3, 6 + 1);
		int otherGuyAge = mingAge * multiple;

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		q.setExpression(
				String.format(moduleStr, keyWord[0], (mingAge + otherGuyAge), keyWord[0], multiple, keyWord[0]));
		q.addStandardAnswer(String.valueOf(otherGuyAge));
		q.addStandardAnswer(String.valueOf(mingAge));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule5() {
		String moduleStr = "有一筐%s连筐重%d千克，卖掉一半%s后，连筐重%d千克，这筐%s原来有多重？";

		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "苹果" });
		dynamicKeyWord.add(new String[] { "雪梨" });
		dynamicKeyWord.add(new String[] { "草莓" });
		dynamicKeyWord.add(new String[] { "番茄" });
		dynamicKeyWord.add(new String[] { "青瓜" });
		dynamicKeyWord.add(new String[] { "冬瓜" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int netWeight = t.nextInt(56, 100 + 1);
		while (netWeight % 2 != 0) {
			netWeight = t.nextInt(56, 100 + 1);
		}
		int tareWeight = t.nextInt(3, 6 + 1);

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		q.setExpression(String.format(moduleStr, keyWord[0], (netWeight + tareWeight), keyWord[0],
				(tareWeight + netWeight / 2), keyWord[0]));
		q.addStandardAnswer(String.valueOf(netWeight));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule6() {
		String moduleStr = "商店买来%d%s%s和%d%s%s，共用%d元钱，每%s%s是每%s%s格的%d倍，<br>"
				+ "每%s%s和每%s%s各多少元？";

		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "支", "彩笔", "铅笔" });
		dynamicKeyWord.add(new String[] { "支", "钢笔", "圆珠笔" });
		dynamicKeyWord.add(new String[] { "本", "练习册", "草稿本" });
		dynamicKeyWord.add(new String[] { "个", "篮球", "排球" });
		dynamicKeyWord.add(new String[] { "对", "羽毛球拍", "乒乓球拍" });
		dynamicKeyWord.add(new String[] { "支", "固体胶", "浆糊" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int item2Price = t.nextInt(13, 100 + 1);
		int multiple = t.nextInt(3, 6 + 1);
		int item1Price = item2Price * multiple;

		int item1Count = t.nextInt(10, 20 + 1);
		int item2Count = t.nextInt(15, 30 + 1);

		int totalPrice = item1Price * item1Count + item2Price * item2Count;

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

//		商店买来%d%s%s和%d%s%s，共用%d元钱，每%s%s是每%s%s格的%d倍，每%s%s和每%s%s各多少元？"
		q.setExpression(String.format(moduleStr, item1Count, keyWord[0], keyWord[1], item2Count, keyWord[0], keyWord[2], // 商店买来%d%s%s和%d%s%s
				totalPrice, keyWord[0], keyWord[1], keyWord[0], keyWord[2], multiple, keyWord[0], keyWord[1],
				keyWord[0], keyWord[2]));
		q.addStandardAnswer(String.valueOf(item1Price));
		q.addStandardAnswer(String.valueOf(item2Price));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule7() {
		String moduleStr = "两个数的和是%d，小玲在抄题时，将其中一个加数个位上0丢掉了，结果算出是%d，这两个数分别是____和____。";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int i1 = t.nextInt(13, 100 + 1);
		int i2Small = t.nextInt(3, 9 + 1);
		int i2 = i2Small * 10;

		q.setExpression(String.format(moduleStr, (i1 + i2), (i1 + i2Small)));
		q.addStandardAnswer(String.valueOf(i1));
		q.addStandardAnswer(String.valueOf(i2));

		return q;
	}

	public static void main(String[] args) {
		ExerciseServiceMathG3_1Impl t = new ExerciseServiceMathG3_1Impl();
		int i = 0;
		while (i < 10) {
			MathQuestionBaseDTO q = t.createWordProblemModule6();
			System.out.println(q.toString());
			i++;
		}
	}
}
