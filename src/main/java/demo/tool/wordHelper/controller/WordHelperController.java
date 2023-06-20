package demo.tool.wordHelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.wordHelper.pojo.dto.GetRandomWordDTO;
import demo.tool.wordHelper.pojo.dto.UpdateOrAppendWordDTO;
import demo.tool.wordHelper.pojo.dto.WordDTO;
import demo.tool.wordHelper.pojo.result.GetRandomWordResult;
import demo.tool.wordHelper.service.WordHelperService;

@Controller
@RequestMapping(value = "/wordHelper")
public class WordHelperController {

	@Autowired
	private WordHelperService wordHelperService;
	
	@GetMapping(value = "/wordHelper")
	public ModelAndView wordHelper() {
		return wordHelperService.wordHelper();
	}

	@PostMapping(value = "/addNewWord")
	@ResponseBody
	public CommonResult addNewWord(@RequestBody WordDTO dto) {
		return wordHelperService.addNewWord(dto);
	}

	@PostMapping(value = "/updateOrAppendWord")
	@ResponseBody
	public CommonResult updateOrAppendWord(@RequestBody UpdateOrAppendWordDTO dto) {
		return wordHelperService.updateOrAppendWord(dto);
	}

	@PostMapping(value = "/printRandomWords")
	@ResponseBody
	public GetRandomWordResult printRandomWords(@RequestBody GetRandomWordDTO dto) {
		return wordHelperService.printRandomWords(dto);
	}
}
