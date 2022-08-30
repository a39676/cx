package demo.toyParts.educate.pojo.po;

import java.time.LocalDateTime;

public class StudentParent extends StudentParentKey {
    private LocalDateTime createTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}