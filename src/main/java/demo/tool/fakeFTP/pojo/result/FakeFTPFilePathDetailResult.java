package demo.tool.fakeFTP.pojo.result;

import java.util.List;

import demo.common.pojo.result.CommonResultCX;

public class FakeFTPFilePathDetailResult extends CommonResultCX {

	List<FakeFTPFileDetail> fileDetails;

	List<FakeFTPFileDetail> folderDetails;

	public List<FakeFTPFileDetail> getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(List<FakeFTPFileDetail> fileDetails) {
		this.fileDetails = fileDetails;
	}

	public List<FakeFTPFileDetail> getFolderDetails() {
		return folderDetails;
	}

	public void setFolderDetails(List<FakeFTPFileDetail> folderDetails) {
		this.folderDetails = folderDetails;
	}

	@Override
	public String toString() {
		return "FakeFTPFilePathDetailResult [fileDetails=" + fileDetails + ", folderDetails=" + folderDetails + "]";
	}

}
