package demo.joy.garden.service;

import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.garden.pojo.dto.CreateNewGardenDTO;
import demo.joy.garden.pojo.result.JoyGardenCreateNewFieldLandResult;

public interface JoyGradenInfoService {

	ModelAndView visitOtherGarden(String userPK);

	ModelAndView index();

	JoyCommonResult createNewGarden(CreateNewGardenDTO dto);

	JoyGardenCreateNewFieldLandResult createNewFieldLand();

	ModelAndView getFieldLandView();

}
