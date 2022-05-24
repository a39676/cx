package demo.joy.garden.pojo.po;

import java.time.LocalDateTime;

public class JoyGardenPlantGrowingStage {
    private Long id;

    private Long plantId;

    private Long nextStageId;

    private String stageName;

    private Integer livingMinute;

    private Long imgId;

    private LocalDateTime updateTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getNextStageId() {
        return nextStageId;
    }

    public void setNextStageId(Long nextStageId) {
        this.nextStageId = nextStageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName == null ? null : stageName.trim();
    }

    public Integer getLivingMinute() {
        return livingMinute;
    }

    public void setLivingMinute(Integer livingMinute) {
        this.livingMinute = livingMinute;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
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