package demo.joy.garden.pojo.vo;

import java.math.BigDecimal;

public class JoyGardenShopPlantVO {

	private String pk;

	private String goodsName;

	private String imgUrlPath;

	private Integer goodsCounting;

	private BigDecimal goodsPrice;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getImgUrlPath() {
		return imgUrlPath;
	}

	public void setImgUrlPath(String imgUrlPath) {
		this.imgUrlPath = imgUrlPath;
	}

	public Integer getGoodsCounting() {
		return goodsCounting;
	}

	public void setGoodsCounting(Integer goodsCounting) {
		this.goodsCounting = goodsCounting;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	@Override
	public String toString() {
		return "JoyGardenShopPlantVO [pk=" + pk + ", goodsName=" + goodsName + ", imgUrlPath=" + imgUrlPath
				+ ", goodsCounting=" + goodsCounting + ", goodsPrice=" + goodsPrice + "]";
	}

}
