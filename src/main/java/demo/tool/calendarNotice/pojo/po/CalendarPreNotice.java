package demo.tool.calendarNotice.pojo.po;

import java.time.LocalDateTime;

public class CalendarPreNotice {
    private Long id;

    private Long bindNoticeId;

    private Integer repeatTimeUnit;

    private Integer repeatTimeRange;

    private Integer repeatCount;

    private LocalDateTime validTime;

    private LocalDateTime noticeTime;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBindNoticeId() {
        return bindNoticeId;
    }

    public void setBindNoticeId(Long bindNoticeId) {
        this.bindNoticeId = bindNoticeId;
    }

    public Integer getRepeatTimeUnit() {
        return repeatTimeUnit;
    }

    public void setRepeatTimeUnit(Integer repeatTimeUnit) {
        this.repeatTimeUnit = repeatTimeUnit;
    }

    public Integer getRepeatTimeRange() {
        return repeatTimeRange;
    }

    public void setRepeatTimeRange(Integer repeatTimeRange) {
        this.repeatTimeRange = repeatTimeRange;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public LocalDateTime getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    public LocalDateTime getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(LocalDateTime noticeTime) {
        this.noticeTime = noticeTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}