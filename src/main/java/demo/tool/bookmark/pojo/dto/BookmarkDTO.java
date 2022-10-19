package demo.tool.bookmark.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDTO {

	private String bookmarkName;

	private List<BookmarkDTO> folderList = new ArrayList<>();

	private List<BookmarkUrlDTO> urlList = new ArrayList<>();

	/** 包括所有 url 的 Tag */
	private List<BookmarkTagDTO> allTagList = new ArrayList<>();

	public String getBookmarkName() {
		return bookmarkName;
	}

	public void setBookmarkName(String bookmarkName) {
		this.bookmarkName = bookmarkName;
	}

	public List<BookmarkDTO> getFolderList() {
		return folderList;
	}

	public void setFolderList(List<BookmarkDTO> folderList) {
		this.folderList = folderList;
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
		return "BookmarkDTO [bookmarkName=" + bookmarkName + ", folderList=" + folderList + ", urlList=" + urlList
				+ ", allTagList=" + allTagList + "]";
	}

}
