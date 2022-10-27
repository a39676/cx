package demo.tool.bookmark.pojo.vo;

public class BookmarkTagVO implements Comparable<BookmarkTagVO> {

	private String pk;

	private String tagName;

	private Double weight = 0D;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "BookmarkTagVO [pk=" + pk + ", tagName=" + tagName + ", weight=" + weight + "]";
	}

	@Override
	public int compareTo(BookmarkTagVO o) {
		if (this.weight > o.weight) {
			return 1;
		} else if (this.weight < o.weight) {
			return -1;
		}
		return 0;
	}

}
