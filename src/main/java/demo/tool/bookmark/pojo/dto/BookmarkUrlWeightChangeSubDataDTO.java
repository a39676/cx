package demo.tool.bookmark.pojo.dto;

public class BookmarkUrlWeightChangeSubDataDTO {

	private String urlPK;

	private Double weight = 0D;

	public String getUrlPK() {
		return urlPK;
	}

	public void setUrlPK(String urlPK) {
		this.urlPK = urlPK;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "BookmarkUrlWeightChangeSubDataDTO [urlPK=" + urlPK + ", weight=" + weight + "]";
	}

}
