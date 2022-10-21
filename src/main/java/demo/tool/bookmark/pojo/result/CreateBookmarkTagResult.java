package demo.tool.bookmark.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class CreateBookmarkTagResult extends CommonResult {

	private String pk;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "CreateBookmarkTagResult [pk=" + pk + "]";
	}

}
