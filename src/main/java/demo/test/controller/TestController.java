package demo.test.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.root })
public class TestController extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;

	@GetMapping(value = { "/testException" })
	public ModelAndView testException(HttpServletRequest request) {
		log.debug("dateTime: {}", new Date());
		log.error("dateTime error: {}", new Date());

		ModelAndView v = new ModelAndView();
		return v;
	}

}
