package demo.joy.scene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyUrl;
import demo.joy.scene.pojo.constant.JoySceneOperationUrl;
import demo.joy.scene.pojo.constant.JoySceneUrl;
import demo.joy.scene.pojo.dto.JoySceneOperationDTO;
import demo.joy.scene.service.JoySceneOperationService;

@Controller
@RequestMapping(value = JoyUrl.ROOT + JoySceneUrl.ROOT + JoySceneOperationUrl.ROOT)
public class JoySceneOperationController extends JoyCommonController {

	@Autowired
	private JoySceneOperationService joySceneOperationService;
	
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
}
