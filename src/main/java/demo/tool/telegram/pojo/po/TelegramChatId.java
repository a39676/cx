package demo.tool.telegram.pojo.po;

import java.time.LocalDateTime;

public class TelegramChatId {
    private Long id;

    private String chatId;

    private String chatUserName;

    private LocalDateTime createtime;

    private Boolean isdelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId == null ? null : chatId.trim();
    }

    public String getChatUserName() {
        return chatUserName;
    }

    public void setChatUserName(String chatUserName) {
        this.chatUserName = chatUserName == null ? null : chatUserName.trim();
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