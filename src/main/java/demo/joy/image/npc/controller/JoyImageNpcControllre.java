package demo.joy.image.npc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.image.npc.pojo.constant.JoyImageNpcUrl;
import demo.joy.image.npc.service.JoyImageNpcService;

@Controller
@RequestMapping(value = JoyUrl.ROOT + JoyImageNpcUrl.ROOT)
public class JoyImageNpcControllre {

	@Autowired
	private JoyImageNpcService imageNpcService;
	
	@GetMapping(value = JoyImageNpcUrl.GET_NPC_IMAGE)
	public void getIcon(@RequestParam(value = "id") Long id, HttpServletResponse response) {
		imageNpcService.getImageNpc(id, response);
	}
}
