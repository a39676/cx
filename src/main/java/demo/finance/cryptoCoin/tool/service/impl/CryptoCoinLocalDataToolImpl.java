package demo.finance.cryptoCoin.tool.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinMABO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinMAResult;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoCoinLocalDataFinderResult;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLocalDataTool;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Service
public class CryptoCoinLocalDataToolImpl extends CryptoCoinCommonService implements CryptoCoinLocalDataTool {

	@Autowired
	private CryptoCoinPrice1dayMapper _1DayDataMapper;
	@Autowired
	private CryptoCoin1DayDataSummaryService _1DayDataService;
	@Autowired
	private CryptoCoinCatalogMapper catalogMapper;
	
	@Override
	public CryptoCoinLocalDataFinderResult finder1() {
		LocalDateTime startDatetime = LocalDateTime.of(2021, 1, 1, 0, 0);
		
		List<CryptoCoinCatalog> catalogList = filterByStartDatetime(startDatetime);
		
		List<CryptoCoinCatalog> resultList = new ArrayList<>();
		
		for(CryptoCoinCatalog catalog : catalogList) {
			if(judgment1(catalog, startDatetime)) {
				resultList.add(catalog);
			}
		}
		
		CryptoCoinLocalDataFinderResult r = new CryptoCoinLocalDataFinderResult();
		r.setCatalogList(resultList);
		r.setIsSuccess();
		return r;
	}
	
	private List<CryptoCoinCatalog> filterByStartDatetime(LocalDateTime startDatetime) {
		CryptoCoinPrice1dayExample example = new CryptoCoinPrice1dayExample();
		example.createCriteria().andStartTimeEqualTo(startDatetime);
		List<CryptoCoinPrice1day> startDataList = _1DayDataMapper.selectByExample(example);
		
		List<Long> catalogIDList = startDataList.stream().map(po -> po.getCoinType()).collect(Collectors.toList());
		
		CryptoCoinCatalogExample catalogExample = new CryptoCoinCatalogExample();
		catalogExample.createCriteria().andIdIn(catalogIDList);
		return catalogMapper.selectByExample(catalogExample);
	}

	private boolean judgment1(CryptoCoinCatalog catalog, LocalDateTime startDatetime) {
		List<CryptoCoinPriceCommonDataBO> dataList = _1DayDataService.getCommonDataList(catalog, CurrencyType.USD, startDatetime);
		FilterBODataResult maxMinPriceResult = filterData(dataList);
		
		if (maxMinPriceResult.isFail()) {
			return false;
		}

		try {
			if(maxMinPriceResult.getMaxPriceDateTime().isBefore(maxMinPriceResult.getMinPriceDateTime())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		
		double lastMax = maxMinPriceResult.getMaxPrice().doubleValue();
		double lastMin = maxMinPriceResult.getMinPrice().doubleValue();

		Double upApmlitude = (lastMax / lastMin - 1) * 100;
//		Double lowApmlitude = (lastMin / lastMax - 1) * 100;
		if(upApmlitude < 200) {
			return false;
		}
		
		CryptoCoinPriceCommonDataBO lastData = dataList.get(dataList.size() - 1);
		Double lastFallRate = lastData.getEndPrice().doubleValue() / maxMinPriceResult.getMaxPrice().doubleValue();
		if(lastFallRate > 0.6) {
			return false;
		}
		
		Integer[] maSizeList = new Integer[] {5, 10, 30, 60};
		CryptoCoinMAResult maList = getMAList(dataList, maSizeList);
		for(CryptoCoinMABO ma : maList.getMaList()) {
			if(ma.getMaSize() == 30) {
				if(ma.getMaValue() != null && ma.getMaValue() < lastData.getEndPrice().doubleValue()) {
					return false;
				}
			}
			
			if(ma.getMaSize() == 5) {
				if(ma.getMaValue() != null && ma.getMaValue() > lastData.getEndPrice().doubleValue()) {
					return false;
				}
			}
			
		}
		
		return true;
	}
	
}
