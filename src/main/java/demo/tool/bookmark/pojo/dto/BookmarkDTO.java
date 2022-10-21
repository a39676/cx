package demo.tool.bookmark.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDTO {

	private Long id;

	private String bookmarkName;

	private List<BookmarkUrlDTO> urlList = new ArrayList<>();

	/** 包括所有 url 的 Tag */
	private List<BookmarkTagDTO> allTagList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookmarkName() {
		return bookmarkName;
	}

	public void setBookmarkName(String bookmarkName) {
		this.bookmarkName = bookmarkName;
	}

	public List<BookmarkUrlDTO> getUrlList() {
		return urlList;
	}

	public void addUrl(BookmarkUrlDTO u) {
		urlList.add(u);
	}

	public void setUrlList(List<BookmarkUrlDTO> urlList) {
		this.urlList = urlList;
	}

	public List<BookmarkTagDTO> getAllTagList() {
		return allTagList;
	}

	public void addTag(BookmarkTagDTO t) {
		allTagList.add(t);
	}

	public void setAllTagList(List<BookmarkTagDTO> allTagList) {
		this.allTagList = allTagList;
	}

	@Override
	public String toString() {
		return "BookmarkDTO [id=" + id + ", bookmarkName=" + bookmarkName + ", urlList=" + urlList + ", allTagList="
				+ allTagList + "]";
	}

}
