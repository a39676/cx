package demo.tool.taobao.pojo.vo;

import demo.tool.taobao.pojo.po.TaobaoProductSource;

public class TaobaoProductSourceVO extends TaobaoProductSource {

	private String supplierName;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Override
	public String toString() {
		return "TaobaoProductSourceVO [supplierName=" + supplierName + ", getId()=" + getId() + ", getCommodityId()="
				+ getCommodityId() + ", getSourceId()=" + getSourceId() + ", getMerchantId()=" + getMerchantId()
				+ ", getCommodityName()=" + getCommodityName() + ", getCommodityNameZhTw()=" + getCommodityNameZhTw()
				+ ", getCommodityNameEn()=" + getCommodityNameEn() + ", getCommodityImgName()=" + getCommodityImgName()
				+ ", getIncludePostage()=" + getIncludePostage() + ", getIsAvailable()=" + getIsAvailable()
				+ ", getRemark()=" + getRemark() + ", getCreateTime()=" + getCreateTime() + ", getIsDelete()="
				+ getIsDelete() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
