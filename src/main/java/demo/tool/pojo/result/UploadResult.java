package demo.tool.pojo.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import demo.baseCommon.pojo.result.CommonResultCX;

public class UploadResult extends CommonResultCX {

	private List<String> uploadSuccessFileNameList;

	private List<String> uploadFailFileNameList;

	private Date uploadTime;

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public List<String> getUploadSuccessFileNameList() {
		return uploadSuccessFileNameList;
	}

	public void setUploadSuccessFileNameList(List<String> uploadSuccessFileNameList) {
		this.uploadSuccessFileNameList = uploadSuccessFileNameList;
	}

	public void addUploadSuccessFileName(String uploadSuccessFilename) {
		if(this.uploadSuccessFileNameList == null) {
			this.uploadSuccessFileNameList = new ArrayList<>();
		}
		this.uploadSuccessFileNameList.add(uploadSuccessFilename);
	}
	
	public List<String> getUploadFailFileNameList() {
		return uploadFailFileNameList;
	}

	public void setUploadFailFileNameList(List<String> uploadFailFileNameList) {
		this.uploadFailFileNameList = uploadFailFileNameList;
	}

	@Override
	public String toString() {
		return "UploadResult [uploadSuccessFileNameList=" + uploadSuccessFileNameList + ", uploadFailFileNameList="
				+ uploadFailFileNameList + ", uploadTime=" + uploadTime + "]";
	}

}
