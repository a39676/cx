package demo.tool.bookmark.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class BookmarkUrlDTO {

	private String name;

	private String url;

	private List<BookmarkTagDTO> tagList = new ArrayList<>();

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

	@Override
	public String toString() {
		return "BookmarkUrlDTO [name=" + name + ", url=" + url + ", tagList=" + tagList + "]";
	}

}
