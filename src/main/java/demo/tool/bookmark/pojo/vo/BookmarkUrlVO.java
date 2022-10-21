package demo.tool.bookmark.pojo.vo;

import java.util.ArrayList;
import java.util.List;

public class BookmarkUrlVO {

	private String pk;

	private String name;

	private String url;

	private List<BookmarkTagVO> tagVoList = new ArrayList<>();

	private List<String> tagNameList = new ArrayList<>();

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

	public List<BookmarkTagVO> getTagVoList() {
		return tagVoList;
	}

	public void setTagVoList(List<BookmarkTagVO> tagVoList) {
		this.tagVoList = tagVoList;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public List<String> getTagNameList() {
		return tagNameList;
	}

	public void setTagNameList(List<String> tagNameList) {
		this.tagNameList = tagNameList;
	}

	@Override
	public String toString() {
		return "BookmarkUrlVO [pk=" + pk + ", name=" + name + ", url=" + url + ", tagVoList=" + tagVoList
				+ ", tagNameList=" + tagNameList + ", weight=" + weight + "]";
	}
}
