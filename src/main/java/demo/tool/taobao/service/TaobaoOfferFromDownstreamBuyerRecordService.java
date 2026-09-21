package demo.tool.taobao.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferFromDownstreamBuyerDTO;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferToSupplierDTO;
import demo.tool.taobao.pojo.result.TaobaoAddOfferFromDownstreamBuyerResult;

public interface TaobaoOfferFromDownstreamBuyerRecordService {

	ModelAndView offerRecordView();

	TaobaoAddOfferFromDownstreamBuyerResult addNewOfferFromDownstreamBuyer(TaobaoAddOfferFromDownstreamBuyerDTO dto);

	CommonResult addNewOfferToSupplier(TaobaoAddOfferToSupplierDTO dto);

}
