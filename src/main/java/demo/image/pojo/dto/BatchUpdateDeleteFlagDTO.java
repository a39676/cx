package demo.image.pojo.dto;

import java.util.List;

public class BatchUpdateDeleteFlagDTO {

	private Boolean isDelete = false;
	private List<Long> imageIdList;

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public List<Long> getImageIdList() {
		return imageIdList;
	}

	public void setImageIdList(List<Long> imageIdList) {
		this.imageIdList = imageIdList;
	}

	@Override
	public String toString() {
		return "BatchUpdateDeleteFlagDTO [isDelete=" + isDelete + ", imageIdList=" + imageIdList + "]";
	}

}
