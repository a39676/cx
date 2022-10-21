package demo.tool.bookmark.pojo.dto;

import java.util.List;

public class DeleteBookmarkTagDTO {

	private String bookmarkPK;

	private List<String> tagPkList;

	public String getBookmarkPK() {
		return bookmarkPK;
	}

	public void setBookmarkPK(String bookmarkPK) {
		this.bookmarkPK = bookmarkPK;
	}

	public List<String> getTagPkList() {
		return tagPkList;
	}

	public void setTagPkList(List<String> tagPkList) {
		this.tagPkList = tagPkList;
	}

	@Override
	public String toString() {
		return "DeleteBookmarkTagDTO [bookmarkPK=" + bookmarkPK + ", tagPkList=" + tagPkList + "]";
	}

}
