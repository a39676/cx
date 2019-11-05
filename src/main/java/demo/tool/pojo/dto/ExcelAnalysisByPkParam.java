package demo.tool.pojo.dto;

public class ExcelAnalysisByPkParam {

	private Integer chartType;

	private String pk;

	/**
	 * 需要行列转换
	 */
	private boolean needColumnToRow = false;


	public Integer getChartType() {
		return chartType;
	}

	public void setChartType(Integer chartType) {
		this.chartType = chartType;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public boolean getNeedColumnToRow() {
		return needColumnToRow;
	}

	public void setNeedColumnToRow(boolean needColumnToRow) {
		this.needColumnToRow = needColumnToRow;
	}

	@Override
	public String toString() {
		return "ExcelAnalysisByPkParam [chartType=" + chartType + ", pk=" + pk + ", needColumnToRow=" + needColumnToRow
				+ "]";
	}

}
