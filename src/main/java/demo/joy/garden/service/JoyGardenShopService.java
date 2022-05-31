package demo.joy.garden.service;

import org.springframework.web.servlet.ModelAndView;

import demo.joy.garden.pojo.dto.JoyGardenShopSeedSearchDTO;

public interface JoyGardenShopService {

	ModelAndView seedSearchView(JoyGardenShopSeedSearchDTO dto);

	ModelAndView shop();

}
