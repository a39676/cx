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
import demo.joy.common.pojo.constant.JoyGardenShopUrl;
import demo.joy.common.pojo.constant.JoyGardenUrl;
import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.garden.pojo.dto.JoyGardenShopAddShopStoreDTO;
import demo.joy.garden.pojo.dto.JoyGardenShopSearchDTO;
import demo.joy.garden.service.JoyGardenShopManagerService;

@RequestMapping(value = JoyManagerUrl.ROOT + JoyGardenUrl.ROOT + JoyGardenShopUrl.ROOT)
@Controller
public class JoyGardenShopManagerController extends CommonController {

	@Autowired
	private JoyGardenShopManagerService shopManagerService;
	
	@GetMapping(value = JoyGardenShopUrl.INDEX)
	public ModelAndView index() {
		return shopManagerService.shopManagerView();
	}

	@PostMapping(value = JoyGardenShopUrl.PLANT_SEARCH)
	public ModelAndView index(@RequestBody JoyGardenShopSearchDTO dto) {
		return shopManagerService.plantShopSearchView(dto);
	}


	@PostMapping(value = JoyGardenShopUrl.ADD_SHOP_STORE)
	@ResponseBody
	public JoyCommonResult index(@RequestBody JoyGardenShopAddShopStoreDTO dto) {
		return shopManagerService.addShopStore(dto);
	}

}
