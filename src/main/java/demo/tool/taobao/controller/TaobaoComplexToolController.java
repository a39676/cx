package demo.tool.taobao.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.tool.taobao.pojo.constant.TaobaoUrlConstant;

@RestController
@RequestMapping(value = TaobaoUrlConstant.ROOT + TaobaoUrlConstant.COMPLEX_TOOL)
public class TaobaoComplexToolController {

//	@Autowired
//	private TaobaoComplexService service;

//	@ResponseBody
//	public CommonResult priceCalculate(@RequestBody TaobaoSkuMonitorMsgDTO dto) {
//		return service.forwardSkuMonitorMsg(dto);
//	}

}
