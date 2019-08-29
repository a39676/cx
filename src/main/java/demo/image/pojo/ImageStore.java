package demo.image.pojo;

import java.util.Date;

public class ImageStore {
    private Long imageId;

    private String imageUrl;

    private String imageName;

    private Date createTime;

    private Date backupTime;

    private String md5Mark;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime;
    }

    public String getMd5Mark() {
        return md5Mark;
    }

    public void setMd5Mark(String md5Mark) {
        this.md5Mark = md5Mark == null ? null : md5Mark.trim();
    }
    
    public ImageStore createImageStoreFromImageCache(ImageCache ic) {
		ImageStore is = new ImageStore();
		is.setImageId(ic.getImageId());
		is.setCreateTime(ic.getCreateTime());
		is.setImageName(ic.getImageName());
		is.setImageUrl(ic.getImageUrl());
		is.setMd5Mark(ic.getMd5Mark());
		return is;
	}
    
}