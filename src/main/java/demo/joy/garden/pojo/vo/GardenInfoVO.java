package demo.joy.garden.pojo.vo;

public class GardenInfoVO {

	private String pk;

	private String gardenName;

	private Integer fieldCount;

	private Integer wetlandCount;

	private Integer woodlandCount;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getGardenName() {
		return gardenName;
	}

	public void setGardenName(String gardenName) {
		this.gardenName = gardenName;
	}

	public Integer getFieldCount() {
		return fieldCount;
	}

	public void setFieldCount(Integer fieldCount) {
		this.fieldCount = fieldCount;
	}

	public Integer getWetlandCount() {
		return wetlandCount;
	}

	public void setWetlandCount(Integer wetlandCount) {
		this.wetlandCount = wetlandCount;
	}

	public Integer getWoodlandCount() {
		return woodlandCount;
	}

	public void setWoodlandCount(Integer woodlandCount) {
		this.woodlandCount = woodlandCount;
	}

	@Override
	public String toString() {
		return "GardenInfoVO [pk=" + pk + ", gardenName=" + gardenName + ", fieldCount=" + fieldCount
				+ ", wetlandCount=" + wetlandCount + ", woodlandCount=" + woodlandCount + "]";
	}

}
