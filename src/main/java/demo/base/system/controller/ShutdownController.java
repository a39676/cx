package demo.base.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.base.system.pojo.constant.BaseUrl;
import demo.base.system.service.ShutdownService;

@Controller
@RequestMapping(value = BaseUrl.SHUTDOWN)
public class ShutdownController {

	@Autowired
	private ShutdownService shutdownService;

	@GetMapping(BaseUrl.SHUTDOWN)
	@ResponseBody
	public String shutdownContext() {
		return shutdownService.shutdownContext();

	}

}
