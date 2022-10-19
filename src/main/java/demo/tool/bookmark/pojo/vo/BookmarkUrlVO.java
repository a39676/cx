package demo.tool.bookmark.pojo.vo;

import java.util.ArrayList;
import java.util.List;

import demo.tool.bookmark.pojo.dto.BookmarkTagDTO;

public class BookmarkUrlVO {

	private String pk;

	private String name;

	private String url;

	private List<BookmarkTagDTO> tagList = new ArrayList<>();

	private Double weight = 0D;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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
		return "BookmarkUrlVO [pk=" + pk + ", name=" + name + ", url=" + url + ", tagList=" + tagList + ", weight="
				+ weight + "]";
	}
}
