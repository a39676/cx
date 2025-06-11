package demo.finance.cryptoCoin.data.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class CryptoCoinFilterBigMoveDataInTimeRangeResult extends CommonResult {

	GetBigMoveSummaryDataResult todayBigDataFilterResult;
	GetBigMoveSummaryDataResult yesterdayBigDataFilterResult;
	GetBigMoveSummaryDataResult lastweekBigDataFilterResult;

	public GetBigMoveSummaryDataResult getTodayBigDataFilterResult() {
		return todayBigDataFilterResult;
	}

	public void setTodayBigDataFilterResult(GetBigMoveSummaryDataResult todayBigDataFilterResult) {
		this.todayBigDataFilterResult = todayBigDataFilterResult;
	}

	public GetBigMoveSummaryDataResult getYesterdayBigDataFilterResult() {
		return yesterdayBigDataFilterResult;
	}

	public void setYesterdayBigDataFilterResult(GetBigMoveSummaryDataResult yesterdayBigDataFilterResult) {
		this.yesterdayBigDataFilterResult = yesterdayBigDataFilterResult;
	}

	public GetBigMoveSummaryDataResult getLastweekBigDataFilterResult() {
		return lastweekBigDataFilterResult;
	}

	public void setLastweekBigDataFilterResult(GetBigMoveSummaryDataResult lastweekBigDataFilterResult) {
		this.lastweekBigDataFilterResult = lastweekBigDataFilterResult;
	}

	@Override
	public String toString() {
		return "CryptoCoinHandleBigMoveDataCrossResult [todayBigDataFilterResult=" + todayBigDataFilterResult
				+ ", yesterdayBigDataFilterResult=" + yesterdayBigDataFilterResult + ", lastweekBigDataFilterResult="
				+ lastweekBigDataFilterResult + "]";
	}

}
