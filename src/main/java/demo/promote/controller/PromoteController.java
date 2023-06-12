package demo.promote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.promote.service.PromotePageService;

@Controller
public class PromoteController {

	@Autowired
	private PromotePageService promotePageService;

	@GetMapping(value = "/promote")
	public ModelAndView promotePage(@RequestParam(defaultValue = "0") String k) {
		return promotePageService.promote(k);
	}
}
