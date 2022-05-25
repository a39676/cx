package demo.toyParts.educate.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StudentDetail {
    private Long id;

    private Long gradeType;

    private BigDecimal pointsSummary;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGradeType() {
        return gradeType;
    }

    public void setGradeType(Long gradeType) {
        this.gradeType = gradeType;
    }

    public BigDecimal getPointsSummary() {
        return pointsSummary;
    }

    public void setPointsSummary(BigDecimal pointsSummary) {
        this.pointsSummary = pointsSummary;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}