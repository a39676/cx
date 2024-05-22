package demo.finance.cryptoCoin.data.pojo.dto;

public class GetBigMoveSummaryDataDTO {

	private Integer hourRangeStart = 0;
	private Integer hourRangeEnd = 24;
	private String symbols;

	public Integer getHourRangeStart() {
		return hourRangeStart;
	}

	public void setHourRangeStart(Integer hourRangeStart) {
		this.hourRangeStart = hourRangeStart;
	}

	public Integer getHourRangeEnd() {
		return hourRangeEnd;
	}

	public void setHourRangeEnd(Integer hourRangeEnd) {
		this.hourRangeEnd = hourRangeEnd;
	}

	public String getSymbols() {
		return symbols;
	}

	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}

	@Override
	public String toString() {
		return "GetBigMoveSummaryDataDTO [hourRangeStart=" + hourRangeStart + ", hourRangeEnd=" + hourRangeEnd
				+ ", symbols=" + symbols + "]";
	}

}
