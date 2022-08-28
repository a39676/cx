package demo.toyParts.vcode.pojo.po;

import java.time.LocalDateTime;

public class VcodeHistory {
    private Long codeId;

    private LocalDateTime visitTime;

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }
}