package demo.tool.educate.wordHelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.educate.wordHelper.pojo.constant.WordHelperUrl;
import demo.tool.educate.wordHelper.pojo.dto.GetWordDTO;
import demo.tool.educate.wordHelper.pojo.dto.UpdateOrAppendWordDTO;
import demo.tool.educate.wordHelper.pojo.dto.WordDTO;
import demo.tool.educate.wordHelper.pojo.result.GetWordResult;
import demo.tool.educate.wordHelper.service.WordHelperService;

@Controller
@RequestMapping(value = WordHelperUrl.ROOT)
public class WordHelperController {

	@Autowired
	private WordHelperService wordHelperService;

	@GetMapping(value = WordHelperUrl.ROOT)
	public ModelAndView wordHelper() {
		return wordHelperService.wordHelper();
	}

	@PostMapping(value = WordHelperUrl.ADD)
	@ResponseBody
	public CommonResult addNewWord(@RequestBody WordDTO dto) {
		return wordHelperService.addNewWord(dto);
	}

	@PostMapping(value = WordHelperUrl.FIND)
	@ResponseBody
	public GetWordResult findWord(@RequestBody WordDTO dto) {
		return wordHelperService.findWords(dto);
	}

	@PostMapping(value = WordHelperUrl.EDIT)
	@ResponseBody
	public CommonResult updateOrAppendWord(@RequestBody UpdateOrAppendWordDTO dto) {
		return wordHelperService.updateOrAppendWord(dto);
	}

	@PostMapping(value = WordHelperUrl.PRINT_RANDOM)
	@ResponseBody
	public GetWordResult printRandomWords(@RequestBody GetWordDTO dto) {
		return wordHelperService.printRandomWords(dto);
	}

	@PostMapping(value = WordHelperUrl.PRINT_NEW)
	@ResponseBody
	public GetWordResult printNewWords(@RequestBody GetWordDTO dto) {
		return wordHelperService.printNewWords(dto);
	}

	@PostMapping(value = WordHelperUrl.DELETE)
	@ResponseBody
	public CommonResult deleteWord(@RequestBody WordDTO dto) {
		return wordHelperService.deleteWord(dto);
	}
	
	@PostMapping(value = WordHelperUrl.PRINT_RANDOM_IN_MARKS)
	@ResponseBody
	public GetWordResult printRandomWordsInMarks(@RequestBody GetWordDTO dto) {
		return wordHelperService.printRandomWordsInMarks(dto);
	}

	@PostMapping(value = WordHelperUrl.PRINT_NEW_IN_MARKS)
	@ResponseBody
	public GetWordResult printNewWordsInMarks(@RequestBody GetWordDTO dto) {
		return wordHelperService.printNewWordsInMarks(dto);
	}
}
