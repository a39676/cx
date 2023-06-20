package demo.tool.wordHelper.pojo.dto;

import java.util.List;

public class CustomerDictionaryDTO {

	private List<WordDTO> markWordList;

	private List<WordDayLineDTO> wordDateLineList;

	public List<WordDTO> getMarkWordList() {
		return markWordList;
	}

	public void setMarkWordList(List<WordDTO> markWordList) {
		this.markWordList = markWordList;
	}

	public List<WordDayLineDTO> getWordDateLineList() {
		return wordDateLineList;
	}

	public void setWordDateLineList(List<WordDayLineDTO> wordDateLineList) {
		this.wordDateLineList = wordDateLineList;
	}

	@Override
	public String toString() {
		return "CustomerDictionaryDTO [markWordList=" + markWordList + ", wordDateLineList=" + wordDateLineList + "]";
	}

}
