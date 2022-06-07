package demo.joy.garden.service;

import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.garden.pojo.dto.JoyGardenShopAddShopStoreDTO;
import demo.joy.garden.pojo.dto.JoyGardenShopSearchDTO;

public interface JoyGardenShopManagerService {

	ModelAndView shopManagerView();

	ModelAndView plantShopSearchView(JoyGardenShopSearchDTO dto);

	JoyCommonResult addShopStore(JoyGardenShopAddShopStoreDTO dto);

}
