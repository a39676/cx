package demo.tool.wodian.controller;

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
import demo.tool.wodian.pojo.dto.CreateWodianContractDTO;
import demo.tool.wodian.pojo.dto.WodianContractSerachConditionDTO;
import demo.tool.wodian.pojo.result.WodianContractListResult;
import demo.tool.wodian.service.WodianService;

@Controller
@RequestMapping(value = "/wodian")
public class WodianController extends CommonController {

	@Autowired
	private WodianService wodianService;

	@GetMapping(value = "/summary")
	public ModelAndView summary() {
		return wodianService.summaryView();
	}

	@PostMapping(value = "/getContractListByCondition")
	@ResponseBody
	public WodianContractListResult getContractListByCondition(@RequestBody WodianContractSerachConditionDTO dto) {
		return wodianService.getContractListByCondition(dto);
	}

	@PostMapping(value = "/createContract")
	@ResponseBody
	public CommonResult createContract(@RequestBody CreateWodianContractDTO dto) {
		return wodianService.createContract(dto);
	}
}
