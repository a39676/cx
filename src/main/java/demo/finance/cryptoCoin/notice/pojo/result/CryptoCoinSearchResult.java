package demo.finance.cryptoCoin.notice.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.notice.pojo.vo.CryptoCoinNoticeVO;

public class CryptoCoinSearchResult extends CommonResult {

	private List<CryptoCoinNoticeVO> voList;

	@Override
	public String toString() {
		return "CryptoCoinSearchResult [voList=" + voList + "]";
	}

	public List<CryptoCoinNoticeVO> getVoList() {
		return voList;
	}

	public void setVoList(List<CryptoCoinNoticeVO> voList) {
		this.voList = voList;
	}
	
	
}
