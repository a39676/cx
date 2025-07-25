package demo.tool.temuAgent.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.temuAgent.pojo.dto.TemuAgentCeateProductDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentProductSearchDTO;

public interface TemuAgentService {

	ModelAndView home();

	CommonResult createProduct(TemuAgentCeateProductDTO dto);

	ModelAndView searchProductList(TemuAgentProductSearchDTO dto);

}
