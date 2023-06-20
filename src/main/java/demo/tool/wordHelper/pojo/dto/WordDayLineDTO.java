package demo.tool.wordHelper.pojo.dto;

import java.util.List;

public class WordDayLineDTO {

	private String dateStr;
	private List<WordDTO> wordList;

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public List<WordDTO> getWordList() {
		return wordList;
	}

	public void setWordList(List<WordDTO> wordList) {
		this.wordList = wordList;
	}

	@Override
	public String toString() {
		return "WordDayLineDTO [dateStr=" + dateStr + ", wordList=" + wordList + "]";
	}

}
