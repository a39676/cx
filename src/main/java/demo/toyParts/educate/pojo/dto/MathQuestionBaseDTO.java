package demo.toyParts.educate.pojo.dto;

import java.util.ArrayList;
import java.util.List;

import demo.toyParts.educate.pojo.type.MathQuestionType;

public class MathQuestionBaseDTO {

	private Integer questionNumber;

	private String comment;

	private MathQuestionType mathQuestionType = MathQuestionType.CALCULATE;

	private String expression;

	private List<String> imgUrlList;

	private List<String> standardAnswer = new ArrayList<>();

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public MathQuestionType getMathQuestionType() {
		return mathQuestionType;
	}

	public void setMathQuestionType(MathQuestionType mathQuestionType) {
		this.mathQuestionType = mathQuestionType;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public List<String> getImgUrlList() {
		return imgUrlList;
	}

	public void setImgUrlList(List<String> imgUrlList) {
		this.imgUrlList = imgUrlList;
	}

	public void addImgUrlList(String imgUrl) {
		if (this.imgUrlList == null) {
			this.imgUrlList = new ArrayList<>();
		}
		this.imgUrlList.add(imgUrl);
	}

	public List<String> getStandardAnswer() {
		return standardAnswer;
	}

	public void setStandardAnswer(List<String> standardAnswer) {
		this.standardAnswer = standardAnswer;
	}

	public void addStandardAnswer(String standardAnswer) {
		if (this.standardAnswer == null) {
			this.standardAnswer = new ArrayList<>();
		}
		this.standardAnswer.add(standardAnswer);
	}

	@Override
	public String toString() {
		return "MathQuestionBaseDTO [questionNumber=" + questionNumber + ", comment=" + comment + ", mathQuestionType="
				+ mathQuestionType + ", expression=" + expression + ", imgUrlList=" + imgUrlList + ", standardAnswer="
				+ standardAnswer + "]";
	}

}
