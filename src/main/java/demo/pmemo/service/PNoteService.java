package demo.pmemo.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.pmemo.pojo.dto.EditPNoteDTO;

public interface PNoteService {

	CommonResult editPNote(EditPNoteDTO dto);

}
