package demo.joy.mercenary.pojo.po;

import java.time.LocalDateTime;

public class JoyMercenaryUser {
    private Long id;

    private Long imgId;

    private Long userId;

    private Long mercenaryId;

    private String mercenaryName;

    private Boolean gender;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMercenaryId() {
        return mercenaryId;
    }

    public void setMercenaryId(Long mercenaryId) {
        this.mercenaryId = mercenaryId;
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