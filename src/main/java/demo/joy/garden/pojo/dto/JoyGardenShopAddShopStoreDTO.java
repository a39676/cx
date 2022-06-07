package demo.joy.garden.pojo.dto;

import java.math.BigDecimal;

import demo.joy.garden.pojo.type.JoyGardenShopGoodsMainType;

public class JoyGardenShopAddShopStoreDTO {

	/** {@link JoyGardenShopGoodsMainType} */
	private Integer goodsMainType;

	private Integer goodsSubType;

	private String goodsPK;

	private Integer addCounting;

	private BigDecimal price;

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

	public String getGoodsPK() {
		return goodsPK;
	}

	public void setGoodsPK(String goodsPK) {
		this.goodsPK = goodsPK;
	}

	public Integer getAddCounting() {
		return addCounting;
	}

	public void setAddCounting(Integer addCounting) {
		this.addCounting = addCounting;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "JoyGardenShopAddShopStoreDTO [goodsMainType=" + goodsMainType + ", goodsSubType=" + goodsSubType
				+ ", goodsPK=" + goodsPK + ", addCounting=" + addCounting + ", price=" + price + "]";
	}

}
