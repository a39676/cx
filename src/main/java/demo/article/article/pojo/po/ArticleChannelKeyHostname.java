package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleChannelKeyHostname extends ArticleChannelKeyHostnameKey {
    private LocalDateTime createTime;

    private Integer keyType;

    private Boolean isDelete;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}