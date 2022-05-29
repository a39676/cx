package demo.joy.garden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.joy.common.pojo.constant.JoyGardenUrl;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.garden.pojo.dto.CreateNewGardenDTO;
import demo.joy.garden.service.JoyGradenInfoService;

@RequestMapping(value = JoyUrl.ROOT + JoyGardenUrl.ROOT)
@Controller
public class JoyGardenController extends CommonController {

	@Autowired
	private JoyGradenInfoService gardenInfoService;

	@GetMapping(value = JoyGardenUrl.INDEX)
	public ModelAndView index() {
		return gardenInfoService.index();
	}

	@PostMapping(value = JoyGardenUrl.CREAT_NEW_GARDEN)
	@ResponseBody
	public JoyCommonResult createNewGarden(@RequestBody CreateNewGardenDTO dto) {
		return gardenInfoService.createNewGarden(dto);
	}

	@PostMapping(value = JoyGardenUrl.CREAT_NEW_FIELD_LAND)
	@ResponseBody
	public JoyCommonResult createNewFieldLand() {
		return gardenInfoService.createNewFieldLand();
	}

}
