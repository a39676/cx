package demo.tool.telegram.pojo.po;

import java.time.LocalDateTime;

public class TelegramConstant {
    private Integer id;

    private String constantname;

    private String constantvalue;

    private LocalDateTime createtime;

    private Boolean isdelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConstantname() {
        return constantname;
    }

    public void setConstantname(String constantname) {
        this.constantname = constantname == null ? null : constantname.trim();
    }

    public String getConstantvalue() {
        return constantvalue;
    }

    public void setConstantvalue(String constantvalue) {
        this.constantvalue = constantvalue == null ? null : constantvalue.trim();
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }
}