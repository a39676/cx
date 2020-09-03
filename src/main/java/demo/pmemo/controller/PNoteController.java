package demo.pmemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.controller.CommonController;
import demo.pmemo.pojo.constant.PNoteUrl;
import demo.pmemo.pojo.dto.EditPNoteDTO;
import demo.pmemo.service.PNoteService;

@Controller
@RequestMapping(value = PNoteUrl.ROOT)
public class PNoteController extends CommonController {

	@Autowired
	private PNoteService pNoteService;
	
	@GetMapping(value = PNoteUrl.EDIT)
	@ResponseBody
	public CommonResult edit(@RequestBody EditPNoteDTO dto) {
		return pNoteService.editPNote(dto);
	}
	
}
