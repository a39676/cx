package demo.joy.image.npc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.icon.pojo.dto.BatchUploadIconDTO;
import demo.joy.image.icon.service.JoyIconManagerService;
import demo.joy.image.npc.pojo.constant.JoyImageNpcUrl;

@Controller
@RequestMapping(value = JoyManagerUrl.ROOT + JoyImageNpcUrl.ROOT)
public class JoyImageNpcManagerControllre {

	@Autowired
	private JoyIconManagerService iconManagerService;

	@GetMapping(value = JoyImageNpcUrl.UPLOAD)
	public ModelAndView uploadIcon() {
		return new ModelAndView("joyJSP/npc/uploadNpcImage");
	}

	@PostMapping(value = JoyImageNpcUrl.UPLOAD)
	@ResponseBody
	public JoyCommonResult uploadIcon(@RequestBody BatchUploadIconDTO dto) {
		return iconManagerService.batchUploadIcon(dto);
	}

}
