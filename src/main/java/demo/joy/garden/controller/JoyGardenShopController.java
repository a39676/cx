package demo.joy.garden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.joy.common.pojo.constant.JoyGardenShopUrl;
import demo.joy.common.pojo.constant.JoyGardenUrl;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.garden.pojo.dto.JoyGardenShopSeedSearchDTO;
import demo.joy.garden.service.JoyGardenShopService;

@RequestMapping(value = JoyUrl.ROOT + JoyGardenUrl.ROOT + JoyGardenShopUrl.ROOT)
@Controller
public class JoyGardenShopController extends CommonController {

	@Autowired
	private JoyGardenShopService shopService;
	
	@GetMapping(value = JoyGardenShopUrl.INDEX)
	public ModelAndView index() {
		return shopService.shop();
	}

	@PostMapping(value = JoyGardenShopUrl.SEED_SEARCH)
	public ModelAndView index(@RequestBody JoyGardenShopSeedSearchDTO dto) {
		return shopService.seedSearchView(dto);
	}

}
