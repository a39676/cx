package demo.pmemo.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class UpdateMessageResponseStoreDTO {

	private List<UpdateMessageStoreDTO> noticeList = new ArrayList<UpdateMessageStoreDTO>();

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
