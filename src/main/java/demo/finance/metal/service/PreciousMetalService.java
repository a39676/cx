package demo.finance.metal.service;

import auxiliaryCommon.pojo.result.CommonResult;
import precious_metal.pojo.dto.TransPreciousMetalPriceDTO;

public interface PreciousMetalService {

	CommonResult reciveMetalPrice(TransPreciousMetalPriceDTO dto);

}
