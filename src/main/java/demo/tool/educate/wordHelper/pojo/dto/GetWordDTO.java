package demo.tool.educate.wordHelper.pojo.dto;

public class GetWordDTO {

	private Integer wordCount = 10;
	private Boolean enInMark = false;

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	public Boolean getEnInMark() {
		return enInMark;
	}

	public void setEnInMark(Boolean enInMark) {
		this.enInMark = enInMark;
	}

	@Override
	public String toString() {
		return "GetWordDTO [wordCount=" + wordCount + ", enInMark=" + enInMark + "]";
	}

}
