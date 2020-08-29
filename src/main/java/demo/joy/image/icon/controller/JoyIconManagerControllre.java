package demo.joy.image.icon.controller;

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
import demo.joy.image.icon.pojo.constanto.JoyIconUrl;
import demo.joy.image.icon.pojo.dto.BatchUploadIconDTO;
import demo.joy.image.icon.service.JoyIconService;

@Controller
@RequestMapping(value = JoyManagerUrl.ROOT + JoyIconUrl.ROOT)
public class JoyIconManagerControllre {

	@Autowired
	private JoyIconService iconService;

	@GetMapping(value = JoyIconUrl.UPLOAD)
	public ModelAndView uploadIcon() {
		return new ModelAndView("joyJSP/icon/uploadIcon");
	}

	@PostMapping(value = JoyIconUrl.UPLOAD)
	@ResponseBody
	public JoyCommonResult uploadIcon(@RequestBody BatchUploadIconDTO dto) {
		return iconService.batchUploadIcon(dto);
	}

}
