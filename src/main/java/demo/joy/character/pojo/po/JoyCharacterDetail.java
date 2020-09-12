package demo.joy.character.pojo.po;

import java.time.LocalDateTime;

public class JoyCharacterDetail {
    private Long id;

    private Integer characterLevel;

    private Integer characterExp;

    private String characterImgPath;

    private LocalDateTime editTime;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(Integer characterLevel) {
        this.characterLevel = characterLevel;
    }

    public Integer getCharacterExp() {
        return characterExp;
    }

    public void setCharacterExp(Integer characterExp) {
        this.characterExp = characterExp;
    }

    public String getCharacterImgPath() {
        return characterImgPath;
    }

    public void setCharacterImgPath(String characterImgPath) {
        this.characterImgPath = characterImgPath == null ? null : characterImgPath.trim();
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