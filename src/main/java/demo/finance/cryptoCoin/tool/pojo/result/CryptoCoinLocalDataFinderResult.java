package demo.finance.cryptoCoin.tool.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;

public class CryptoCoinLocalDataFinderResult extends CommonResult {

	private List<CryptoCoinCatalog> catalogList;

	public List<CryptoCoinCatalog> getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(List<CryptoCoinCatalog> catalogList) {
		this.catalogList = catalogList;
	}

	@Override
	public String toString() {
		return "CryptoCoinLocalDataFinderResult [catalogList=" + catalogList + ", success=" + success + "]";
	}

}
