package demo.tool.educate.wordHelper.pojo.vo;

import demo.tool.educate.wordHelper.pojo.dto.WordDTO;

public class WordVO extends WordDTO {

	private String enInMark;
	private String cnInMark;

	public String getEnInMark() {
		return enInMark;
	}

	public void setEnInMark(String enInMark) {
		this.enInMark = enInMark;
	}

	public String getCnInMark() {
		return cnInMark;
	}

	public void setCnInMark(String cnInMark) {
		this.cnInMark = cnInMark;
	}

	@Override
	public String toString() {
		return "WordVO [enInMark=" + enInMark + ", cnInMark=" + cnInMark + "]";
	}

}
