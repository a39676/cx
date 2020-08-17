package demo.joy.scene.pojo.po;

import java.time.LocalDateTime;

public class JoySceneGroup {
    private Long id;

    private String sceneGroupName;

    private String remark;

    private LocalDateTime createTime;

    private Long createBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSceneGroupName() {
        return sceneGroupName;
    }

    public void setSceneGroupName(String sceneGroupName) {
        this.sceneGroupName = sceneGroupName == null ? null : sceneGroupName.trim();
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
}