package demo.toyParts.complex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.service.impl.ToolOptionService;

@Controller
public class ComplexController {

	@Autowired
	private ToolOptionService toolConstantService;

	@GetMapping(value = "/1jlbdmb")
	@ResponseBody
	public String for_1jlbdmb() {
		return "pong";
	}

	@GetMapping(value = "/promote")
	public ModelAndView promote() {
		ModelAndView v = new ModelAndView("toolJSP/promotePage");
		v.addObject("promoteImgUrl", toolConstantService.getPromoteImgUrl());
		return v;
	}

}
