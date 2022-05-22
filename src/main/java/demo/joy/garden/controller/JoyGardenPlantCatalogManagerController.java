package demo.joy.garden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.constant.JoyGardenUrl;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.garden.pojo.dto.JoyGardenPlantSearchConditionDTO;
import demo.joy.garden.service.JoyGardenPlantCatalogManagerService;

@RequestMapping(value = JoyUrl.ROOT + JoyGardenUrl.ROOT)
@Controller
public class JoyGardenPlantCatalogManagerController {

	@Autowired
	private JoyGardenPlantCatalogManagerService plantCatalogManagerService;
	
	@GetMapping(value = JoyGardenUrl.PLANT_CATALOG_MANAGER)
	public ModelAndView plantCatalogManager() {
		return plantCatalogManagerService.plantCatalogManager();
	}
	
	@PostMapping(value = JoyGardenUrl.PLANT_CATALOG_SEARCH)
	public ModelAndView plantSearch(@RequestBody JoyGardenPlantSearchConditionDTO dto) {
		return plantCatalogManagerService.plantSearch(dto);
	}
}
