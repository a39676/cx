package demo.tool.bookmark.pojo.dto;

import java.util.List;

public class BookmarkWeightChangeMainDTO {

	private String bookmarkPK;

	private List<BookmarkTagWeightChangeSubDataDTO> tagSubDataList;

	private List<BookmarkUrlWeightChangeSubDataDTO> urlSudDataList;

	public String getBookmarkPK() {
		return bookmarkPK;
	}

	public void setBookmarkPK(String bookmarkPK) {
		this.bookmarkPK = bookmarkPK;
	}

	public List<BookmarkTagWeightChangeSubDataDTO> getTagSubDataList() {
		return tagSubDataList;
	}

	public void setTagSubDataList(List<BookmarkTagWeightChangeSubDataDTO> tagSubDataList) {
		this.tagSubDataList = tagSubDataList;
	}

	public List<BookmarkUrlWeightChangeSubDataDTO> getUrlSubDataList() {
		return urlSudDataList;
	}

	public void setUrlSudDataList(List<BookmarkUrlWeightChangeSubDataDTO> urlSudDataList) {
		this.urlSudDataList = urlSudDataList;
	}

	@Override
	public String toString() {
		return "BookmarkWeightChangeMainDTO [bookmarkPK=" + bookmarkPK + ", tagSubDataList=" + tagSubDataList
				+ ", urlSudDataList=" + urlSudDataList + "]";
	}

}
