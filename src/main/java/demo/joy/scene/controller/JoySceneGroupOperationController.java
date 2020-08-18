package demo.joy.scene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.scene.pojo.constant.JoySceneGroupOperationUrl;
import demo.joy.scene.pojo.dto.CreateJoySceneGroupDTO;
import demo.joy.scene.service.JoySceneGroupService;

@Controller
@RequestMapping(value = JoySceneGroupOperationUrl.ROOT)
public class JoySceneGroupOperationController extends JoyCommonController {

	@Autowired
	private JoySceneGroupService secneGroupService;
	
	@PostMapping(value = JoySceneGroupOperationUrl.CREATE)
	@ResponseBody
	public JoyCommonResult create(@RequestBody CreateJoySceneGroupDTO dto) {
		return secneGroupService.createJoySceneGroup(dto);
	}
	
	@PostMapping(value = JoySceneGroupOperationUrl.DELETE)
	@ResponseBody
	public JoyCommonResult delete(@RequestBody String sceneGroupPK) {
		return secneGroupService.deleteJoySceneGroup(sceneGroupPK);
	}
}
