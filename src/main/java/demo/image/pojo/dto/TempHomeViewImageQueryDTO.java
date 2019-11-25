package demo.image.pojo.dto;

import java.time.LocalDateTime;

public class TempHomeViewImageQueryDTO {

	private LocalDateTime p1Start;
	private LocalDateTime p1End;
	private LocalDateTime p2Start;
	private LocalDateTime p2End;
	private LocalDateTime p3Start;
	private LocalDateTime p3End;

	public LocalDateTime getP1Start() {
		return p1Start;
	}

	public void setP1Start(LocalDateTime p1Start) {
		this.p1Start = p1Start;
	}

	public LocalDateTime getP1End() {
		return p1End;
	}

	public void setP1End(LocalDateTime p1End) {
		this.p1End = p1End;
	}

	public LocalDateTime getP2Start() {
		return p2Start;
	}

	public void setP2Start(LocalDateTime p2Start) {
		this.p2Start = p2Start;
	}

	public LocalDateTime getP2End() {
		return p2End;
	}

	public void setP2End(LocalDateTime p2End) {
		this.p2End = p2End;
	}

	public LocalDateTime getP3Start() {
		return p3Start;
	}

	public void setP3Start(LocalDateTime p3Start) {
		this.p3Start = p3Start;
	}

	public LocalDateTime getP3End() {
		return p3End;
	}

	public void setP3End(LocalDateTime p3End) {
		this.p3End = p3End;
	}

}
