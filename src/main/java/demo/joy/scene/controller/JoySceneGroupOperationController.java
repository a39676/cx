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
import demo.joy.scene.pojo.constant.JoySceneGroupOperationUrl;
import demo.joy.scene.pojo.dto.EditJoySceneGroupDTO;
import demo.joy.scene.pojo.dto.FindSceneGroupByConditionDTO;
import demo.joy.scene.pojo.result.FindSceneGroupVOResult;
import demo.joy.scene.service.JoySceneGroupOperationService;

@Controller
@RequestMapping(value = JoyAdminUrl.ROOT + JoySceneGroupOperationUrl.ROOT)
public class JoySceneGroupOperationController extends JoyCommonController {

	@Autowired
	private JoySceneGroupOperationService secneGroupService;
	
	@PostMapping(value = JoySceneGroupOperationUrl.CREATE)
	@ResponseBody
	public JoyCommonResult create(@RequestBody EditJoySceneGroupDTO dto) {
		return secneGroupService.createJoySceneGroup(dto);
	}
	
	@PostMapping(value = JoySceneGroupOperationUrl.DELETE)
	@ResponseBody
	public JoyCommonResult delete(@RequestBody EditJoySceneGroupDTO dto) {
		return secneGroupService.deleteJoySceneGroup(dto);
	}
	
	@PostMapping(value = JoySceneGroupOperationUrl.FIND_BY_CONDITION)
	@ResponseBody
	public FindSceneGroupVOResult findSceneGroupVOListByCondition(@RequestBody FindSceneGroupByConditionDTO dto) {
		return secneGroupService.findSceneGroupVOListByCondition(dto);
	}
}
