package demo.tool.taobao.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferFromDownstreamBuyerDTO;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferToSupplierDTO;

public interface TaobaoOfferFromDownstreamBuyerRecordService {

	ModelAndView offerRecordView();

	CommonResult addNewOfferFromDownstreamBuyer(TaobaoAddOfferFromDownstreamBuyerDTO dto);

	CommonResult addNewOfferToSupplier(TaobaoAddOfferToSupplierDTO dto);

}
