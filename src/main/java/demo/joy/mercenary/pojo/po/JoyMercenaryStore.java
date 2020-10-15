package demo.joy.mercenary.pojo.po;

import java.time.LocalDateTime;

public class JoyMercenaryStore {
    private Long id;

    private Long imgId;

    private String mercenaryName;

    private Boolean gender;

    private Integer maxSale;

    private Integer skillCount;

    private String description;

    private String attributePath;

    private LocalDateTime editTime;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public String getMercenaryName() {
        return mercenaryName;
    }

    public void setMercenaryName(String mercenaryName) {
        this.mercenaryName = mercenaryName == null ? null : mercenaryName.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getMaxSale() {
        return maxSale;
    }

    public void setMaxSale(Integer maxSale) {
        this.maxSale = maxSale;
    }

    public Integer getSkillCount() {
        return skillCount;
    }

    public void setSkillCount(Integer skillCount) {
        this.skillCount = skillCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getAttributePath() {
        return attributePath;
    }

    public void setAttributePath(String attributePath) {
        this.attributePath = attributePath == null ? null : attributePath.trim();
    }

    public LocalDateTime getEditTime() {
        return editTime;
    }

    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
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