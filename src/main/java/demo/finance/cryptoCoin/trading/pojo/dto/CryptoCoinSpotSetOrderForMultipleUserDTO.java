package demo.finance.cryptoCoin.trading.pojo.dto;

import java.math.BigDecimal;

import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinanceTimeInForceType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionMultipleUserCommonDTO;

public class CryptoCoinSpotSetOrderForMultipleUserDTO extends CryptoCoinInteractionMultipleUserCommonDTO {

	/** {@link BinanceOrderSideType} */
	private Integer sideCode;
	/** {@link BinanceOrderTypeType} */
	private Integer typeCode;
	/** {@link BinanceTimeInForceType} */
	private Integer timeInForceCode = BinanceTimeInForceType.GTC.getCode();
	private BigDecimal price;
	private BigDecimal quantity;

	public Integer getSideCode() {
		return sideCode;
	}

	public void setSideCode(Integer sideCode) {
		this.sideCode = sideCode;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getTimeInForceCode() {
		return timeInForceCode;
	}

	public void setTimeInForceCode(Integer timeInForceCode) {
		this.timeInForceCode = timeInForceCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CryptoCoinSpotSetOrderForMultipleUserDTO [symbol=" + symbol + ", sideCode=" + sideCode + ", typeCode="
				+ typeCode + ", timeInForceCode=" + timeInForceCode + ", price=" + price + ", quantity=" + quantity
				+ ", userIdList=" + userIdList + ", userNicknameList=" + userNicknameList + ", totpCode=" + totpCode
				+ ", exchangeCode=" + exchangeCode + "]";
	}

}
