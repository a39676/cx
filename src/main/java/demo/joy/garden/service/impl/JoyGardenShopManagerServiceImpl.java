package demo.joy.garden.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.service.JoyCommonService;
import demo.joy.garden.mapper.JoyGardenPlantGrowingStageMapper;
import demo.joy.garden.service.JoyGardenShopManagerService;

@Service
public class JoyGardenShopManagerServiceImpl extends JoyCommonService implements JoyGardenShopManagerService {

	@Autowired
	private JoyGardenPlantGrowingStageMapper stageMapper;
	
	public ModelAndView seedSearchView() {
//		TODO
		return null;
	}
}
