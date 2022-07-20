package demo.toyParts.educate.service.impl;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.toyParts.educate.pojo.dto.MathExerciesDTO;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.result.ExerciesBuildResult;
import demo.toyParts.educate.pojo.type.GradeType;
import demo.toyParts.educate.pojo.type.MathBaseSymbolType;
import demo.toyParts.educate.service.ExerciesMathCommonService;
import demo.toyParts.educate.service.ExerciesServiceMathG2_2;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class ExerciesServiceMathG2_2Impl extends ExerciesMathCommonService implements ExerciesServiceMathG2_2 {

	private static final Integer MAX_ADDITION_NUM = 99;
	private static final Integer MIN_ADDITION_NUM = 0;
	private static final Integer MAX_DIVISION_NUM = 9;
	private static final Integer MIN_DIVISION_NUM = 3;
	private static final Integer MIN_RESULT = 0;
	private static final Integer MAX_RESULT = 10000;
	private static final GradeType GRADE_TYPE = GradeType.GRADE_2_2;

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
		for (; questionNumber <= optionService.getQuestionListSize() - 6; questionNumber++) {
			question = createCalculateQuestion();
			question.setQuestionNumber(questionNumber);
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
		
		question = createWordProblemModule6();
		question.setQuestionNumber(questionNumber);
		exerciesDTO.getQuestionList().add(question);
		questionNumber++;

		return exerciesDTO;
	}

	private MathQuestionBaseDTO createCalculateQuestion() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
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
		String moduleStr = "%s今年%d岁, %s今年%d岁, 小朋友比%s%s%d岁. (1)%s比%s大多少岁 (2)小朋友今年多少岁 ";
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
		String moduleStr = "一%s%s, 可以买%d%s%s, 如果每%s%s%d元, (1)每%s%s几元? (2)一%s%s比一%s%s便宜多少元?";
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

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		int item2Price = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int times = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int item1Price = item2Price * times;

//		String moduleStr = "一%s%s, 可以买%d%s%s, 如果每%s%s%d元, (1)每%s%s几元? (2)一%s%s比一%s%s便宜多少元?";
		q.setExpression(String.format(moduleStr, keyWord[1], keyWord[0], // 一%s%s
				times, keyWord[3], keyWord[2], // 可以买%d%s%s,
				keyWord[1], keyWord[0], item1Price, // 如果每%s%s%d元
				keyWord[3], keyWord[2], // (1)每%s%s几元?
				keyWord[3], keyWord[2], keyWord[1], keyWord[0] // (2)一%s%s比一%s%s便宜多少元?";
		));

		q.addStandardAnswer(String.valueOf(item2Price));
		q.addStandardAnswer(String.valueOf(item1Price - item2Price));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule4() {
		String moduleStr = "商店有%s%d%s, %s%d%s, %s%d%s, %s想用%d%s%s, %d%s%s, %d%s%s, 做成%s%s, 这些%s最多可以做成多少个这样的%s%s";
		List<String[]> dynamicKeyWord = new ArrayList<>();
		dynamicKeyWord.add(new String[] { "个", "苹果", "雪梨", "橙", "水果", "礼盒", "李叔叔" });
		dynamicKeyWord.add(new String[] { "支", "蜡笔", "颜色笔", "水彩笔", "文具", "套装", "张叔叔" });
		dynamicKeyWord.add(new String[] { "张", "红色", "蓝色", "黄色", "卡纸", "组合", "王叔叔" });
		dynamicKeyWord.add(new String[] { "颗", "草莓糖果", "芒果糖果", "橘子糖果", "糖果", "礼包", "何叔叔" });
		dynamicKeyWord.add(new String[] { "个", "兔子印章", "精灵印章", "小熊印章", "印章", "套装", "李阿姨" });
		dynamicKeyWord.add(new String[] { "颗", "棉花糖", "水果软糖", "花生糖", "零食", "大礼包", "张阿姨" });
		dynamicKeyWord.add(new String[] { "条", "绿色丝带", "白色丝带", "彩色丝带", "丝带", "组合", "王阿姨" });

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomKeyWordIndex = t.nextInt(0, dynamicKeyWord.size());
		String[] keyWord = dynamicKeyWord.get(randomKeyWordIndex);

		int item1base = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int item2base = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int item3base = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);

		int times1 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int times2 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);
		int times3 = t.nextInt(MIN_DIVISION_NUM, MAX_DIVISION_NUM + 1);

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

		int item1Total = item1base * times1 + random1Add;
		int item2Total = item2base * times2 + random2Add;
		int item3Total = item3base * times3 + random3Add;

//		String moduleStr = "商店有%s%d%s, %s%d%s, %s%d%s, %s想用%d%s%s, %d%s%s, %d%s%s, 做成%s%s, 这些%s最多可以做成多少个这样的%s%s";
//		dynamicKeyWord.add(new String[] { "条", "绿色丝带", "白色丝带", "彩色丝带", "丝带", "组合", "李叔叔" });
		q.setExpression(String.format(moduleStr, keyWord[1], item1Total, keyWord[0], keyWord[2], item2Total, keyWord[0],
				keyWord[3], item3Total, keyWord[0], // 商店有%s%d%s, %s%d%s, %s%d%s,
				keyWord[6], item1base, keyWord[0], keyWord[1], item2base, keyWord[0], keyWord[2], item3base, keyWord[0],
				keyWord[3], // %s想用%d%s%s, %d%s%s, %d%s%s,
				keyWord[4], keyWord[5], // 做成%s%s
				keyWord[4], keyWord[4], keyWord[5] // 这些%s最多可以做成多少个这样的%s%s"
		));

		int result = times1;
		if (times2 < result) {
			result = times2;
		}
		if (times3 < result) {
			result = times3;
		}

		q.addStandardAnswer(String.valueOf(result));

		return q;
	}

	private MathQuestionBaseDTO createWordProblemModule5() {
		String moduleStr = "一场篮球赛分为上半场和下半场, A班上半场得了%d分, 全场总分是%d分, %s,B班的全场总分是%d分, (1)B班下半场得了多少分";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();

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

		ThreadLocalRandom t = ThreadLocalRandom.current();

		int randomHour = t.nextInt(0, 23 + 1);
		int randomMinute = t.nextInt(0, 59 + 1);
		LocalTime startTime = LocalTime.of(randomHour, randomMinute);
		int randomPass = 0;
		boolean isBefore = t.nextInt(0, 1 + 1) > 0;
		LocalTime time2 = null;
		if(isBefore) {
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

}
