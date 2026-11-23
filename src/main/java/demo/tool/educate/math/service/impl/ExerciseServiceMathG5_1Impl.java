package demo.tool.educate.math.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.educate.math.service.ExerciseMathCommonService;
import demo.tool.educate.math.service.ExerciseServiceMathG5_1;
import demo.tool.educate.pojo.dto.MathExerciseDTO;
import demo.tool.educate.pojo.dto.MathQuestionBaseDTO;
import demo.tool.educate.pojo.result.ExerciseBuildResult;
import demo.tool.educate.pojo.type.GradeType;
import demo.tool.educate.pojo.type.MathQuestionType;

// 2026-11-23 AI稿 仍有问题 未实际使用
@Service
public class ExerciseServiceMathG5_1Impl extends ExerciseMathCommonService implements ExerciseServiceMathG5_1 {

	private static final GradeType GRADE_TYPE = resolveGradeType();
	private static final int QUESTION_COUNT = 20;
	private static final int MIN_TERMS = 4;
	private static final int MAX_TERMS = 6;
	// 除不尽时精确到 n 位小数
	private static final int DECIMAL_SCALE_N = 2;
	// 是否允许生成带余数的题型
	private static final boolean ALLOW_REMAINDER = true;

	private static GradeType resolveGradeType() {
		for (GradeType gt : GradeType.values()) {
			if (gt.name().contains("5")) {
				return gt;
			}
		}
		return GradeType.GRADE_5_1;
	}

	@Override
	public ModelAndView getExercise() {
		return buildExerciseView(buildExercise());
	}

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
		exerciseDTO.setGradeType(GRADE_TYPE);

		List<MathQuestionBaseDTO> questionList = new ArrayList<>();
		for (int i = 1; i <= QUESTION_COUNT; i++) {
			MathQuestionBaseDTO q = createFlexibleEquationQuestion(DECIMAL_SCALE_N, ALLOW_REMAINDER);
			q.setQuestionNumber(i);
			q.setMathQuestionType(MathQuestionType.CALCULATE);
			questionList.add(q);
		}
		exerciseDTO.setQuestionList(questionList);
		return exerciseDTO;
	}

	/**
	 * 生成 4~6 个项随机运算符拼凑的方程（允许负数，支持精确到 n 位小数或余数）
	 */
	public MathQuestionBaseDTO createFlexibleEquationQuestion(int scaleN, boolean allowRemainder) {
		ThreadLocalRandom random = ThreadLocalRandom.current();

		// 约 25% 概率生成带余数的方程题（如 (ax + b) ÷ d 的商与余数，或反求被除数/未知数相关余数形式）
		if (allowRemainder && random.nextInt(4) == 0) {
			return buildRemainderEquationQuestion(random);
		}

		// 常规 4~6 项拼凑的一元一次方程：左边由 termCount 个项 + 随机运算符组成，右边为常数项计算结果
		int attempt = 0;
		while (attempt++ < 100) {
			int termCount = random.nextInt(MIN_TERMS, MAX_TERMS + 1); // 4 ~ 6 项
			LinearExpr left = buildRandomLinearTerms(random, termCount);
			if (left == null || left.coeffX.compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}

			// 右侧由 1~2 个随机常数运算得到
			BigDecimal rightVal = buildRandomConstantValue(random);

			// 方程：left.coeffX * x + left.constant = rightVal
			// 解：x = (rightVal - left.constant) / left.coeffX
			BigDecimal numerator = rightVal.subtract(left.constant);
			BigDecimal exactX;
			try {
				exactX = numerator.divide(left.coeffX, MathContext.DECIMAL128);
			} catch (ArithmeticException ex) {
				continue;
			}

			// 过滤极端过大或过小的解
			if (exactX.abs().compareTo(BigDecimal.valueOf(10000)) > 0) {
				continue;
			}

			BigDecimal answerX = exactX.setScale(scaleN, RoundingMode.HALF_UP).stripTrailingZeros();
			String expr = left.expression + " = " + formatSignedNum(rightVal.stripTrailingZeros());
			String comment = "允许负数；结果除不尽时精确到 " + scaleN + " 位小数";

			MathQuestionBaseDTO q = new MathQuestionBaseDTO();
			q.setMathQuestionType(MathQuestionType.CALCULATE);
			q.setExpression(expr);
			q.setComment(comment);
			q.getStandardAnswer().clear();
			q.addStandardAnswer(answerX.toPlainString());
			return q;
		}

		// 兜底方程
		MathQuestionBaseDTO fallback = new MathQuestionBaseDTO();
		fallback.setMathQuestionType(MathQuestionType.CALCULATE);
		fallback.setExpression("2x + (-4) + 6 - 3 = 9");
		fallback.setComment("允许负数；结果除不尽时精确到 " + scaleN + " 位小数");
		fallback.addStandardAnswer("5");
		return fallback;
	}

	/**
	 * 拼凑 termCount (4~6) 个项：包含含 x 的项（如 3x、-2x）和常数项（如 5、-8），中间用 + / - 衔接， 并随机对相邻项做 ×
	 * / ÷ 组合（保证除数不为 0 且不把 x 放进除数）
	 */
	private LinearExpr buildRandomLinearTerms(ThreadLocalRandom random, int termCount) {
		List<LinearExpr> items = new ArrayList<>();
		int xItemCount = 0;

		for (int i = 0; i < termCount; i++) {
			// 至少保证 1 个含 x 的项，最多 3 个含 x 的项，其余为常数项
			boolean makeXItem = false;
			if (xItemCount == 0 && i == termCount - 1) {
				makeXItem = true;
			} else if (xItemCount < 3) {
				makeXItem = random.nextBoolean();
			}

			if (makeXItem) {
				int coeff = randomNonZeroInt(random, -9, 9);
				String coeffStr = (coeff == 1) ? "x" : (coeff == -1 ? "-x" : coeff + "x");
				String display = (coeff < 0) ? "(" + coeffStr + ")" : coeffStr;
				items.add(new LinearExpr(BigDecimal.valueOf(coeff), BigDecimal.ZERO, display));
				xItemCount++;
			} else {
				int c = randomNonZeroInt(random, -20, 20);
				BigDecimal val = BigDecimal.valueOf(c);
				String display = (c < 0) ? "(" + c + ")" : String.valueOf(c);
				items.add(new LinearExpr(BigDecimal.ZERO, val, display));
			}
		}

		// 将相邻项随机用 +、-、×、÷ 合并，最终得到单个 LinearExpr
		while (items.size() > 1) {
			int idx = random.nextInt(items.size() - 1);
			LinearExpr a = items.get(idx);
			LinearExpr b = items.get(idx + 1);
			int op = random.nextInt(4); // 0:+, 1:-, 2:*, 3:/

			LinearExpr merged = null;
			if (op == 0) {
				merged = new LinearExpr(a.coeffX.add(b.coeffX), a.constant.add(b.constant),
						a.expression + " + " + b.expression);
			} else if (op == 1) {
				merged = new LinearExpr(a.coeffX.subtract(b.coeffX), a.constant.subtract(b.constant),
						a.expression + " - " + b.expression);
			} else if (op == 2) {
				// 仅当至少一方为纯常数时保持线性（a*x * k 或 k * (a*x + b)）
				if (b.coeffX.compareTo(BigDecimal.ZERO) == 0) {
					BigDecimal k = b.constant;
					merged = new LinearExpr(a.coeffX.multiply(k), a.constant.multiply(k),
							wrapIfNeeded(a, true) + " × " + b.expression);
				} else if (a.coeffX.compareTo(BigDecimal.ZERO) == 0) {
					BigDecimal k = a.constant;
					merged = new LinearExpr(b.coeffX.multiply(k), b.constant.multiply(k),
							a.expression + " × " + wrapIfNeeded(b, true));
				} else {
					// 两个含 x 项相乘会变成二次项，改为 +
					merged = new LinearExpr(a.coeffX.add(b.coeffX), a.constant.add(b.constant),
							a.expression + " + " + b.expression);
				}
			} else {
				// 除法：仅允许纯非零常数作除数，保持一元一次方程结构
				if (b.coeffX.compareTo(BigDecimal.ZERO) == 0 && b.constant.compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal divisor = b.constant;
					try {
						BigDecimal newCoeff = a.coeffX.divide(divisor, MathContext.DECIMAL128);
						BigDecimal newConst = a.constant.divide(divisor, MathContext.DECIMAL128);
						merged = new LinearExpr(newCoeff, newConst, wrapIfNeeded(a, true) + " ÷ " + b.expression);
					} catch (ArithmeticException ex) {
						merged = null;
					}
				} else {
					merged = new LinearExpr(a.coeffX.add(b.coeffX), a.constant.add(b.constant),
							a.expression + " + " + b.expression);
				}
			}

			if (merged == null) {
				return null;
			}
			items.set(idx, merged);
			items.remove(idx + 1);
		}

		return items.get(0);
	}

	private BigDecimal buildRandomConstantValue(ThreadLocalRandom random) {
		int c1 = randomNonZeroInt(random, -30, 30);
		int c2 = randomNonZeroInt(random, -15, 15);
		if (random.nextBoolean()) {
			return BigDecimal.valueOf(c1).add(BigDecimal.valueOf(c2));
		}
		return BigDecimal.valueOf(c1).subtract(BigDecimal.valueOf(c2));
	}

	/**
	 * 带余数题型：先随机生成整数 x 与多步算式拼凑被除数，再除以非零除数 d，得到商与余数
	 */
	private MathQuestionBaseDTO buildRemainderEquationQuestion(ThreadLocalRandom random) {
		int x = randomNonZeroInt(random, -15, 15);
		int a = randomNonZeroInt(random, 2, 8);
		int b = randomNonZeroInt(random, -20, 20);
		int c = randomNonZeroInt(random, -15, 15);
		int d = randomNonZeroInt(random, 2, 9); // 除数（允许负数可扩展为 -9 ~ 9 且 != 0）

		// 被除数 = a * x + b + c
		long dividend = (long) a * x + b + c;
		long quotient = dividend / d;
		long remainder = dividend % d;

		String expr = "(" + a + "x + (" + b + ") + (" + c + ")) ÷ " + d + " （当 x = " + x + " 时，求商和余数）";

		MathQuestionBaseDTO q = new MathQuestionBaseDTO();
		q.setMathQuestionType(MathQuestionType.CALCULATE);
		q.setExpression(expr);
		q.setComment("允许负数；除不尽时按 [商, 余数] 填写");
		q.getStandardAnswer().clear();
		q.addStandardAnswer(String.valueOf(quotient));
		q.addStandardAnswer(String.valueOf(remainder));
		return q;
	}

	private String wrapIfNeeded(LinearExpr expr, boolean isMulOrDiv) {
		if (!isMulOrDiv) {
			return expr.expression;
		}
		if (expr.expression.contains(" + ") || expr.expression.contains(" - ")) {
			return "(" + expr.expression + ")";
		}
		return expr.expression;
	}

	private String formatSignedNum(BigDecimal val) {
		return val.compareTo(BigDecimal.ZERO) < 0 ? "(" + val.toPlainString() + ")" : val.toPlainString();
	}

	private int randomNonZeroInt(ThreadLocalRandom random, int minInclusive, int maxInclusive) {
		int v = 0;
		while (v == 0) {
			v = random.nextInt(minInclusive, maxInclusive + 1);
		}
		return v;
	}

	private static class LinearExpr {
		final BigDecimal coeffX;
		final BigDecimal constant;
		final String expression;

		LinearExpr(BigDecimal coeffX, BigDecimal constant, String expression) {
			this.coeffX = coeffX;
			this.constant = constant;
			this.expression = expression;
		}
	}
}
