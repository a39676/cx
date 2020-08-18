package demo.joy.scene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyAdminUrl;
import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.scene.pojo.constant.JoySceneGroupRelationOperationUrl;
import demo.joy.scene.pojo.dto.EditJoySceneGroupRelationDTO;
import demo.joy.scene.service.JoySceneGroupRelationOperationService;

@Controller
@RequestMapping(value = JoyAdminUrl.ROOT + JoySceneGroupRelationOperationUrl.ROOT)
public class JoySceneGroupRelationOperationController extends JoyCommonController {

	@Autowired
	private JoySceneGroupRelationOperationService secneGroupService;
	
	@PostMapping(value = JoySceneGroupRelationOperationUrl.CREATE)
	@ResponseBody
	public JoyCommonResult create(@RequestBody EditJoySceneGroupRelationDTO dto) {
		return secneGroupService.createSceneGroupRelation(dto);
	}
	
	@PostMapping(value = JoySceneGroupRelationOperationUrl.DELETE)
	@ResponseBody
	public JoyCommonResult delete(@RequestBody EditJoySceneGroupRelationDTO dto) {
		return secneGroupService.deleteSceneGroupRelation(dto);
	}
}
