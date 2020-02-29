package demo.image.pojo.po;

import java.time.LocalDateTime;

public class ImageStore {
    private Long imageId;

    private String imageUrl;

    private String imageName;

    private LocalDateTime createTime;

    private LocalDateTime backupTime;

    private String md5Mark;

    private LocalDateTime validTime;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName == null ? null : imageName.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(LocalDateTime backupTime) {
        this.backupTime = backupTime;
    }

    public String getMd5Mark() {
        return md5Mark;
    }

    public void setMd5Mark(String md5Mark) {
        this.md5Mark = md5Mark == null ? null : md5Mark.trim();
    }

    public LocalDateTime getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }
}