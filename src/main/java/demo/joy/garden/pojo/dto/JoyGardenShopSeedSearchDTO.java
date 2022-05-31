package demo.joy.garden.pojo.dto;

public class JoyGardenShopSeedSearchDTO {

	private String startPK;
	private String endPK;
	private Boolean isNextPage;

	public String getStartPK() {
		return startPK;
	}

	public void setStartPK(String startPK) {
		this.startPK = startPK;
	}

	public String getEndPK() {
		return endPK;
	}

	public void setEndPK(String endPK) {
		this.endPK = endPK;
	}

	public Boolean getIsNextPage() {
		return isNextPage;
	}

	public void setIsNextPage(Boolean isNextPage) {
		this.isNextPage = isNextPage;
	}

	@Override
	public String toString() {
		return "JoyGardenShopSeedSearchDTO [startPK=" + startPK + ", endPK=" + endPK + ", isNextPage=" + isNextPage
				+ "]";
	}

}
