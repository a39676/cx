package demo.finance.metal.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.metal.pojo.constant.MetalConstant;
import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.pojo.po.MetalPriceExample;
import demo.finance.metal.service.PreciousMetalService;
import finance.precious_metal.pojo.dto.PreciousMetailPriceDTO;
import finance.precious_metal.pojo.type.MetalType;
import tool.pojo.type.UtilOfWeightType;

@Service
public class PreciousMetalServiceImpl extends PreciousMetalCommonService implements PreciousMetalService {

	@Override
	public CommonResult reciveMetalPrice(PreciousMetailPriceDTO dto) {
		CommonResult result = new CommonResult();

		MetalPrice tmpPO = null;
		int insertCount = 0;
		if (dto.getPrice() == null || dto.getWeightUtilType() == null) {
			return result;
		}

		UtilOfWeightType weightType = UtilOfWeightType.getType(dto.getWeightUtilType());
		if (weightType == null) {
			return result;
		}

		MetalType metalType = MetalType.getType(dto.getMetalType());
		if (metalType == null) {
			return result;
		}

		tmpPO = new MetalPrice();
		tmpPO.setId(snowFlake.getNextId());
		tmpPO.setMetalType(dto.getMetalType());
		tmpPO.setPrice(new BigDecimal(dto.getPrice()));
		tmpPO.setWeightType(dto.getWeightUtilType());
		if (StringUtils.isNotBlank(dto.getTransactionDateTime())) {
			tmpPO.setCreateTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getTransactionDateTime()));
		}
		if (tmpPO.getCreateTime() == null) {
			tmpPO.setCreateTime(LocalDateTime.now());
		}

		insertCount += metalPriceMapper.insertSelective(tmpPO);

		if (insertCount > 0) {
			result.setIsSuccess();
		}

		return result;
	}

	@Override
	public CommonResult deleteExpiredCacheData() {
		CommonResult r = new CommonResult();

		LocalDateTime expriedTime = LocalDateTime.now().minusHours(MetalConstant.METAL_PRICE_CACHE_DATA_LIVE_HOURS);

		MetalPriceExample example = new MetalPriceExample();
		example.createCriteria().andCreateTimeLessThan(expriedTime);
		metalPriceMapper.deleteByExample(example);
		r.setIsSuccess();
		return r;
	}

}
