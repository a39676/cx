package demo.joy.garden.pojo.dto;

public class JoyGardenShopSearchDTO {

	private Integer goodsMainType;
	private Integer goodsSubType;
	private String startPK;
	private String endPK;
	private Boolean isNextPage;

	public Integer getGoodsMainType() {
		return goodsMainType;
	}

	public void setGoodsMainType(Integer goodsMainType) {
		this.goodsMainType = goodsMainType;
	}

	public Integer getGoodsSubType() {
		return goodsSubType;
	}

	public void setGoodsSubType(Integer goodsSubType) {
		this.goodsSubType = goodsSubType;
	}

	public String getStartPK() {
		return startPK;
	}

	public void setStartPK(String startPK) {
		this.startPK = startPK;
	}

	public String getEndPK() {
		return endPK;
	}

	public void setEndPK(String endPK) {
		this.endPK = endPK;
	}

	public Boolean getIsNextPage() {
		return isNextPage;
	}

	public void setIsNextPage(Boolean isNextPage) {
		this.isNextPage = isNextPage;
	}

	@Override
	public String toString() {
		return "JoyGardenShopSearchDTO [goodsMainType=" + goodsMainType + ", goodsSubType=" + goodsSubType
				+ ", startPK=" + startPK + ", endPK=" + endPK + ", isNextPage=" + isNextPage + "]";
	}

}
