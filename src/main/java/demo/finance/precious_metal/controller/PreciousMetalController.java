package demo.finance.precious_metal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.controller.CommonController;
import demo.finance.precious_metal.service.PreciousMetalService;
import precious_metal.pojo.constant.PreciousMetalPriceUrl;
import precious_metal.pojo.dto.TransPreciousMetalPriceDTO;

@RequestMapping(value = PreciousMetalPriceUrl.root)
public class PreciousMetalController extends CommonController {

	@Autowired
	private PreciousMetalService preciousMetalService;
	
	@PostMapping(value = PreciousMetalPriceUrl.transPerciousMetalPrice)
	@ResponseBody
	public CommonResult reciveMetalPrice(@RequestBody TransPreciousMetalPriceDTO dto) {
		return preciousMetalService.reciveMetalPrice(dto);
	}
	
}
