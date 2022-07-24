package demo.toyParts.educate.math.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import demo.toyParts.educate.math.service.ExerciseMathCommonService;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.type.MathQuestionType;

public class SimpleAlgorithmGenerator extends ExerciseMathCommonService {

	public MathQuestionBaseDTO createSimpleAlgorithmQuestion() {
		ThreadLocalRandom t = ThreadLocalRandom.current();

		MathQuestionBaseDTO q = null;
		int num1 = t.nextInt(1, 12);
		if (num1 == 1) {
			q = createSimpleAlgorithm1();
		} else if (num1 == 2) {
			q = createSimpleAlgorithm2();
		} else if (num1 == 3) {
			q = createSimpleAlgorithm3();
		} else if (num1 == 4) {
			q = createSimpleAlgorithm4();
		} else if (num1 == 5) {
			q = createSimpleAlgorithm5();
		} else if (num1 == 6) {
			q = createSimpleAlgorithm6();
		} else if (num1 == 7) {
			q = createSimpleAlgorithm7();
		} else if (num1 == 8) {
			q = createSimpleAlgorithm8();
		} else if (num1 == 9) {
			q = createSimpleAlgorithm9();
		} else if (num1 == 10) {
			q = createSimpleAlgorithm10();
		} else if (num1 == 11) {
			q = createSimpleAlgorithm11();
		}

		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm1() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(123, 1000);
		int num2 = 1000 - num1;
		int num3 = t.nextInt(159, 500);
		int num4 = 500 - num3;

		String exp = num1 + " + " + num3 + " + " + num2 + " + " + num4;

		int result = num1 + num2 + num3 + num4;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm2() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(125, 1000);
		int num2 = t.nextInt(39, 100);
		int num3 = 100 - num2;

		String exp = num1 + " * " + num3 + " + " + num2 + " * " + num1;

		int result = num1 * num3 + num2 * num1;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm3() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(-2, 3);
		while (num1 == 0) {
			num1 = t.nextInt(-2, 3);
		}
		int num2 = t.nextInt(12, 50);

		String exp = num2 + " * " + (100 + num1);

		int result = num2 * (num1 + 100);

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm4() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(59, 99);

		String exp = num1 + " * 99 + " + num1;
		
		int result = num1 * 100;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm5() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(1, 10);
		num1 = num1 * 100 + num1;
		int num2 = num1 + t.nextInt(125, 500);

		String exp = num2 + " - " + num1;

		int result = num2 - num1;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm6() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int hunderdBase = t.nextInt(1, 8);
		int hunderd = hunderdBase * 100;
		int num2 = t.nextInt(hunderd / 3, hunderd);
		int num3 = hunderd - num2;
		int num1 = t.nextInt(hunderd, hunderd + 100);

		String exp = num1 + " - " + num3 + " - " + num2;

		int result = num1 - num3 - num2;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm7() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(2, 11);

		String exp = num1 * 8 + " * 125";

		int result = num1 * 1000;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm8() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(2, 11);
		while (num1 == 4) {
			num1 = t.nextInt(2, 11);
		}
		int num2 = t.nextInt(2, 11);
		while (num2 == 4) {
			num2 = t.nextInt(2, 11);
		}
		List<Integer> numList = new ArrayList<>();
		numList.add(25);
		numList.add(4);
		numList.add(num1);
		numList.add(num2);

		Collections.shuffle(numList);

		String exp = "";

		for (Integer i : numList) {
			exp = exp + i + " * ";
		}

		exp = exp.substring(0, exp.length() - 3);

		int result = num1 * num2 * 100;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm9() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int tenBase = t.nextInt(2, 10);
		int hundredBase = t.nextInt(11, 45);
		int tened = tenBase * 10;
		int num1 = 5;
		int num2 = tened / 5;

		String exp = hundredBase * 100 + " / " + num1 + " / " + num2;

		int result = hundredBase * 100 / tened;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm10() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int tenBase = t.nextInt(2, 10);
		int tened = tenBase * 10;
		int num2 = 5;
		int num3 = tened / 5;
		int num1 = t.nextInt(11, 45);

		String exp = num1 + " * " + num3 + " * " + num2;

		int result = num1 * tened;

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(result));
		return q;
	}

	private MathQuestionBaseDTO createSimpleAlgorithm11() {
		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		ThreadLocalRandom t = ThreadLocalRandom.current();

		int num1 = t.nextInt(3, 10);

		String exp = num1 * 100 + " / 25 / 4";

		exp = replaceCodingSymbolToMathSymbol(exp);
		q.setExpression(exp);
		q.setComment("请使用简便算法, 将演算过程写在草稿纸上, 以备复查");
		q.getStandardAnswer().clear();
		q.getStandardAnswer().add(String.valueOf(num1));
		return q;
	}

}
