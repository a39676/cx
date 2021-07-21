package demo.finance.cryptoCoin.data.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

public class FindNextCoinTypeMissingDataResult extends CommonResult {

	private CryptoCoinDailyDataQueryDTO dto;

	public CryptoCoinDailyDataQueryDTO getDto() {
		return dto;
	}

	public void setDto(CryptoCoinDailyDataQueryDTO dto) {
		this.dto = dto;
	}

	@Override
	public String toString() {
		return "FindNextCoinTypeMissingDataResult [dto=" + dto + ", success=" + success + "]";
	}

}
