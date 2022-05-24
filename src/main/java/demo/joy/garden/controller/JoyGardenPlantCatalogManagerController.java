package demo.joy.garden.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.constant.JoyGardenUrl;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantDTO;
import demo.joy.garden.pojo.dto.JoyGardenCreatePlantStageDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantStageDeleteDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantStageUpdateDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantStageUpdateSortDTO;
import demo.joy.garden.pojo.dto.JoyGardenPlantSearchConditionDTO;
import demo.joy.garden.pojo.dto.ShowPlantStageMangerDTO;
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
	
	@GetMapping(value = JoyGardenUrl.PLANT_CATALOG_CREATOR)
	public ModelAndView plantCatalogCreator() {
		return plantCatalogManagerService.plantCatalogCreator();
	}
	
	@PostMapping(value = JoyGardenUrl.PLANT_CATALOG_CREATOR)
	@ResponseBody
	public JoyCommonResult plantCatalogCreator(@RequestBody JoyGardenCreatePlantDTO dto) {
		return plantCatalogManagerService.createNewPlant(dto);
	}
	
	@PostMapping(value = JoyGardenUrl.PLANT_STAGE_DELETE)
	@ResponseBody
	public JoyCommonResult deletePlantStage(@RequestBody JoyGardenPlantStageDeleteDTO dto) {
		return plantCatalogManagerService.deletePlantStage(dto);
	}
	
	@PostMapping(value = JoyGardenUrl.PLANT_STAGE_UPDATE)
	@ResponseBody
	public JoyCommonResult updatePlantStage(@RequestBody JoyGardenPlantStageUpdateDTO dto) {
		return plantCatalogManagerService.updatePlantStage(dto);
	}
	
	@PostMapping(value = JoyGardenUrl.PLANT_STAGE_SORT_UPDATE)
	@ResponseBody
	public JoyCommonResult updatePlantStage(@RequestBody JoyGardenPlantStageUpdateSortDTO dto) {
		return plantCatalogManagerService.updatePlantStageSort(dto);
	}
	
	@PostMapping(value = JoyGardenUrl.CREAT_PLANT_STAGE)
	@ResponseBody
	public JoyCommonResult createNewPlantStage(@RequestBody JoyGardenCreatePlantStageDTO dto) {
		return plantCatalogManagerService.createNewPlantStage(dto);
	}
	
	@PostMapping(value = JoyGardenUrl.PLANT_CATALOG_SEARCH)
	public ModelAndView plantSearch(@RequestBody JoyGardenPlantSearchConditionDTO dto) {
		return plantCatalogManagerService.plantSearch(dto);
	}
	
	@PostMapping(value = JoyGardenUrl.PLANT_STAGE_MANAGER)
	public ModelAndView createNewPlantStage(@RequestBody ShowPlantStageMangerDTO dto) {
		return plantCatalogManagerService.plantStageManger(dto);
	}
}
