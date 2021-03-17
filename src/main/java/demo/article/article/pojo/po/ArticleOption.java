package demo.article.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleOption {
    private Long id;

    private String constantname;

    private String constantvalue;

    private LocalDateTime createtime;

    private Boolean isdelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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