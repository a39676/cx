package demo.finance.metal.service;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.precious_metal.pojo.dto.PreciousMetailPriceDTO;

public interface PreciousMetalService {

	CommonResult reciveMetalPrice(PreciousMetailPriceDTO dto);

	CommonResult deleteExpiredCacheData();

}
