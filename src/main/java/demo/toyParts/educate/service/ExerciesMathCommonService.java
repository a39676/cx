package demo.toyParts.educate.service;

import java.util.concurrent.ThreadLocalRandom;

import demo.toyParts.educate.pojo.type.MathBaseSymbolType;

public abstract class ExerciesMathCommonService extends EducateCommonService {

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
	
}
