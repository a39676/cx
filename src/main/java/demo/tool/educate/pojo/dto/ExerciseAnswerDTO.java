package demo.tool.educate.pojo.dto;

import java.util.List;

public class ExerciseAnswerDTO {

	private String pk;

	private List<AnswerElementDTO> answerList;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<AnswerElementDTO> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<AnswerElementDTO> answerList) {
		this.answerList = answerList;
	}

	@Override
	public String toString() {
		return "AnswerDTO [pk=" + pk + "]";
	}

}
