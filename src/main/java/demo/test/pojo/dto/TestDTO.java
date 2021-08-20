package demo.test.pojo.dto;

import java.time.LocalDateTime;

public class TestDTO {

	private LocalDateTime datetime;

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "TestDTO [datetime=" + datetime + "]";
	}

}
