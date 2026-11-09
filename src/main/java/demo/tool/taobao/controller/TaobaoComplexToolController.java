package demo.tool.taobao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.constant.TaobaoUrlConstant;
import demo.tool.taobao.service.TaobaoComplexService;

@RestController
@RequestMapping(value = TaobaoUrlConstant.ROOT + TaobaoUrlConstant.COMPLEX_TOOL)
public class TaobaoComplexToolController {

	@Autowired
	private TaobaoComplexService service;

	@GetMapping(value = TaobaoUrlConstant.GET_SELLING_SOURCE_PRODUCT_ID)
	public CommonResult priceCalculate() {
		return service.getSellingSourceProductId();
	}

}
