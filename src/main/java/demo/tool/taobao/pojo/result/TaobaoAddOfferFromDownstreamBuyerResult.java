package demo.tool.taobao.pojo.result;

import java.util.Set;

import auxiliaryCommon.pojo.result.CommonResult;

public class TaobaoAddOfferFromDownstreamBuyerResult extends CommonResult {

	private Long buyerOrderID;
	private String receiverFullInfo;
	private Set<String> productCommdityIdSet;
	private String orderJsonStr;

	public Long getBuyerOrderID() {
		return buyerOrderID;
	}

	public void setBuyerOrderID(Long buyerOrderID) {
		this.buyerOrderID = buyerOrderID;
	}

	public String getReceiverFullInfo() {
		return receiverFullInfo;
	}

	public void setReceiverFullInfo(String receiverFullInfo) {
		this.receiverFullInfo = receiverFullInfo;
	}

	public Set<String> getProductCommdityIdSet() {
		return productCommdityIdSet;
	}

	public void setProductCommdityIdSet(Set<String> productCommdityIdSet) {
		this.productCommdityIdSet = productCommdityIdSet;
	}

	public String getOrderJsonStr() {
		return orderJsonStr;
	}

	public void setOrderJsonStr(String orderJsonStr) {
		this.orderJsonStr = orderJsonStr;
	}

	@Override
	public String toString() {
		return "TaobaoAddOfferFromDownstreamBuyerResult [buyerOrderID=" + buyerOrderID + ", receiverFullInfo="
				+ receiverFullInfo + ", productCommdityIdSet=" + productCommdityIdSet + ", orderJsonStr=" + orderJsonStr
				+ "]";
	}

}
