package demo.joy.garden.pojo.dto;

public class FindShopGoodsPageDTO {

	private Integer goodsMainType;
	private Integer goodsSubType;
	private Long objectId;
	private Long startId;
	private Long endId;
	private Integer pageSize;

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

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Long getStartId() {
		return startId;
	}

	public void setStartId(Long startId) {
		this.startId = startId;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "FindShopGoodsPageDTO [goodsMainType=" + goodsMainType + ", goodsSubType=" + goodsSubType + ", objectId="
				+ objectId + ", startId=" + startId + ", endId=" + endId + ", pageSize=" + pageSize + "]";
	}

}
