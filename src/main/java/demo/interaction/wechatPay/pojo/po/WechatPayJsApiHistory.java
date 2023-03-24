package demo.interaction.wechatPay.pojo.po;

import java.time.LocalDateTime;

public class WechatPayJsApiHistory {
    private Long id;

    private String openId;

    private Boolean isPaySuccess;

    private Boolean isHandleSuccess;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Boolean getIsPaySuccess() {
        return isPaySuccess;
    }

    public void setIsPaySuccess(Boolean isPaySuccess) {
        this.isPaySuccess = isPaySuccess;
    }

    public Boolean getIsHandleSuccess() {
        return isHandleSuccess;
    }

    public void setIsHandleSuccess(Boolean isHandleSuccess) {
        this.isHandleSuccess = isHandleSuccess;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}