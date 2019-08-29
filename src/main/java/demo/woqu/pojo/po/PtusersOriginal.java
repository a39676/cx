package demo.woqu.pojo.po;

import java.util.Date;

public class PtusersOriginal {
	private Long id;

	private String mobile;

	private String telephone;

	private String cnName;

	private Integer gender;

	private String jobtitle;

	private String subcompany;

	private String department;

	private String managerName;

	private String accounttype;

	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName == null ? null : cnName.trim();
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle == null ? null : jobtitle.trim();
	}

	public String getSubcompany() {
		return subcompany;
	}

	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany == null ? null : subcompany.trim();
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department == null ? null : department.trim();
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName == null ? null : managerName.trim();
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype == null ? null : accounttype.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "PtusersOriginal [id=" + id + ", mobile=" + mobile + ", telephone=" + telephone + ", cnName=" + cnName
				+ ", gender=" + gender + ", jobtitle=" + jobtitle + ", subcompany=" + subcompany + ", department="
				+ department + ", managerName=" + managerName + ", accounttype=" + accounttype + ", createDate="
				+ createDate + "]";
	}

}