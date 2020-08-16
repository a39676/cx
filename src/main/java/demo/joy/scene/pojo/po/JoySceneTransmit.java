package demo.joy.scene.pojo.po;

import java.time.LocalDateTime;

public class JoySceneTransmit {
    private Long id;

    private Long fromSceneId;

    private Long toSceneId;

    private String remark;

    private LocalDateTime createTime;

    private Long createBy;

    private LocalDateTime editTime;

    private Long editBy;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromSceneId() {
        return fromSceneId;
    }

    public void setFromSceneId(Long fromSceneId) {
        this.fromSceneId = fromSceneId;
    }

    public Long getToSceneId() {
        return toSceneId;
    }

    public void setToSceneId(Long toSceneId) {
        this.toSceneId = toSceneId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getEditTime() {
        return editTime;
    }

    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
    }

    public Long getEditBy() {
        return editBy;
    }

    public void setEditBy(Long editBy) {
        this.editBy = editBy;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}