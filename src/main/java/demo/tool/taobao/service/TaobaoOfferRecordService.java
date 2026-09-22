package demo.tool.taobao.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferFromDownstreamBuyerDTO;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferToSupplierDTO;
import demo.tool.taobao.pojo.dto.TaobaoOfferStatisticsQueryDTO;
import demo.tool.taobao.pojo.result.TaobaoAddOfferFromDownstreamBuyerResult;
import demo.tool.taobao.pojo.result.TaobaoOfferStatisticsResult;

public interface TaobaoOfferRecordService {

	ModelAndView offerRecordView();

	TaobaoAddOfferFromDownstreamBuyerResult addNewOfferFromDownstreamBuyer(TaobaoAddOfferFromDownstreamBuyerDTO dto);

	CommonResult addNewOfferToSupplier(TaobaoAddOfferToSupplierDTO dto);

	TaobaoOfferStatisticsResult taobaoOfferStatistics(TaobaoOfferStatisticsQueryDTO dto);

}
