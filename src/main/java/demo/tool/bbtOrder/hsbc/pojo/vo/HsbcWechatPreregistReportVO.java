package demo.tool.bbtOrder.hsbc.pojo.vo;

import autoTest.testEvent.scheduleClawing.hsbc.pojo.type.HsbcIdType;
import tool.pojo.type.InternationalityType;

public class HsbcWechatPreregistReportVO {

	private String idNumber;
	private String phoneNumber;
	private String customerFirstName;
	private String customerLastName;
	/** {@link HsbcIdType} */
	private String idTypeCnName;
	/** {@link InternationalityType} */
	private String areaTypeCnName;
	/** {@link InternationalityType} */
	private String phoneAreaCnName;

	private String startTimeStr;
	private String endTimeStr;
	private Boolean isPass;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getIdTypeCnName() {
		return idTypeCnName;
	}

	public void setIdTypeCnName(String idTypeCnName) {
		this.idTypeCnName = idTypeCnName;
	}

	public String getAreaTypeCnName() {
		return areaTypeCnName;
	}

	public void setAreaTypeCnName(String areaTypeCnName) {
		this.areaTypeCnName = areaTypeCnName;
	}

	public String getPhoneAreaCnName() {
		return phoneAreaCnName;
	}

	public void setPhoneAreaCnName(String phoneAreaCnName) {
		this.phoneAreaCnName = phoneAreaCnName;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	@Override
	public String toString() {
		return "HsbcWechatPreregistReportVO [idNumber=" + idNumber + ", phoneNumber=" + phoneNumber
				+ ", customerFirstName=" + customerFirstName + ", customerLastName=" + customerLastName
				+ ", idTypeCnName=" + idTypeCnName + ", areaTypeCnName=" + areaTypeCnName + ", phoneAreaCnName="
				+ phoneAreaCnName + ", startTimeStr=" + startTimeStr + ", endTimeStr=" + endTimeStr + ", isPass="
				+ isPass + "]";
	}

}
