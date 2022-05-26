package demo.joy.character.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.joy.character.pojo.constant.JoyCharacterUrl;
import demo.joy.character.pojo.dto.CreateJoyCharacterCharacterDTO;
import demo.joy.character.pojo.result.GetCharacterDetailResult;
import demo.joy.character.service.JoyCharacterService;
import demo.joy.common.controller.JoyCommonController;
import demo.joy.common.pojo.constant.JoyUrl;

@Controller
@RequestMapping(value = JoyUrl.ROOT + JoyCharacterUrl.ROOT)
public class JoyCharacterController extends JoyCommonController {

	@Autowired
	private JoyCharacterService joyCharacterService;
	
	@PostMapping(value = JoyCharacterUrl.CREATE_CHARACTER)
	@ResponseBody
	public CommonResult createJoyCharacterCharacter(@RequestBody CreateJoyCharacterCharacterDTO dto) {
		return joyCharacterService.createJoyCharacterCharacter(dto);
	}
	
	@PostMapping(value = JoyCharacterUrl.GET_CHARACTER_DETAIL)
	@ResponseBody
	public GetCharacterDetailResult createJoyCharacterCharacter() {
		return joyCharacterService.getCharacterDetail();
	}
}
