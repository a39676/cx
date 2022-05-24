package demo.joy.image.common.pojo.po;

import java.time.LocalDateTime;

public class JoyImageStore {
    private Long id;

    private Integer moduleId;

    private Integer subModuleId;

    private String imagePath;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getSubModuleId() {
        return subModuleId;
    }

    public void setSubModuleId(Integer subModuleId) {
        this.subModuleId = subModuleId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}