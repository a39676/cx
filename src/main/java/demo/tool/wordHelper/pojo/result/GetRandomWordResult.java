package demo.tool.wordHelper.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.wordHelper.pojo.dto.WordDTO;

public class GetRandomWordResult extends CommonResult {

	private List<WordDTO> wordList;

	public List<WordDTO> getWordList() {
		return wordList;
	}

	public void setWordList(List<WordDTO> wordList) {
		this.wordList = wordList;
	}

	@Override
	public String toString() {
		return "GetRandomWordResult [wordList=" + wordList + "]";
	}

}
