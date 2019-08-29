package demo.article.pojo.po;

import java.util.Date;

public class ArticleHot implements Comparable<ArticleHot>{
    private Long articleId;

    private Long channelId;

    private Date createTime;

    private Date validTime;

    private Integer hotLevel;

    private Boolean isDelete;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Integer getHotLevel() {
        return hotLevel;
    }

    public void setHotLevel(Integer hotLevel) {
        this.hotLevel = hotLevel;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

	@Override
	public int compareTo(ArticleHot o) {
		if(this.hotLevel == null || o.hotLevel == null) {
			if(this.hotLevel == null && o.hotLevel == null) {
				return 0;
			} else if(this.hotLevel == null) {
				return -1;
			} else {
				return 1;
			}
		} else {
			if(this.hotLevel > o.hotLevel) {
				return 1;
			} else if(this.hotLevel < o.hotLevel) {
				return -1;
			} 
		}
		return 0;
	}
}