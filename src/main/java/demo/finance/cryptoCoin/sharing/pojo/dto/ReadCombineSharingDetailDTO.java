package demo.finance.cryptoCoin.sharing.pojo.dto;

import java.util.List;

public class ReadCombineSharingDetailDTO {

	private List<String> detailPkList;

	public List<String> getDetailPkList() {
		return detailPkList;
	}

	public void setDetailPkList(List<String> detailPkList) {
		this.detailPkList = detailPkList;
	}

	@Override
	public String toString() {
		return "ReadCombineSharingDetail [detailPkList=" + detailPkList + "]";
	}

}
