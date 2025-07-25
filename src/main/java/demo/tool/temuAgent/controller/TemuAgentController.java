package demo.tool.temuAgent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.tool.temuAgent.pojo.constant.TemuAgentUrl;
import demo.tool.temuAgent.pojo.dto.TemuAgentCeateProductDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentProductModelDetailSearchDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentSearchProductDTO;
import demo.tool.temuAgent.service.TemuAgentService;

@Controller
@RequestMapping(value = TemuAgentUrl.ROOT)
public class TemuAgentController extends CommonController {

	@Autowired
	private TemuAgentService temuAgentService;

	@GetMapping(value = "/")
	public ModelAndView home() {
		return temuAgentService.home();
	}

	@PostMapping(value = TemuAgentUrl.CREATE_PRODUCT)
	public CommonResult createProduct(@RequestBody TemuAgentCeateProductDTO dto) {
		return temuAgentService.createProduct(dto);
	}

	@PostMapping(value = TemuAgentUrl.SEARCH_PRODUCT)
	public ModelAndView searchProductList(@RequestBody TemuAgentSearchProductDTO dto) {
		return temuAgentService.searchProductList(dto);
	}

	@PostMapping(value = TemuAgentUrl.SEARCH_PRODUCT_MODEL)
	public ModelAndView searchProductModelList(@RequestBody TemuAgentProductModelDetailSearchDTO dto) {
		return temuAgentService.searchProductModelDetail(dto);
	}
}
