package demo.joy.start.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.character.pojo.result.GetCharacterDetailResult;
import demo.joy.character.service.JoyCharacterService;
import demo.joy.common.service.JoyCommonService;
import demo.joy.scene.pojo.result.FindSceneGroupVOResult;
import demo.joy.scene.service.JoySceneGroupService;
import demo.joy.start.pojo.constant.JoyStartView;
import demo.joy.start.service.JoyStartService;

@Service
public class JoyStartServiceImpl extends JoyCommonService implements JoyStartService {
	
	@Autowired
	private JoyCharacterService joyCharacterService;
	@Autowired
	private JoySceneGroupService joySceneGroupService;

	@Override
	public ModelAndView getStartView() {
		ModelAndView view = new ModelAndView(JoyStartView.START);
		
		GetCharacterDetailResult characterDetailVOResult = joyCharacterService.getCharacterDetail();
		view.addObject("characterDetailVO", characterDetailVOResult.getCharacterVO());
		
		FindSceneGroupVOResult findSceneGroupResult = joySceneGroupService.findAllSceneGroupVOList();
		view.addObject("sceneGroupVOList", findSceneGroupResult.getSceneGroupVOList());
		
		return view;
	}
}
