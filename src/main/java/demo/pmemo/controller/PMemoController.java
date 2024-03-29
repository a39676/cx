package demo.pmemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.pmemo.pojo.constant.PMemoUrl;
import demo.pmemo.pojo.dto.SetPMemoDTO;
import demo.pmemo.service.PMemoService;

@Controller
@RequestMapping(value = PMemoUrl.ROOT)
public class PMemoController extends CommonController {

	@Autowired
	private PMemoService pMemoService;
	
	@GetMapping(value = PMemoUrl.GET)
	@ResponseBody
	public String get(@RequestParam("key") String key) {
		return pMemoService.getMemo(key);
	}
	
	@GetMapping(value = PMemoUrl.SET)
	public ModelAndView set() {
		return pMemoService.setMemoPage();
	}
	
	@PostMapping(value = PMemoUrl.SET)
	@ResponseBody
	public CommonResult set(@RequestBody SetPMemoDTO dto) {
		return pMemoService.setMemo(dto);
	}
	
}
