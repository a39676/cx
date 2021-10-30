package demo.tool.calendarNotice.pojo.po;

import java.time.LocalDateTime;

public class CalendarNotice {
    private Long id;

    private String noticeContent;

    private Boolean needRepeat;

    private Integer repeatTimeUnit;

    private Integer repeatTimeRange;

    private LocalDateTime validTime;

    private LocalDateTime noticeTime;

    private Boolean isLunarCalendar;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent == null ? null : noticeContent.trim();
    }

    public Boolean getNeedRepeat() {
        return needRepeat;
    }

    public void setNeedRepeat(Boolean needRepeat) {
        this.needRepeat = needRepeat;
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

    public Boolean getIsLunarCalendar() {
        return isLunarCalendar;
    }

    public void setIsLunarCalendar(Boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
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