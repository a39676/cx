package demo.tool.bookmark.pojo.dto;

public class BookmarkTagWeightChangeSubDataDTO {

	private String tagPK;

	private Double weight = 0D;

	public String getTagPK() {
		return tagPK;
	}

	public void setTagPK(String tagPK) {
		this.tagPK = tagPK;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "BookmarkTagWeightChangeSubDataDTO [tagPK=" + tagPK + ", weight=" + weight + "]";
	}

}
