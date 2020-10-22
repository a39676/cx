package demo.finance.metal.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import finance.precious_metal.pojo.dto.PreciousMetailPriceDTO;

public interface PreciousMetalService {

	CommonResult reciveMetalPrice(PreciousMetailPriceDTO dto);

	CommonResultCX deleteExpiredCacheData();

}
