package demo.joy.garden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.garden.service.JoyGardenPlantCatalogManagerService;

@RequestMapping(value = JoyUrl.ROOT)
@Controller
public class JoyGardenPlantCatalogManagerController {

	@Autowired
	private JoyGardenPlantCatalogManagerService plantCatalogManagerService;
	
	
}
