package demo.joy.mercenary.pojo.po;

import java.time.LocalDateTime;

public class JoyUserMercenary {
    private Long id;

    private Long userId;

    private Long mercenaryId;

    private String mercenaryName;

    private String attributePath;

    private String skillPath;

    private Boolean gender;

    private LocalDateTime editTime;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAttributePath() {
        return attributePath;
    }

    public void setAttributePath(String attributePath) {
        this.attributePath = attributePath == null ? null : attributePath.trim();
    }

    public String getSkillPath() {
        return skillPath;
    }

    public void setSkillPath(String skillPath) {
        this.skillPath = skillPath == null ? null : skillPath.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
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