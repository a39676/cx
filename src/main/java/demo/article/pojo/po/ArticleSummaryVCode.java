package demo.article.pojo.po;

import java.util.Date;

public class ArticleSummaryVCode {
    private Long vcodeId;

    private Long articleId;

    private Date createTime;

    private String privateKey;

    public Long getVcodeId() {
        return vcodeId;
    }

    public void setVcodeId(Long vcodeId) {
        this.vcodeId = vcodeId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }
}