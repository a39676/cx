package demo.joy.garden.pojo.vo;

import java.math.BigDecimal;

public class JoyGardenShopGoodsVO {

	private String pk;
	private String goodsName;
	private BigDecimal price;
	private Integer counting;
	private String imgUrlPath;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCounting() {
		return counting;
	}

	public void setCounting(Integer counting) {
		this.counting = counting;
	}

	public String getImgUrlPath() {
		return imgUrlPath;
	}

	public void setImgUrlPath(String imgUrlPath) {
		this.imgUrlPath = imgUrlPath;
	}

	@Override
	public String toString() {
		return "JoyGardenShopGoodsVO [pk=" + pk + ", goodsName=" + goodsName + ", price=" + price + ", counting="
				+ counting + ", imgUrlPath=" + imgUrlPath + "]";
	}

}
