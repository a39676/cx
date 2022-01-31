package demo.toyParts.educate.pojo.dto;

import java.util.List;

public class AnswerDTO {

	private String pk;

	private List<AnswerSubDTO> answerList;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<AnswerSubDTO> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<AnswerSubDTO> answerList) {
		this.answerList = answerList;
	}

	@Override
	public String toString() {
		return "AnswerDTO [pk=" + pk + "]";
	}

}
