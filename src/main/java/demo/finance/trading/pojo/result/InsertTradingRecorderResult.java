package demo.finance.trading.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class InsertTradingRecorderResult extends CommonResult {

	private Long newTradingId;

	public Long getNewTradingId() {
		return newTradingId;
	}

	public void setNewTradingId(Long newTradingId) {
		this.newTradingId = newTradingId;
	}

	@Override
	public String toString() {
		return "InsertTradingRecorderResult [newTradingId=" + newTradingId + "]";
	}

}
