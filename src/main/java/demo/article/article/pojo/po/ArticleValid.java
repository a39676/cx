package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleValid {
    private Long articleId;

    private LocalDateTime validTime;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public LocalDateTime getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }
}