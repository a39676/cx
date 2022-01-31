package demo.toyParts.educate.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StudentExerciesHistory {
    private Long exerciesId;

    private Long userId;

    private Long subjectType;

    private Long gradeType;

    private LocalDateTime createTime;

    private LocalDateTime compeletionTime;

    private BigDecimal score;

    private BigDecimal points;

    private String filePath;

    public Long getExerciesId() {
        return exerciesId;
    }

    public void setExerciesId(Long exerciesId) {
        this.exerciesId = exerciesId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Long subjectType) {
        this.subjectType = subjectType;
    }

    public Long getGradeType() {
        return gradeType;
    }

    public void setGradeType(Long gradeType) {
        this.gradeType = gradeType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getCompeletionTime() {
        return compeletionTime;
    }

    public void setCompeletionTime(LocalDateTime compeletionTime) {
        this.compeletionTime = compeletionTime;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }
}