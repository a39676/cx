package demo.vcode.pojo.param;

import java.util.List;

public class UpdateDeleteMarkParam {

	private List<Long> idList;

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	@Override
	public String toString() {
		return "DeleteInvalidCodeParam [idList=" + idList + "]";
	}

}
