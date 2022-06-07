package demo.joy.garden.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JoyGardenShopGoods {
    private Long id;

    private Integer objectType;

    private Integer objectSubType;

    private Long objectId;

    private String objectName;

    private Long imgId;

    private Integer counting;

    private BigDecimal objectPrice;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectSubType() {
        return objectSubType;
    }

    public void setObjectSubType(Integer objectSubType) {
        this.objectSubType = objectSubType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public Integer getCounting() {
        return counting;
    }

    public void setCounting(Integer counting) {
        this.counting = counting;
    }

    public BigDecimal getObjectPrice() {
        return objectPrice;
    }

    public void setObjectPrice(BigDecimal objectPrice) {
        this.objectPrice = objectPrice;
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
}