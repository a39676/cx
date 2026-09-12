package demo.tool.taobao.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.po.TaobaoProductSource;

public class TaobaoGetHotSaleListResult extends CommonResult {

	private List<TaobaoProductSource> productSourceList;

	public List<TaobaoProductSource> getProductSourceList() {
		return productSourceList;
	}

	public void setProductSourceList(List<TaobaoProductSource> productSourceList) {
		this.productSourceList = productSourceList;
	}

	@Override
	public String toString() {
		return "TaobaoGetHotSaleListResult [productSourceList=" + productSourceList + "]";
	}

}
