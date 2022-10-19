package demo.tool.bookmark.pojo.vo;

import java.util.ArrayList;
import java.util.List;

import demo.tool.bookmark.pojo.dto.BookmarkDTO;
import demo.tool.bookmark.pojo.dto.BookmarkTagDTO;
import demo.tool.bookmark.pojo.dto.BookmarkUrlDTO;

public class BookmarkVO {

	private String pk;

	private String bookmarkName;

	private List<BookmarkDTO> folderList = new ArrayList<>();

	private List<BookmarkUrlDTO> urlList = new ArrayList<>();

	/** 包括所有 url 的 Tag */
	private List<BookmarkTagDTO> allTagList = new ArrayList<>();

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

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

	public void setUrlList(List<BookmarkUrlDTO> urlList) {
		this.urlList = urlList;
	}

	public List<BookmarkTagDTO> getAllTagList() {
		return allTagList;
	}

	public void setAllTagList(List<BookmarkTagDTO> allTagList) {
		this.allTagList = allTagList;
	}

	@Override
	public String toString() {
		return "BookMarkVO [pk=" + pk + ", bookmarkName=" + bookmarkName + ", folderList=" + folderList + ", urlList="
				+ urlList + ", allTagList=" + allTagList + "]";
	}

}
