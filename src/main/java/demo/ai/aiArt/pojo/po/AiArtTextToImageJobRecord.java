package demo.ai.aiArt.pojo.po;

import java.time.LocalDateTime;

public class AiArtTextToImageJobRecord {
    private Long id;

    private Long aiUserId;

    private LocalDateTime createTime;

    private Boolean isDelete;

    private Byte jobStatus;

    private Integer runCount;

    private Boolean isFromApi;

    private Boolean isFreeJob;

    private Boolean hasReview;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAiUserId() {
        return aiUserId;
    }

    public void setAiUserId(Long aiUserId) {
        this.aiUserId = aiUserId;
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

    public Byte getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Byte jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getRunCount() {
        return runCount;
    }

    public void setRunCount(Integer runCount) {
        this.runCount = runCount;
    }

    public Boolean getIsFromApi() {
        return isFromApi;
    }

    public void setIsFromApi(Boolean isFromApi) {
        this.isFromApi = isFromApi;
    }

    public Boolean getIsFreeJob() {
        return isFreeJob;
    }

    public void setIsFreeJob(Boolean isFreeJob) {
        this.isFreeJob = isFreeJob;
    }

    public Boolean getHasReview() {
        return hasReview;
    }

    public void setHasReview(Boolean hasReview) {
        this.hasReview = hasReview;
    }
}