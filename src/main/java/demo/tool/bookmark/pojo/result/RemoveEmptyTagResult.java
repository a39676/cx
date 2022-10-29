package demo.tool.bookmark.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class RemoveEmptyTagResult extends CommonResult {

	private List<String> removedTagNameList;

	public List<String> getRemovedTagNameList() {
		return removedTagNameList;
	}

	public void setRemovedTagNameList(List<String> removedTagNameList) {
		this.removedTagNameList = removedTagNameList;
	}

	@Override
	public String toString() {
		return "RemoveEmptyTagResult []";
	}

}
