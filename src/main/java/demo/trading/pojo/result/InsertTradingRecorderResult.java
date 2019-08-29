package demo.trading.pojo.result;

import demo.baseCommon.pojo.result.CommonResult;

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
