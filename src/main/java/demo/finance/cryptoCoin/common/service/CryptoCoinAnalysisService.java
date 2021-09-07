package demo.finance.cryptoCoin.common.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinMABO;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinMAResult;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

public abstract class CryptoCoinAnalysisService extends CryptoCoinCommonService {


	protected BigDecimal numberSetScale(BigDecimal num) {
		if(num.compareTo(new BigDecimal(100)) >= 0) {
			return num.setScale(2, RoundingMode.HALF_UP);
		} else {
			return num.setScale(6, RoundingMode.HALF_UP);
		}
	}
	
	protected CryptoCoinMAResult getDefaultMAList(List<CryptoCoinPriceCommonDataBO> dataList) {
		return getMAList(dataList, 5, 10, 30, 60);
	}
	
	protected CryptoCoinMAResult getMAList(List<CryptoCoinPriceCommonDataBO> dataList, Integer... maSize) {
		CryptoCoinMAResult r = new CryptoCoinMAResult();
		
		CryptoCoinMABO bo = null;
		for(Integer ms : maSize) {
			bo = getMA(dataList, ms);
			r.addMA(bo);
		}
		Collections.sort(r.getMaList());
		return r;
	}
	
	protected CryptoCoinMABO getMA(List<CryptoCoinPriceCommonDataBO> dataList, Integer maSize) {
		CryptoCoinMABO r = new CryptoCoinMABO();
		r.setMaSize(maSize);
		if(dataList.size() < maSize) {
			return r;
		}

		BigDecimal sum = BigDecimal.ZERO;
		for(int i = dataList.size() - 1; i > (dataList.size() - maSize - 1); i--) {
			sum = sum.add(dataList.get(i).getEndPrice());
		}
		
		sum = numberSetScale(sum);
		r.setMaValue(sum.doubleValue() / maSize);
		
		return r;
	}
}
