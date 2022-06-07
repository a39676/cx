package demo.joy.garden.pojo.dto;

public class FindSeedPageDTO {

	private Long startId;

	private Long endId;

	private Integer pageSize;

	public Long getStartId() {
		return startId;
	}

	public void setStartId(Long startId) {
		this.startId = startId;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "FindSeedPageDTO [startId=" + startId + ", endId=" + endId + ", pageSize=" + pageSize + "]";
	}

}
