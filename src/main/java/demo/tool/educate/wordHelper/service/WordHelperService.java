package demo.tool.educate.wordHelper.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.educate.wordHelper.pojo.dto.GetWordDTO;
import demo.tool.educate.wordHelper.pojo.dto.UpdateOrAppendWordDTO;
import demo.tool.educate.wordHelper.pojo.dto.WordDTO;
import demo.tool.educate.wordHelper.pojo.result.GetWordResult;

public interface WordHelperService {

	ModelAndView wordHelper();

	GetWordResult printRandomWords(GetWordDTO dto);

	CommonResult updateOrAppendWord(UpdateOrAppendWordDTO dto);

	CommonResult addNewWord(WordDTO inputWord);

	GetWordResult findWords(WordDTO inputWord);

	CommonResult deleteWord(WordDTO dto);

	GetWordResult printNewWords(GetWordDTO dto);

	GetWordResult printNewWordsInMarks(GetWordDTO dto);

	GetWordResult printRandomWordsInMarks(GetWordDTO dto);

}
