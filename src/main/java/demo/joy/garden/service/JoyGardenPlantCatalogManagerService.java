package demo.joy.garden.service;

import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantDTO;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantStageDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantSearchConditionDTO;
import demo.joy.garden.pojo.dto.ShowPlantStageMangerDTO;

public interface JoyGardenPlantCatalogManagerService {
	
	ModelAndView plantCatalogManager();
	
	ModelAndView plantCatalogCreator();

	ModelAndView plantSearch(JoyGardenPlantSearchConditionDTO dto);

	JoyCommonResult createNewPlant(JoyGardenCreatePlantDTO dto);

	JoyCommonResult createNewPlantStage(JoyGardenCreatePlantStageDTO dto);

	ModelAndView plantStageManger(ShowPlantStageMangerDTO dto);

}
