package demo.finance.metal.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.baseCommon.service.CommonService;
import demo.finance.metal.mapper.MetalPriceMapper;
import demo.finance.metal.pojo.po.MetalPrice;
import demo.finance.metal.service.PreciousMetalService;
import precious_metal.pojo.dto.PreciousMetailPriceDTO;
import precious_metal.pojo.dto.TransPreciousMetalPriceDTO;
import precious_metal.pojo.type.MetalType;
import tool.pojo.type.UtilOfWeightType;

@Service
public class PreciousMetalServiceImpl extends CommonService implements PreciousMetalService {

	@Autowired
	private MetalPriceMapper metalPriceMapper;
	
	@Override
	public CommonResult reciveMetalPrice(TransPreciousMetalPriceDTO dto) {
		CommonResult result = new CommonResult();
		List<PreciousMetailPriceDTO> list = dto.getPriceList();
		
		MetalPrice tmpPO = null;
		int insertCount = 0;
		for(PreciousMetailPriceDTO d : list) {
			if(d.getPrice() == null || d.getWeightUtilType() == null) {
				continue;
			}
			
			UtilOfWeightType weightType = UtilOfWeightType.getType(d.getWeightUtilType());
			if(weightType == null) {
				continue;
			}
			
			MetalType metalType = MetalType.getType(d.getMetalType());
			if(metalType == null) {
				continue;
			}
			
			tmpPO = new MetalPrice();
			tmpPO.setId(snowFlake.getNextId());
			tmpPO.setMetalType(d.getMetalType());
			tmpPO.setPrice(new BigDecimal(d.getPrice()));
			tmpPO.setWeightType(d.getWeightUtilType());
			tmpPO.setCreateTime(LocalDateTime.now());
			
			insertCount += metalPriceMapper.insertSelective(tmpPO);
		}
		
		if(insertCount > 0) {
			result.setIsSuccess();
		}
		
		return result;
	}
}
