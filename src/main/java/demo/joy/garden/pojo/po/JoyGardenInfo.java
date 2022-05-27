package demo.joy.garden.pojo.po;

import java.time.LocalDateTime;

public class JoyGardenInfo {
    private Long id;

    private String gardenName;

    private Integer fieldCount;

    private Integer wetlandCount;

    private Integer woodlandCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName == null ? null : gardenName.trim();
    }

    public Integer getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(Integer fieldCount) {
        this.fieldCount = fieldCount;
    }

    public Integer getWetlandCount() {
        return wetlandCount;
    }

    public void setWetlandCount(Integer wetlandCount) {
        this.wetlandCount = wetlandCount;
    }

    public Integer getWoodlandCount() {
        return woodlandCount;
    }

    public void setWoodlandCount(Integer woodlandCount) {
        this.woodlandCount = woodlandCount;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}