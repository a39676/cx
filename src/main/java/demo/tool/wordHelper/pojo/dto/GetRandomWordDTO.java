package demo.tool.wordHelper.pojo.dto;

public class GetRandomWordDTO {

	private Integer wordCount = 10;

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	@Override
	public String toString() {
		return "GetRandomWordDTO [wordCount=" + wordCount + "]";
	}

}
