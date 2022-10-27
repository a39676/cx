package demo.tool.bookmark.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class EditBookmarkUrlResult extends CommonResult {

	private String bookmarkUrlPk;

	public String getBookmarkUrlPk() {
		return bookmarkUrlPk;
	}

	public void setBookmarkUrlPk(String bookmarkUrlPk) {
		this.bookmarkUrlPk = bookmarkUrlPk;
	}

	@Override
	public String toString() {
		return "EditBookmarkUrlResult [bookmarkUrlPk=" + bookmarkUrlPk + "]";
	}

}
