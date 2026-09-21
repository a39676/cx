package demo.tool.taobao.pojo.dto;

public class TaobaoAddOfferFromDownstreamBuyerDTO {

	private String offerRawText;
	private Integer internationalNum;

	public String getOfferRawText() {
		return offerRawText;
	}

	public void setOfferRawText(String offerRawText) {
		this.offerRawText = offerRawText;
	}

	public Integer getInternationalNum() {
		return internationalNum;
	}

	public void setInternationalNum(Integer internationalNum) {
		this.internationalNum = internationalNum;
	}

	@Override
	public String toString() {
		return "TaobaoAddOfferFromDownstreamBuyerDTO [offerRawText=" + offerRawText + ", internationalNum="
				+ internationalNum + "]";
	}

}
