package demo.tool.educate.wordHelper.pojo.dto;

import java.util.List;

public class CustomerDictionaryV2DTO {

	private List<WordDTO> wordList;

	public List<WordDTO> getWordList() {
		return wordList;
	}

	public void setWordList(List<WordDTO> wordList) {
		this.wordList = wordList;
	}

	@Override
	public String toString() {
		return "CustomerDictionaryV2DTO [wordList=" + wordList + "]";
	}

}
