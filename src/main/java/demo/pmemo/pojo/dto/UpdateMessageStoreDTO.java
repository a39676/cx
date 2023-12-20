package demo.pmemo.pojo.dto;

import demo.tool.textMessageForward.telegram.pojo.dto.TelegramUpdateMessageDTO;

public class UpdateMessageStoreDTO extends TelegramUpdateMessageDTO implements Comparable<UpdateMessageStoreDTO>{

	private Integer orderNumber;

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public String toString() {
		return "UpdateMessageStoreDTO [orderNumber=" + orderNumber + ", getUpdate_id()=" + getUpdate_id()
				+ ", getMessage()=" + getMessage() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

	@Override
	public int compareTo(UpdateMessageStoreDTO o) {
		if(o.getUpdate_id() < this.getUpdate_id()) {
			return -1;
		} else if (o.getOrderNumber() > this.getUpdate_id()) {
			return 1;
		}
		return 0;
	}

}
