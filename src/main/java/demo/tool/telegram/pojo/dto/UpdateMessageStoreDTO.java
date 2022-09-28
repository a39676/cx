package demo.tool.telegram.pojo.dto;

public class UpdateMessageStoreDTO extends UpdateMessageDTO {

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

}
