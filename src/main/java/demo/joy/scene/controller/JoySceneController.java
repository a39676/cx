package demo.joy.scene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.scene.pojo.constant.JoySceneUrl;
import demo.joy.scene.pojo.dto.FindSceneVOListBySceneGroupPKDTO;
import demo.joy.scene.pojo.result.FindSceneVOListResult;
import demo.joy.scene.service.JoySceneService;

@Controller
@RequestMapping(value = JoyUrl.ROOT + JoySceneUrl.ROOT)
public class JoySceneController extends JoyCommonController {

	@Autowired
	private JoySceneService sceneService;
	
	@PostMapping(value = JoySceneUrl.FIND_SCENE_VO_LIST_BY_SCENE_GROUP_PK)
	@ResponseBody
	public FindSceneVOListResult findSceneVOListBySceneGroupPK(@RequestBody FindSceneVOListBySceneGroupPKDTO dto) {
		return sceneService.findSceneVOListBySceneGroupPK(dto);
	}
}
