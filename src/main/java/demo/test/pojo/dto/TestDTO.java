package demo.test.pojo.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class TestDTO {

//	@JsonFormat(pattern = DateTimeConstant.normalDateTimeFormat)
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime startTime = LocalDateTime.now();

	private BigInteger bigInt = new BigInteger(String.valueOf(Long.MAX_VALUE));

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public BigInteger getBigInt() {
		return bigInt;
	}

	public void setBigInt(BigInteger bigInt) {
		this.bigInt = bigInt;
	}

}
