package demo.pmemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.pmemo.pojo.constant.PUrgeNoteUrl;
import demo.pmemo.pojo.dto.EditPNoteDTO;
import demo.pmemo.service.PUrgeNoteService;

@Controller
@RequestMapping(value = PUrgeNoteUrl.ROOT)
public class PUrgeNoteController extends CommonController {

	@Autowired
	private PUrgeNoteService urgeNoteService;
	
	@PostMapping(value = PUrgeNoteUrl.EDIT)
	@ResponseBody
	public CommonResult edit(@RequestBody EditPNoteDTO dto) {
		return urgeNoteService.editPNote(dto);
	}
	
	@GetMapping(value = PUrgeNoteUrl.EDIT)
	public ModelAndView edit() {
		return urgeNoteService.readNote();
	}
	
}
