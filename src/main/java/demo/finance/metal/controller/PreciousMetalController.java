package demo.finance.metal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResultCX;
import demo.finance.metal.pojo.constant.PreciousMetalPriceUrl;
import demo.finance.metal.pojo.dto.InsertNewMetalPriceNoticeSettingDTO;
import demo.finance.metal.service.PreciousMetalService;
import precious_metal.pojo.constant.PreciousMetalPriceCommonUrl;
import precious_metal.pojo.dto.TransmissionPreciousMetalPriceDTO;

@Controller
@RequestMapping(value = PreciousMetalPriceCommonUrl.root)
public class PreciousMetalController extends CommonController {

	@Autowired
	private PreciousMetalService preciousMetalService;
	
	@PostMapping(value = PreciousMetalPriceCommonUrl.transPreciousMetalPrice)
	@ResponseBody
	public CommonResult reciveMetalPrice(@RequestBody TransmissionPreciousMetalPriceDTO dto) {
		return preciousMetalService.reciveMetalPrice(dto);
	}
	
	@PostMapping(value = PreciousMetalPriceUrl.INSERT_METAL_NOTICE_SETTING)
	@ResponseBody
	public CommonResultCX insertNewMetalPriceNoticeSetting(@RequestBody InsertNewMetalPriceNoticeSettingDTO dto) {
		return preciousMetalService.insertNewMetalPriceNoticeSetting(dto);
	}
	
	@GetMapping(value = PreciousMetalPriceUrl.INSERT_METAL_NOTICE_SETTING)
	public ModelAndView insertNewMetalPriceNoticeSetting() {
		return preciousMetalService.insertNewMetalPriceNoticeSetting();
	}
}
