package demo.pmemo.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.pmemo.pojo.dto.EditPNoteDTO;

public interface PNoteService {

	CommonResult editPNote(EditPNoteDTO dto);

	ModelAndView readNote();


}
