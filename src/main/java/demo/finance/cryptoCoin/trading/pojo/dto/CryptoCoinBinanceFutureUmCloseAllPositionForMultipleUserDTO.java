package demo.finance.cryptoCoin.trading.pojo.dto;

import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionMultipleUserCommonDTO;

public class CryptoCoinBinanceFutureUmCloseAllPositionForMultipleUserDTO
		extends CryptoCoinInteractionMultipleUserCommonDTO {

	/** {@link BinancePositionSideType} */
	private Integer positionSideCode;

	public Integer getPositionSideCode() {
		return positionSideCode;
	}

	public void setPositionSideCode(Integer positionSideCode) {
		this.positionSideCode = positionSideCode;
	}

	@Override
	public String toString() {
		return "CryptoCoinBinanceFutureUmCloseAllPositionForMultipleUserDTO [positionSideCode=" + positionSideCode
				+ ", userIdList=" + userIdList + ", userNicknameList=" + userNicknameList + ", totpCode=" + totpCode
				+ ", exchangeCode=" + exchangeCode + "]";
	}

}
