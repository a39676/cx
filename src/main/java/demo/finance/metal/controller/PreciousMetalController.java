package demo.finance.metal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.controller.CommonController;
import demo.finance.metal.service.PreciousMetalService;
import precious_metal.pojo.constant.PreciousMetalPriceUrl;
import precious_metal.pojo.dto.TransPreciousMetalPriceDTO;

@Controller
@RequestMapping(value = PreciousMetalPriceUrl.root)
public class PreciousMetalController extends CommonController {

	@Autowired
	private PreciousMetalService preciousMetalService;
	
	@PostMapping(value = PreciousMetalPriceUrl.transPreciousMetalPrice)
	@ResponseBody
	public CommonResult reciveMetalPrice(@RequestBody TransPreciousMetalPriceDTO dto) {
		return preciousMetalService.reciveMetalPrice(dto);
	}
	
}
