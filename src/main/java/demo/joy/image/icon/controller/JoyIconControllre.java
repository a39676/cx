package demo.joy.image.icon.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.image.icon.pojo.constanto.JoyIconUrl;
import demo.joy.image.icon.service.JoyIconService;

@Controller
@RequestMapping(value = JoyUrl.ROOT + JoyIconUrl.ROOT)
public class JoyIconControllre {

	@Autowired
	private JoyIconService iconService;
	
	@GetMapping(value = JoyIconUrl.GET_ICON)
	public void getIcon(@RequestParam(value = "id") Long id, HttpServletResponse response) {
		iconService.getIcon(id, response);
	}
}
