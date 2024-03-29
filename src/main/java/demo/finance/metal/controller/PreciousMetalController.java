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
import demo.common.controller.CommonController;
import demo.finance.metal.pojo.constant.PreciousMetalPriceUrl;
import demo.finance.metal.pojo.dto.InsertNewMetalPriceNoticeSettingDTO;
import demo.finance.metal.service.PreciousMetalNoticeService;
import finance.precious_metal.pojo.constant.PreciousMetalPriceCommonUrl;

@Controller
@RequestMapping(value = PreciousMetalPriceCommonUrl.root)
public class PreciousMetalController extends CommonController {

	@Autowired
	private PreciousMetalNoticeService preciousMetalNoticeService;

	@PostMapping(value = PreciousMetalPriceUrl.INSERT_METAL_NOTICE_SETTING)
	@ResponseBody
	public CommonResult insertNewMetalPriceNoticeSetting(@RequestBody InsertNewMetalPriceNoticeSettingDTO dto) {
		return preciousMetalNoticeService.insertNewMetalPriceNoticeSetting(dto);
	}

	@GetMapping(value = PreciousMetalPriceUrl.INSERT_METAL_NOTICE_SETTING)
	public ModelAndView insertNewMetalPriceNoticeSetting() {
		return preciousMetalNoticeService.insertNewMetalPriceNoticeSetting();
	}
}
