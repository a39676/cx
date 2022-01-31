package demo.toyParts.educate.pojo.dto;

import java.math.BigDecimal;

import demo.toyParts.educate.pojo.type.MathBaseSymbolType;

public class MathQuestionBaseDTO {

	private Integer questionNumber;

	private boolean hasBrackets = false;

	private BigDecimal num1;

	private BigDecimal num2;

	private MathBaseSymbolType mathBaseSymbolType;

	private BigDecimal standardAnswer;

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public boolean getHasBrackets() {
		return hasBrackets;
	}

	public void setHasBrackets(boolean hasBrackets) {
		this.hasBrackets = hasBrackets;
	}

	public BigDecimal getNum1() {
		return num1;
	}

	public void setNum1(BigDecimal num1) {
		this.num1 = num1;
	}

	public BigDecimal getNum2() {
		return num2;
	}

	public void setNum2(BigDecimal num2) {
		this.num2 = num2;
	}

	public MathBaseSymbolType getMathBaseSymbolType() {
		return mathBaseSymbolType;
	}

	public void setMathBaseSymbolType(MathBaseSymbolType mathBaseSymbolType) {
		this.mathBaseSymbolType = mathBaseSymbolType;
	}

	public BigDecimal getStandardAnswer() {
		return standardAnswer;
	}

	public void setStandardAnswer(BigDecimal standardAnswer) {
		this.standardAnswer = standardAnswer;
	}

	@Override
	public String toString() {
		return "MathQuestionBaseDTO [questionNumber=" + questionNumber + ", hasBrackets=" + hasBrackets + ", num1="
				+ num1 + ", num2=" + num2 + ", mathBaseSymbolType=" + mathBaseSymbolType + ", standardAnswer="
				+ standardAnswer + "]";
	}

}
