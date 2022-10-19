package demo.tool.bookmark.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class BookmarkUrlDTO implements Comparable<BookmarkUrlDTO> {

	private Long id;

	private String name;

	private String url;

	private List<BookmarkTagDTO> tagList = new ArrayList<>();

	private Double weight = 0D;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void addTag(BookmarkTagDTO dto) {
		tagList.add(dto);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<BookmarkTagDTO> getTagList() {
		return tagList;
	}

	public void setTagList(List<BookmarkTagDTO> tagList) {
		this.tagList = tagList;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "BookmarkUrlDTO [id=" + id + ", name=" + name + ", url=" + url + ", tagList=" + tagList + ", weight="
				+ weight + "]";
	}

	@Override
	public int compareTo(BookmarkUrlDTO o) {
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
