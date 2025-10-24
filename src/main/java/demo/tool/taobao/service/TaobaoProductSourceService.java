package demo.tool.taobao.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.AddTaobaoProductSourceDTO;

public interface TaobaoProductSourceService {

	CommonResult insert(AddTaobaoProductSourceDTO dto);

	ModelAndView search(AddTaobaoProductSourceDTO dto);

	ModelAndView taobaoProductSource();

}
