package demo.pmemo.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.pmemo.pojo.dto.SetPMemoDTO;

public interface PMemoService {

	String getMemo(String key);

	CommonResult setMemo(SetPMemoDTO dto);

	ModelAndView setMemoPage();

}
