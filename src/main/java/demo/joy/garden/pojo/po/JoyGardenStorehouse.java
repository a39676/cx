package demo.joy.garden.pojo.po;

import java.time.LocalDateTime;

public class JoyGardenStorehouse {
    private Long userId;

    private Integer objectType;

    private Integer objectSubType;

    private String objectName;

    private Long objectId;

    private Long imgId;

    private Integer counting;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectSubType() {
        return objectSubType;
    }

    public void setObjectSubType(Integer objectSubType) {
        this.objectSubType = objectSubType;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public Integer getCounting() {
        return counting;
    }

    public void setCounting(Integer counting) {
        this.counting = counting;
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
}