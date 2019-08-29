package demo.account_info.pojo.po;

import java.time.LocalDateTime;

public class AccountInfoMarker {
	
    private Long accountId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer updateCount;

    private String marker;

    
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String accountInfo) {
        this.marker = accountInfo == null ? null :  accountInfo.trim();
    }
    
}