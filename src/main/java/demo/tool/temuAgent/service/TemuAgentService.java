package demo.tool.temuAgent.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.temuAgent.pojo.dto.TemuAgentCeateProductDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentProductModelDetailSearchDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentSearchProductDTO;

public interface TemuAgentService {

	ModelAndView home();

	CommonResult createProduct(TemuAgentCeateProductDTO dto);

	ModelAndView searchProductList(TemuAgentSearchProductDTO dto);

	ModelAndView searchProductModelDetail(TemuAgentProductModelDetailSearchDTO dto);


}
