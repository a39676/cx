package demo.finance.cryptoCoin.data.pojo.result;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;

public class CryptoCoinNoticeDTOCheckResult extends CommonResult {

	private LocalDateTime validTime;

	public LocalDateTime getValidTime() {
		return validTime;
	}

	public void setValidTime(LocalDateTime validTime) {
		this.validTime = validTime;
	}

	@Override
	public String toString() {
		return "CryptoCoinNoticeDTOCheckResult [validTime=" + validTime + "]";
	}

}
