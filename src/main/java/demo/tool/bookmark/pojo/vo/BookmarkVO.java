package demo.tool.bookmark.pojo.vo;

import java.util.ArrayList;
import java.util.List;

public class BookmarkVO {

	private String pk;

	private String bookmarkName;

	private List<BookmarkUrlVO> urlList = new ArrayList<>();

	/** 包括所有 url 的 Tag */
	private List<BookmarkTagVO> allTagList = new ArrayList<>();

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

	public List<BookmarkUrlVO> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<BookmarkUrlVO> urlList) {
		this.urlList = urlList;
	}

	public List<BookmarkTagVO> getAllTagList() {
		return allTagList;
	}

	public void setAllTagList(List<BookmarkTagVO> allTagList) {
		this.allTagList = allTagList;
	}

	@Override
	public String toString() {
		return "BookmarkVO [pk=" + pk + ", bookmarkName=" + bookmarkName + ", urlList=" + urlList + ", allTagList="
				+ allTagList + "]";
	}

}
