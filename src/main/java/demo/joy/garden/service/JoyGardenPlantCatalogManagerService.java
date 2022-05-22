package demo.joy.garden.service;

import org.springframework.web.servlet.ModelAndView;

import demo.joy.garden.pojo.dto.JoyGardenPlantSearchConditionDTO;

public interface JoyGardenPlantCatalogManagerService {
	
	ModelAndView plantCatalogManager();

	ModelAndView plantSearch(JoyGardenPlantSearchConditionDTO dto);

}
