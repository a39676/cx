package demo.pmemo.pojo.dto;

import java.util.List;

public class UpdateMessageResponseStoreDTO {

	private List<UpdateMessageStoreDTO> noticeList;

	public List<UpdateMessageStoreDTO> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<UpdateMessageStoreDTO> noticeList) {
		this.noticeList = noticeList;
	}

	@Override
	public String toString() {
		return "UpdateMessageResponseStoreDTO [noticeList=" + noticeList + "]";
	}

}
