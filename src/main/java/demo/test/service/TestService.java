package demo.test.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.finance.metal.service.PreciousMetal5MinuteDataSummaryService;
import demo.finance.metal.service.PreciousMetalService;
import precious_metal.pojo.dto.PreciousMetailPriceDTO;
import precious_metal.pojo.type.MetalType;
import tool.pojo.type.UtilOfWeightType;

@Service
public class TestService extends CommonService {

	
	@Autowired
	private PreciousMetal5MinuteDataSummaryService dataSummaryService;
	@Autowired
	private PreciousMetalService preciousMetalService;
	
	public String cacheDataTo5Minute() {
		
		PreciousMetailPriceDTO dto = null;
		LocalDateTime now = LocalDateTime.now();
		for(int i = 0; i < 12; i++) {
			dto = new PreciousMetailPriceDTO();
			dto.setMetalType(MetalType.gold.getCode());
			dto.setWeightUtilType(UtilOfWeightType.kiloGram.getCode());
			dto.setPrice(i * 3.3D);
			dto.setTransactionDateTime(localDateTimeHandler.dateToStr(now.minusMinutes(10).plusMinutes(i)));
			preciousMetalService.reciveMetalPrice(dto);
		}
		
		dataSummaryService.cacheDataTo5Minute();
		return "done";
	}
}
