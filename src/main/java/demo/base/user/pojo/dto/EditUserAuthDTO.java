package demo.base.user.pojo.dto;

import java.util.List;

public class EditUserAuthDTO {

	private Long userId;

	private List<Long> newAuthIdList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getNewAuthIdList() {
		return newAuthIdList;
	}

	public void setNewAuthIdList(List<Long> newAuthIdList) {
		this.newAuthIdList = newAuthIdList;
	}

	@Override
	public String toString() {
		return "EditUserAuthDTO [userId=" + userId + ", newAuthIdList=" + newAuthIdList + "]";
	}

}
