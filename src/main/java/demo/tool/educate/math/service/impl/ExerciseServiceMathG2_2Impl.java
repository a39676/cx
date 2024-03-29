package demo.tool.educate.math.service.impl;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.educate.math.service.ExerciseMathCommonService;
import demo.tool.educate.math.service.ExerciseServiceMathG2_2;
import demo.tool.educate.pojo.dto.MathExerciseDTO;
import demo.tool.educate.pojo.dto.MathQuestionBaseDTO;
import demo.tool.educate.pojo.result.ExerciseBuildResult;
import demo.tool.educate.pojo.type.GradeType;
import demo.tool.educate.pojo.type.MathBaseSymbolType;
import demo.tool.educate.pojo.type.MathQuestionType;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciseServiceMathG2_2Impl extends ExerciseMathCommonService implements ExerciseServiceMathG2_2 {

	private static final Integer MAX_ADDITION_NUM = 99;
	private static final Integer MIN_ADDITION_NUM = 0;
	private static final Integer MAX_DIVISION_NUM = 9;
	private static final Integer MIN_DIVISION_NUM = 3;
	private static final Integer MIN_RESULT = 0;
	private static final Integer MAX_RESULT = 10000;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_2_2;

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
			question = createCalculateQuestion();
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

		question = createWordProblemModule8();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule9();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule10();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule11();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule12();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		question = createWordProblemModule15();
		question.setQuestionNumber(questionNumber);
		exerciseDTO.getQuestionList().add(question);
		questionNumber++;

		return exerciseDTO;
	}

	private MathQuestionBaseDTO createCalculateQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathBaseSymbolType mathSymbolType1 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.division);
		String exp1 = null;
		Expression expression1 = null;
		Double result1 = -1D;
		MathQuestionBaseDTO tmpQuestionDTO = null;

		if (MathBaseSymbolType.division.equals(mathSymbolType1)) {
			tmpQuestionDTO = createBaseSimpleDivision();
			exp1 = tmpQuestionDTO.getExpression();
			expression1 = new ExpressionBuilder(exp1).build();
			result1 = expression1.evaluate();
		} else {
			while (result1 < MIN_RESULT || result1 > MAX_RESULT) {
				tmpQuestionDTO = createSimpleEquation(mathSymbolType1);
				exp1 = tmpQuestionDTO.getExpression();
				expression1 = new ExpressionBuilder(exp1).build();
				result1 = expression1.evaluate();
			}
		}

		MathBaseSymbolType mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
				MathBaseSymbolType.division);
//		if(MathBaseSymbolType.division.equals(mathSymbolType2)) {
//			System.out.println("///////////");
//		}
//		MathBaseSymbolType mathSymbolType2 = MathBaseSymbolType.division;

		String resultExpStr = null;
		Expression resultExpression = null;
		Double finalResult = null;
		Integer finalResult2 = null;

		if (MathBaseSymbolType.division.equals(mathSymbolType2)) {
			if (MathBaseSymbolType.addition.equals(mathSymbolType1)
					|| MathBaseSymbolType.subtraction.equals(mathSymbolType1)) {
				exp1 = "(" + exp1 + ")";
			}

			int num3 = 1;
			if (result1 == 0) {
				num3 = t.nextInt(MIN_ADDITION_NUM + 1, MAX_ADDITION_NUM + 1);
			} else if (result1 > 0 && result1 < 6) {
				num3 = 1;
			} else {
				num3 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
			}
			resultExpStr = String.valueOf(exp1 + MathBaseSymbolType.division.getCodeSymbol() + num3);
			resultExpression = new ExpressionBuilder(resultExpStr).build();
			finalResult = resultExpression.evaluate();
			if (finalResult % 1 != 0) {
				finalResult = finalResult.intValue() + 0D;
				finalResult2 = new BigDecimal(result1).subtract(new BigDecimal(finalResult * num3)).intValue();
			}
		} else {
			boolean numOrEquation = t.nextInt(0, 2) > 0;
			if (numOrEquation) {
				int num3 = 0;
				if (MathBaseSymbolType.subtraction.equals(mathSymbolType2)) {
					num3 = t.nextInt(MIN_ADDITION_NUM, result1.intValue() + 1);
				} else {
					num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
				}

				resultExpStr = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
				resultExpression = new ExpressionBuilder(resultExpStr).build();
				finalResult = resultExpression.evaluate();
				while (finalResult < MIN_RESULT || finalResult > MAX_RESULT) {
					num3 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
					resultExpStr = String.valueOf(exp1 + mathSymbolType2.getCodeSymbol() + num3);
					resultExpression = new ExpressionBuilder(resultExpStr).build();
					finalResult = resultExpression.evaluate();
				}
			} else {
				MathBaseSymbolType mathSymbolType3 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
						MathBaseSymbolType.multiplication);
				boolean brackets = (MathBaseSymbolType.addition.equals(mathSymbolType3)
						|| MathBaseSymbolType.subtraction.equals(mathSymbolType3));

				tmpQuestionDTO = createSimpleEquation(mathSymbolType3);
				String exp3 = tmpQuestionDTO.getExpression();
				Expression expression3 = new ExpressionBuilder(exp3).build();
				Double result3 = expression3.evaluate();

				if (brackets) {
					exp3 = "(" + exp3 + ")";
				}

				resultExpStr = exp1 + mathSymbolType2.getCodeSymbol() + exp3;
				resultExpression = new ExpressionBuilder(resultExpStr).build();
				finalResult = resultExpression.evaluate();

				while (finalResult < MIN_RESULT || finalResult > MAX_RESULT) {
					if (MathBaseSymbolType.subtraction.equals(mathSymbolType2) && result1 < MAX_ADDITION_NUM / 2) {
						mathSymbolType2 = getRandomMathBaseSymbolType(MathBaseSymbolType.addition,
								MathBaseSymbolType.multiplication);
					}

					tmpQuestionDTO = createSimpleEquation(mathSymbolType3);
					exp3 = tmpQuestionDTO.getExpression();
					expression3 = new ExpressionBuilder(exp3).build();
					result3 = expression3.evaluate();

					if (brackets) {
						exp3 = "(" + exp3 + ")";
					}
					resultExpStr = exp1 + mathSymbolType2.getCodeSymbol() + exp3;
					resultExpression = new ExpressionBuilder(resultExpStr).build();
					finalResult = resultExpression.evaluate();
				}

				if (MathBaseSymbolType.division.equals(mathSymbolType2)) {
					if (finalResult % 1 != 0) {
						finalResult2 = new BigDecimal(result1)
								.subtract(new BigDecimal(result3).multiply(new BigDecimal(finalResult))).intValue();
					}
				}
			}
		}

		resultExpStr = replaceCodingSymbolToMathSymbol(resultExpStr);
		q.setExpression(resultExpStr);
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(finalResult.intValue()));
		if (finalResult2 != null) {
			q.getStandardAnswer().add(String.valueOf(finalResult2));
		}
		return q;
	}

	private MathQuestionBaseDTO createBaseSimpleDivision() {
		MathQuestionBaseDTO dto = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int multiplication_1 = t.nextInt(1, 9 + 1);
		int multiplication_2 = t.nextInt(1, 9 + 1);
		String exp = String
				.valueOf(multiplication_1 + MathBaseSymbolType.multiplication.getCodeSymbol() + multiplication_2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();

		exp = String.valueOf(result.intValue() + MathBaseSymbolType.division.getCodeSymbol() + multiplication_2);
		dto.setExpression(exp);
		dto.getStandardAnswer().add(String.valueOf(multiplication_1));
		return dto;
	}

	/** NOT for division!!! */
	private MathQuestionBaseDTO createSimpleEquation(MathBaseSymbolType symbolType) {
		MathQuestionBaseDTO dto = new MathQuestionBaseDTO();
		ThreadLocalRandom t = ThreadLocalRandom.current();
		int num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		int num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		String exp = String.valueOf(num1 + symbolType.getCodeSymbol() + num2);
		Expression expression = new ExpressionBuilder(exp).build();
		Double result = expression.evaluate();
		while (result < MIN_RESULT || result > MAX_RESULT) {
			num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
			num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
			exp = String.valueOf(num1 + symbolType.getCodeSymbol() + num2);
			expression = new ExpressionBuilder(exp).build();
			result = expression.evaluate();
		}

		dto.setExpression(exp);
		dto.getStandardAnswer().add(String.valueOf(result));
		return dto;
	}

	private MathQuestionBaseDTO createWordProblemModule1() {
		String moduleStr = "%s运来一批%s, 卖了%d%s, 还剩%d%s, 运来%s多少%s";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "书店", "小说", "本" });
		dynamicKeyWord.add(new String[] { "水果店", "香蕉", "斤" });
		dynamicKeyWord.add(new String[] { "玩具店", "篮球", "个" });
		dynamicKeyWord.add(new String[] { "饮料店", "可乐", "瓶" });
		dynamicKeyWord.add(new String[] { "娃娃店", "娃娃", "个" });
		dynamicKeyWord.add(new String[] { "电脑店", "显示器", "台" });
		dynamicKeyWord.add(new String[] { "摄影店", "镜头", "个" });
		dynamicKeyWord.add(new String[] { "宠物店", "小猫", "只" });
		dynamicKeyWord.add(new String[] { "文具店", "圆珠笔", "支" });
		dynamicKeyWord.add(new String[] { "家电店", "电饭煲", "台" });
		dynamicKeyWord.add(new String[] { "家电店", "空调", "台" });
		dynamicKeyWord.add(new String[] { "家电店", "电视", "台" });
		dynamicKeyWord.add(new String[] { "家电店", "电冰箱", "台" });
		dynamicKeyWord.add(new String[] { "家电店", "吸尘器", "台" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();
		int num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		int num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);

		int result = num1 + num2;
		while (result < MIN_RESULT || result > MAX_RESULT) {
			num1 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
			num2 = t.nextInt(MIN_ADDITION_NUM, MAX_ADDITION_NUM + 1);
		}

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);
		q.setExpression(String.format(moduleStr, keyWord[0], keyWord[1], num1, keyWord[2], num2, keyWord[2], keyWord[1],
				keyWord[2]));
		q.addStandardAnswer(String.valueOf(result));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule2() {
		String moduleStr = "%s今年%d岁, %s今年%d岁, 小朋友比%s%s%d岁.<br>" + "(1)%s比%s大多少岁 <br>" + "(2)小朋友今年多少岁 ";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "爷爷", "表姑", "-" });
		dynamicKeyWord.add(new String[] { "大舅", "姐姐", "-" });
		dynamicKeyWord.add(new String[] { "哥哥", "弟弟", "+" });
		dynamicKeyWord.add(new String[] { "姐姐", "妹妹", "+" });
		dynamicKeyWord.add(new String[] { "姑姑", "哥哥", "-" });
		dynamicKeyWord.add(new String[] { "奶奶", "婶婶", "-" });
		dynamicKeyWord.add(new String[] { "外公", "表哥", "-" });
		dynamicKeyWord.add(new String[] { "外婆", "弟弟", "+" });
		dynamicKeyWord.add(new String[] { "妈妈", "表妹", "+" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		int num1 = 0;
		int num2 = 0;
		int num3 = 0;

		num1 = t.nextInt(30, MAX_ADDITION_NUM + 1);
		num2 = t.nextInt(25, num1 + 1);
		if ("-".equals(keyWord[2])) {
			num3 = t.nextInt(3, num2 + 1);
			q.setExpression(String.format(moduleStr, keyWord[0], num1, keyWord[1], num2, keyWord[1], "小", (num2 - num3),
					keyWord[0], keyWord[1]));
		} else {
			num3 = t.nextInt(num2, num1 + 1);
			q.setExpression(String.format(moduleStr, keyWord[0], num1, keyWord[1], num2, keyWord[1], "大", (num3 - num2),
					keyWord[0], keyWord[1]));
		}

		q.addStandardAnswer(String.valueOf(num1 - num2));
		q.addStandardAnswer(String.valueOf(num3));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule3() {
		String moduleStr = "一%s%s, 可以买%d%s%s, 如果每%s%s%d元,<br>" + "(1)每%s%s几元?<br>" + "(2)一%s%s比一%s%s便宜多少元?";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "衣服", "件", "帽子", "顶" });
		dynamicKeyWord.add(new String[] { "大衣", "件", "短袖", "件" });
		dynamicKeyWord.add(new String[] { "外套", "件", "鞋子", "对" });
		dynamicKeyWord.add(new String[] { "大娃娃", "个", "小玩具", "个" });
		dynamicKeyWord.add(new String[] { "蛋糕", "个", "棒棒糖", "个" });
		dynamicKeyWord.add(new String[] { "圆珠笔", "支", "铅笔", "支" });
		dynamicKeyWord.add(new String[] { "钢笔", "支", "铅笔", "支" });
		dynamicKeyWord.add(new String[] { "电视", "台", "遥控", "个" });
		dynamicKeyWord.add(new String[] { "笔记本电脑", "台", "台式电脑", "台" });
		dynamicKeyWord.add(new String[] { "兰花", "盆", "金银花", "盆" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		int item2Price = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int multiplication = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int item1Price = item2Price * multiplication;

//		String moduleStr = "一%s%s, 可以买%d%s%s, 如果每%s%s%d元, (1)每%s%s几元? (2)一%s%s比一%s%s便宜多少元?";
		q.setExpression(String.format(moduleStr, keyWord[1], keyWord[0], // 一%s%s
				multiplication, keyWord[3], keyWord[2], // 可以买%d%s%s,
				keyWord[1], keyWord[0], item1Price, // 如果每%s%s%d元
				keyWord[3], keyWord[2], // (1)每%s%s几元?
				keyWord[3], keyWord[2], keyWord[1], keyWord[0] // (2)一%s%s比一%s%s便宜多少元?";
		));

		q.addStandardAnswer(String.valueOf(item2Price));
		q.addStandardAnswer(String.valueOf(item1Price - item2Price));

		return q;
	}

	/**
	 * 商店有%s%d%s, %s%d%s, %s%d%s, %s想用%d%s%s, %d%s%s, %d%s%s, 做成%s%s,
	 * 这些%s最多可以做成多少个这样的%s%s
	 */
	private MathQuestionBaseDTO createWordProblemModule4() {
		String moduleStr = "商店有%s%d%s, %s%d%s, %s%d%s, %s想用%d%s%s, %d%s%s, %d%s%s,<br>"
				+ "做成%s%s, 这些%s最多可以做成多少个这样的%s%s";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "个", "苹果", "雪梨", "橙", "水果", "礼盒", "李叔叔" });
		dynamicKeyWord.add(new String[] { "支", "蜡笔", "颜色笔", "水彩笔", "文具", "套装", "张叔叔" });
		dynamicKeyWord.add(new String[] { "张", "红色", "蓝色", "黄色", "卡纸", "组合", "王叔叔" });
		dynamicKeyWord.add(new String[] { "颗", "草莓糖果", "芒果糖果", "橘子糖果", "糖果", "礼包", "何叔叔" });
		dynamicKeyWord.add(new String[] { "个", "兔子印章", "精灵印章", "小熊印章", "印章", "套装", "李阿姨" });
		dynamicKeyWord.add(new String[] { "颗", "棉花糖", "水果软糖", "花生糖", "零食", "大礼包", "张阿姨" });
		dynamicKeyWord.add(new String[] { "条", "绿色丝带", "白色丝带", "彩色丝带", "丝带", "组合", "王阿姨" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		int item1base = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int item2base = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int item3base = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);

		int multiplication1 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int multiplication2 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int multiplication3 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);

		int random1Add = 0;
		if (item1base == MIN_DIVISION_NUM) {
			random1Add = 2;
		} else {
			random1Add = t.nextInt(MIN_DIVISION_NUM, item1base);
		}

		int random2Add = 0;
		if (item2base == MIN_DIVISION_NUM) {
			random2Add = 2;
		} else {
			random2Add = t.nextInt(MIN_DIVISION_NUM, item2base);
		}

		int random3Add = 0;
		if (item3base == MIN_DIVISION_NUM) {
			random3Add = 2;
		} else {
			random3Add = t.nextInt(MIN_DIVISION_NUM, item3base);
		}

		int item1Total = item1base * multiplication1 + random1Add;
		int item2Total = item2base * multiplication2 + random2Add;
		int item3Total = item3base * multiplication3 + random3Add;

//		String moduleStr = "商店有%s%d%s, %s%d%s, %s%d%s, %s想用%d%s%s, %d%s%s, %d%s%s, 做成%s%s, 这些%s最多可以做成多少个这样的%s%s";
//		dynamicKeyWord.add(new String[] { "条", "绿色丝带", "白色丝带", "彩色丝带", "丝带", "组合", "李叔叔" });
		q.setExpression(String.format(moduleStr, keyWord[1], item1Total, keyWord[0], keyWord[2], item2Total, keyWord[0],
				keyWord[3], item3Total, keyWord[0], // 商店有%s%d%s, %s%d%s, %s%d%s,
				keyWord[6], item1base, keyWord[0], keyWord[1], item2base, keyWord[0], keyWord[2], item3base, keyWord[0],
				keyWord[3], // %s想用%d%s%s, %d%s%s, %d%s%s,
				keyWord[4], keyWord[5], // 做成%s%s
				keyWord[4], keyWord[4], keyWord[5] // 这些%s最多可以做成多少个这样的%s%s"
		));

		int result = multiplication1;
		if (multiplication2 < result) {
			result = multiplication2;
		}
		if (multiplication3 < result) {
			result = multiplication3;
		}

		q.addStandardAnswer(String.valueOf(result));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule5() {
		String moduleStr = "一场篮球赛分为上半场和下半场, A班上半场得了%d分, 全场总分是%d分, %s,<br>" + "B班的全场总分是%d分,<br>" + "(1)B班下半场得了多少分";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int classAFirstHalfScore = t.nextInt(12, 50 + 1);
		int classBFirstHalfScore = t.nextInt(12, 50 + 1);
		int classASecondHalfScore = t.nextInt(12, 50 + 1);
		int classBSecondHalfScore = t.nextInt(12, 50 + 1);
		int total = classAFirstHalfScore + classBFirstHalfScore + classASecondHalfScore + classBSecondHalfScore;
		int classBTotalScore = classBFirstHalfScore + classBSecondHalfScore;

		String dynamicStr = "";
		if (classAFirstHalfScore > classBFirstHalfScore) {
			dynamicStr = "上半场A班比B班得分多" + (classAFirstHalfScore - classBFirstHalfScore) + "分";
		} else if (classAFirstHalfScore < classBFirstHalfScore) {
			dynamicStr = "上半场B班比A班得分多" + (classBFirstHalfScore - classAFirstHalfScore) + "分";
		} else {
			dynamicStr = "上半场两个班的得分一样多";
		}

//		String moduleStr = "一场篮球赛分为上半场和下半场, A班上半场得了%d分, 全场总分是%d分, %s, B班的全场总分是%d分, (1)B班下半场得了多少分";
		q.setExpression(String.format(moduleStr, classAFirstHalfScore, total, dynamicStr, classBTotalScore));

		q.addStandardAnswer(String.valueOf(classBSecondHalfScore));

		return q;
	}

	/** 时间计算 */
	private MathQuestionBaseDTO createWordProblemModule6() {
		String moduleStr = "现在时间是";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomHour = t.nextInt(0, 23 + 1);
		int randomMinute = t.nextInt(0, 59 + 1);
		LocalTime startTime = LocalTime.of(randomHour, randomMinute);
		int randomPass = 0;
		boolean isBefore = t.nextInt(0, 1 + 1) > 0;
		LocalTime time2 = null;
		if (isBefore) {
			randomPass = t.nextInt(randomMinute, 59 + 1);
			time2 = startTime.minusMinutes(randomPass);
			moduleStr = moduleStr + startTime + ", " + randomPass + "分钟之前, 是____";
		} else {
			randomPass = t.nextInt(60 - randomMinute, 59 + 1);
			time2 = startTime.plusMinutes(randomPass);
			moduleStr = moduleStr + startTime + ", 再过" + randomPass + "分钟之后, 是____";
		}

		q.setExpression(moduleStr);

		q.addStandardAnswer(String.valueOf(time2));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule7() {
		String moduleStr = "?÷%d=%d......__，__里最大可以填";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int i1 = t.nextInt(3, 9 + 1);
		int i2 = t.nextInt(3, 9 + 1);

		q.setExpression(String.format(moduleStr, i1, i2));

		q.addStandardAnswer(String.valueOf(i1 - 1));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule8() {
		String moduleStr = "?÷__=%d...%d，当__里填最小的数时，?等于( )";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int i1 = t.nextInt(3, 9 + 1);
		int i2 = t.nextInt(3, 9 + 1);

		q.setExpression(String.format(moduleStr, i1, i2));

		q.addStandardAnswer(String.valueOf((i2 + 1) * i1 + i2));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule9() {
		String moduleStr = "一条%s长%d米, 分割成%d米一段, 需要%d秒, 分割成%d米一段需要多少秒?";

		List<String> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add("钢管");
		dynamicKeyWord.add("木柱");
		dynamicKeyWord.add("竹子");
		dynamicKeyWord.add("电缆");
		dynamicKeyWord.add("水管");

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		int cuttingTimeInSecond = t.nextInt(5, 12);
		int subLong1 = t.nextInt(3, 8);
		int subLong2 = t.nextInt(9, 12);

		int materialLong = findLowestCommonMultiple(Arrays.asList(subLong1, subLong2, t.nextInt(6, 10)));
		int subCounting1 = materialLong / subLong1;

		int cuttingTimeTotalInSubLong1 = cuttingTimeInSecond * (subCounting1 - 1);
		int subCounting2 = materialLong / subLong2;
		int cuttingTimeTotalInSubLong2 = (subCounting2 - 1) * cuttingTimeInSecond;

//		String moduleStr = "一条%s长%d米, 分割成%d米一段, 需要%d秒, 分割成%d米一段需要多少秒?";
		q.setExpression(
				String.format(moduleStr, keyWord, materialLong, subLong1, cuttingTimeTotalInSubLong1, subLong2));

		q.addStandardAnswer(String.valueOf(cuttingTimeTotalInSubLong2));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule10() {
		String moduleStr = "为了丰富同学们的课外生活, %s老师买了%d%s%s和%d%s%s, 共付了%d元, 按价钱算, 一%s%s可以换%d%s%s, <br>那么一%s%s需要多少钱呢?";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "李", "副", "象棋", "跳棋" });
		dynamicKeyWord.add(new String[] { "张", "套", "水彩笔", "蜡笔" });
		dynamicKeyWord.add(new String[] { "王", "本", "小说", "画册" });
		dynamicKeyWord.add(new String[] { "何", "套", "乐高", "玩偶" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWords = dynamicKeyWord.get(randomKeyWordIndex);

		int priceOfObjB = t.nextInt(12, 24);
		int multiplication = t.nextInt(3, 12);
		int priceOfObjA = priceOfObjB * multiplication;

		int countingOfObjA = t.nextInt(3, 8);
		int countingOfObjB = t.nextInt(8, 15);

		int totalAmount = priceOfObjA * countingOfObjA + priceOfObjB * countingOfObjB;

		String unit = keyWords[1];
		String objAName = keyWords[2];
		String objBName = keyWords[3];

//		为了丰富同学们的课外生活, %s老师买了%d%s%s和%d%s%s, 共付了%d元, 按价钱算, 一%s%s可以换%d%s%s, 那么一%s%s需要多少钱呢?;
		q.setExpression(String.format(moduleStr, keyWords[0], countingOfObjA, unit, objAName, countingOfObjB, unit,
				objBName, totalAmount, unit, objAName, multiplication, unit, objBName, unit, objBName));

		q.addStandardAnswer(String.valueOf(priceOfObjB));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule11() {
		String moduleStr = "%s和%s比赛%s%s, %s%d分钟%s%d%s, %s%d分钟%s%d%s. 他们俩谁%s得比较快?";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "小红", "小明", "写", "字", "个" });
		dynamicKeyWord.add(new String[] { "小王", "小枫", "做", "题", "道" });
		dynamicKeyWord.add(new String[] { "小英", "小雪", "擦", "桌子", "张" });
		dynamicKeyWord.add(new String[] { "大明", "胖虎", "砍", "树", "棵" });
		dynamicKeyWord.add(new String[] { "小丽", "小星", "插", "花", "扎" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWords = dynamicKeyWord.get(randomKeyWordIndex);

		int aCountingPerMin = t.nextInt(12, 24);
		int bCountingPerMin = t.nextInt(12, 24);
		while (bCountingPerMin == aCountingPerMin) {
			bCountingPerMin = t.nextInt(12, 24);
		}

		int minOfA = t.nextInt(12, 24);
		int minOfB = t.nextInt(12, 24);
		while (minOfB == minOfA) {
			minOfB = t.nextInt(12, 24);
		}
		int totalOfA = aCountingPerMin * minOfA;
		int totalOfB = bCountingPerMin * minOfB;

		String answer = keyWords[0];
		if (aCountingPerMin < bCountingPerMin) {
			answer = keyWords[1];
		}

		String roleAName = keyWords[0];
		String roleBName = keyWords[1];
		String actionWord = keyWords[2];
		String objName = keyWords[3];
		String unit = keyWords[4];

//		"%s和%s比赛%s%s, %s%d分钟%s%d%s, %s%d分钟%s%d%s. 他们俩谁%s得比较快?";
		q.setExpression(String.format(moduleStr, roleAName, roleBName, actionWord, objName, roleAName, minOfA,
				actionWord, totalOfA, unit, roleBName, minOfB, actionWord, totalOfB, unit, actionWord));

		q.addStandardAnswer(String.valueOf(answer));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule12() {
		String moduleStr = "一道除法题, 除数是%d, 小华把被除数的%s位数字和%s位数字颠倒了, 结果除得的商是%d%s; 这道题的正确商应该是?";
		List<String> keyWord = new ArrayList<>(Arrays.asList("万", "千", "百", "十", "个"));

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomDivisor = t.nextInt(13, 39);
		int randomMultiplier = t.nextInt(257, 7693);
		int correctNum = 0;
		while (correctNum < 10000 || correctNum > 99999 || hadSameNum(correctNum)) {
			randomDivisor = t.nextInt(13, 39);
			randomMultiplier = t.nextInt(257, 7693);
			correctNum = randomDivisor * randomMultiplier;
		}
		int randomIndexA = t.nextInt(0, 5);
		int randomIndexB = t.nextInt(0, 5);
		while (randomIndexB == randomIndexA) {
			randomIndexB = t.nextInt(0, 5);
		}

		String indexACnName = keyWord.get(randomIndexA);
		String indexBCnName = keyWord.get(randomIndexB);

		char[] errorNumStrArray = String.valueOf(correctNum).toCharArray();
		char numCharA = errorNumStrArray[randomIndexA];
		errorNumStrArray[randomIndexA] = errorNumStrArray[randomIndexB];
		errorNumStrArray[randomIndexB] = numCharA;
		String errorNumStr = "";
		for (int i = 0; i < errorNumStrArray.length; i++) {
			errorNumStr = errorNumStr + String.valueOf(errorNumStrArray[i]);
		}
		Integer errorNum = Integer.parseInt(errorNumStr);
		int errorResult = errorNum / randomDivisor;
		int errorRemainder = errorNum % randomDivisor;
		String subDescript = "";
		if (errorRemainder != 0) {
			subDescript = ", 余" + errorRemainder;
		}

//		一道除法题, 除数是%d, 小华把被除数的%s位数字和%s位数字颠倒了, 结果除得的商是%d%s; 这道题的正确商应该是?
		q.setExpression(String.format(moduleStr, randomDivisor, indexACnName, indexBCnName, errorResult, subDescript));

		q.addStandardAnswer(String.valueOf(correctNum / randomDivisor));

		return q;
	}

	private boolean hadSameNum(int num) {
		String numStr = String.valueOf(num);
		Set<Character> set = new HashSet<>();
		for (int i = 0; i < numStr.length(); i++) {
			set.add(numStr.charAt(i));
		}
		return set.size() != numStr.length();
	}

	private MathQuestionBaseDTO createWordProblemModule15() {
		ThreadLocalRandom t = ThreadLocalRandom.current();
		String moduleStr = "%s, %s, %s 共有%d%s,<br> %s, %s, %s, %s的%s數則剛好相等.<br> 問%s, %s, %s各有多少%s?";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "小明", "小王", "小紅", "元", "錢" });
		dynamicKeyWord.add(new String[] { "小紅", "小綠", "小藍", "顆", "糖" });
		dynamicKeyWord.add(new String[] { "小狗", "小猴", "小兔", "條", "香蕉" });
		dynamicKeyWord.add(new String[] { "小強", "小桃", "小葉", "張", "貼紙" });

		String[] keyWords = dynamicKeyWord.get(t.nextInt(0, dynamicKeyWord.size()));
		String roleA = keyWords[0];
		String roleB = keyWords[1];
		String roleC = keyWords[2];
		String util = keyWords[3];
		String objName = keyWords[4];

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.WORD_PROBLEM);

		int total = 3;
		int randomMultiplication = t.nextInt(6, 12);
		total = total * randomMultiplication;
		int intA = randomMultiplication;
		int intB = randomMultiplication;
		int intC = randomMultiplication;

		int movingAB = t.nextInt(0 - randomMultiplication, randomMultiplication);
		while (movingAB == 0) {
			movingAB = t.nextInt(0 - randomMultiplication, randomMultiplication);
		}
		intA = intA - movingAB;
		intB = intB + movingAB;
		int movingBC = t.nextInt(0 - randomMultiplication, randomMultiplication);
		while (movingBC == 0) {
			movingBC = t.nextInt(0 - intB, intB);
		}
		intB = intB - movingBC;
		intC = intC + movingBC;

		String description1 = "";
		if (movingAB < 0) {
			description1 = "如果 " + roleA + " 給 " + roleB + " " + (0 - movingAB) + util + ", ";
		} else {
			description1 = "如果 " + roleB + " 給 " + roleA + " " + movingAB + util + ", ";
		}

		String description2 = "";
		if (movingBC < 0) {
			description2 = "然後 " + roleB + " 給 " + roleC + " " + (0 - movingBC) + util + ", ";
		} else {
			description2 = "然後 " + roleC + " 給 " + roleB + " " + movingBC + util + ", ";
		}

//		%s, %s, %s 共有%d%s, 
//		%s, %s, %s, %s的%s數則剛好相等, 
//		問%s, %s, %s各有多少%s?
		q.setExpression(String.format(moduleStr, roleA, roleB, roleC, total, (util + objName),
				(description1 + description2), roleA, roleB, roleC, objName, roleA, roleB, roleC, objName));

		q.setStandardAnswer(Arrays.asList(String.valueOf(intA), String.valueOf(intB), String.valueOf(intC)));

		return q;
	}
}
