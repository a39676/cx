package demo.tool.wordHelper.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.wordHelper.pojo.dto.GetRandomWordDTO;
import demo.tool.wordHelper.pojo.dto.UpdateOrAppendWordDTO;
import demo.tool.wordHelper.pojo.dto.WordDTO;
import demo.tool.wordHelper.pojo.result.GetRandomWordResult;

public interface WordHelperService {

	ModelAndView wordHelper();

	GetRandomWordResult printRandomWords(GetRandomWordDTO dto);

	CommonResult updateOrAppendWord(UpdateOrAppendWordDTO dto);

	CommonResult addNewWord(WordDTO inputWord);

	GetRandomWordResult findWords(WordDTO inputWord);

	CommonResult deleteWord(WordDTO dto);

}
