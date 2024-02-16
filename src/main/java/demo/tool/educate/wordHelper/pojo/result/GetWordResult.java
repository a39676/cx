package demo.tool.educate.wordHelper.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.educate.wordHelper.pojo.vo.WordVO;

public class GetWordResult extends CommonResult {

	private List<WordVO> wordList;

	public List<WordVO> getWordList() {
		return wordList;
	}

	public void setWordList(List<WordVO> wordList) {
		this.wordList = wordList;
	}

	@Override
	public String toString() {
		return "GetRandomWordResult [wordList=" + wordList + "]";
	}

}
