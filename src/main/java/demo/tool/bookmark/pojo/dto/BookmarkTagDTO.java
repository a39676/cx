package demo.tool.bookmark.pojo.dto;

import java.util.Objects;

public class BookmarkTagDTO implements Comparable<BookmarkTagDTO> {

	private Long id;

	private String tagName;

	private Double weight = 0D;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "BookmarkTagDTO [id=" + id + ", tagName=" + tagName + ", weight=" + weight + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tagName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookmarkTagDTO other = (BookmarkTagDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(tagName, other.tagName);
	}

	@Override
	public int compareTo(BookmarkTagDTO o) {
		if (o.weight == null && this.weight == null) {
			return 0;
		}

		if (o.weight == null && this.weight != null) {
			return -1;
		}

		if (o.weight != null && this.weight == null) {
			return 1;
		}

		if (this.weight > o.weight) {
			return 1;
		} else if (this.weight < o.weight) {
			return -1;
		}
		return 0;
	}

}
