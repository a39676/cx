package demo.tool.taobao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.constant.TaobaoUrlConstant;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferFromDownstreamBuyerDTO;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferToSupplierDTO;
import demo.tool.taobao.service.TaobaoOfferFromDownstreamBuyerRecordService;

@Controller
@RequestMapping(value = TaobaoUrlConstant.ROOT + TaobaoUrlConstant.OFFER)
public class TaobaoOfferRecordController {

	@Autowired
	private TaobaoOfferFromDownstreamBuyerRecordService service;

	@GetMapping(value = "/")
	public ModelAndView taobaoProductSource() {
		return service.offerRecordView();
	}

	@PostMapping(value = TaobaoUrlConstant.OFFER_ADD_FROM_DOWNSTREAM_BUYER)
	@ResponseBody
	public CommonResult priceCalculate(@RequestBody TaobaoAddOfferFromDownstreamBuyerDTO dto) {
		return service.addNewOfferFromDownstreamBuyer(dto);
	}

	@PostMapping(value = TaobaoUrlConstant.OFFER_ADD_TO_SUPPLIER)
	@ResponseBody
	public CommonResult priceCalculate(@RequestBody TaobaoAddOfferToSupplierDTO dto) {
		return service.addNewOfferToSupplier(dto);
	}
}
