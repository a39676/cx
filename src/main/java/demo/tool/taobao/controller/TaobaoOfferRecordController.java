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
import demo.tool.taobao.pojo.dto.TaobaoOfferStatisticsQueryDTO;
import demo.tool.taobao.pojo.result.TaobaoOfferStatisticsResult;
import demo.tool.taobao.service.TaobaoOfferRecordService;

@Controller
@RequestMapping(value = TaobaoUrlConstant.ROOT + TaobaoUrlConstant.OFFER)
public class TaobaoOfferRecordController {

	@Autowired
	private TaobaoOfferRecordService service;

	@GetMapping(value = "/")
	public ModelAndView taobaoProductSource() {
		return service.offerRecordView();
	}

	@PostMapping(value = TaobaoUrlConstant.OFFER_ADD_FROM_DOWNSTREAM_BUYER)
	@ResponseBody
	public CommonResult addNewOfferFromDownstreamBuyer(@RequestBody TaobaoAddOfferFromDownstreamBuyerDTO dto) {
		return service.addNewOfferFromDownstreamBuyer(dto);
	}

	@PostMapping(value = TaobaoUrlConstant.OFFER_ADD_TO_SUPPLIER)
	@ResponseBody
	public CommonResult addNewOfferToSupplier(@RequestBody TaobaoAddOfferToSupplierDTO dto) {
		return service.addNewOfferToSupplier(dto);
	}

	@PostMapping(value = TaobaoUrlConstant.OFFER_STATISTICS)
	@ResponseBody
	public TaobaoOfferStatisticsResult taobaoOfferStatistics(@RequestBody TaobaoOfferStatisticsQueryDTO queryDTO) {
		return service.taobaoOfferStatistics(queryDTO);
	}
}
