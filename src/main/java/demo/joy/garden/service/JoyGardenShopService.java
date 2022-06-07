package demo.joy.garden.service;

import org.springframework.web.servlet.ModelAndView;

import demo.joy.garden.pojo.dto.JoyGardenShopSearchDTO;

public interface JoyGardenShopService {

	ModelAndView shop();

	ModelAndView shopStoreView(JoyGardenShopSearchDTO dto);

}
