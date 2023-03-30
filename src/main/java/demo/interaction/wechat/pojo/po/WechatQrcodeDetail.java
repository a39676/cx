package demo.interaction.wechat.pojo.po;

import java.time.LocalDateTime;

public class WechatQrcodeDetail {
    private Integer id;

    private Integer sourceOfficialAccount;

    private String sceneName;

    private String remark;

    private String url;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceOfficialAccount() {
        return sourceOfficialAccount;
    }

    public void setSourceOfficialAccount(Integer sourceOfficialAccount) {
        this.sourceOfficialAccount = sourceOfficialAccount;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName == null ? null : sceneName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}