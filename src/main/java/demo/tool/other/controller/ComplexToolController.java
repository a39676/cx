package demo.tool.other.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.tool.other.pojo.constant.ToolUrlConstant;
import demo.tool.other.pojo.vo.EncryptIdVO;
import demo.tool.other.service.ComplexToolService;

@Controller
@RequestMapping(value = ToolUrlConstant.root)
public class ComplexToolController extends CommonController {
	
	@Autowired
	private ComplexToolService complexToolService;

	@GetMapping(value = "/systemOption")
	public ModelAndView systemOption() {
		return new ModelAndView("toolJSP/systemOption");
	}
	
	@GetMapping(value = "/encryptId")
	@ResponseBody
	public EncryptIdVO encryptIDNum(@RequestParam(value = "id", defaultValue = "0")Long id) {
		return complexToolService.encryptIDNum(id);
	}
	
	@GetMapping(value = "/decryptPK")
	@ResponseBody
	public Long decryptPK(@RequestParam(value = "pk", defaultValue = "")String pk) {
		return complexToolService.decryptPK(pk);
	}

}
