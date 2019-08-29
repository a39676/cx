package demo.account_holder.pojo.po;

import java.util.Date;

public class AccountHolder {
    private Long accountHolderId;

    private Long userId;

    private String accountHolderName;

    private String gender;

    private Date birth;

    private Long mobileNumber;

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName == null ? null : accountHolderName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

	@Override
	public String toString() {
		return "AccountHolder [accountHolderId=" + accountHolderId + ", userId=" + userId + ", accountHolderName="
				+ accountHolderName + ", gender=" + gender + ", birth=" + birth + ", mobileNumber=" + mobileNumber
				+ "]";
	}
    
    
}