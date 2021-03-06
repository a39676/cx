package demo.joy.scene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyManagerUrl;
import demo.joy.scene.pojo.constant.JoySceneOperationUrl;
import demo.joy.scene.pojo.dto.FindSceneByConditionDTO;
import demo.joy.scene.pojo.dto.JoySceneOperationDTO;
import demo.joy.scene.pojo.result.FindSceneVOListResult;
import demo.joy.scene.service.JoySceneManagerService;

@Controller
@RequestMapping(value = JoyManagerUrl.ROOT + JoySceneOperationUrl.ROOT)
public class JoySceneManagerController extends JoyCommonController {

	@Autowired
	private JoySceneManagerService joySceneOperationService;
	
	@PostMapping(value = JoySceneOperationUrl.CREATE)
	@ResponseBody
	public CommonResult create(@RequestBody JoySceneOperationDTO dto) {
		return joySceneOperationService.createScene(dto);
	}
	
	@PostMapping(value = JoySceneOperationUrl.EDIT)
	@ResponseBody
	public CommonResult editScene(@RequestBody JoySceneOperationDTO dto) {
		return joySceneOperationService.editScene(dto);
	}
	
	@PostMapping(value = JoySceneOperationUrl.DELETE)
	@ResponseBody
	public CommonResult deleteScene(@RequestBody JoySceneOperationDTO dto) {
		return joySceneOperationService.deleteScene(dto);
	}
	
	@PostMapping(value = JoySceneOperationUrl.RESTORE)
	@ResponseBody
	public CommonResult restoreScene(@RequestBody JoySceneOperationDTO dto) {
		return joySceneOperationService.restoreScene(dto);
	}
	
	@PostMapping(value = JoySceneOperationUrl.FIND_BY_CONDITION)
	@ResponseBody
	public FindSceneVOListResult findSceneByCondition(@RequestBody FindSceneByConditionDTO dto) {
		return joySceneOperationService.findSceneByCondition(dto);
	}
}
