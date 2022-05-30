package demo.joy.garden.pojo.po;

import java.time.LocalDateTime;

public class JoyGardenPlant {
    private Long landId;

    private Long plantCatalogId;

    private Long stageId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDelete;

    public Long getLandId() {
        return landId;
    }

    public void setLandId(Long landId) {
        this.landId = landId;
    }

    public Long getPlantCatalogId() {
        return plantCatalogId;
    }

    public void setPlantCatalogId(Long plantCatalogId) {
        this.plantCatalogId = plantCatalogId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
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