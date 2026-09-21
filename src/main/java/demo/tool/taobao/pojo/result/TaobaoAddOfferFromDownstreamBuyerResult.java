package demo.tool.taobao.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class TaobaoAddOfferFromDownstreamBuyerResult extends CommonResult {

	private Long buyerOrderID;
	private String receiverFullInfo;

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

	@Override
	public String toString() {
		return "TaobaoAddOfferFromDownstreamBuyerResult [buyerOrderID=" + buyerOrderID + ", receiverFullInfo="
				+ receiverFullInfo + "]";
	}

}
