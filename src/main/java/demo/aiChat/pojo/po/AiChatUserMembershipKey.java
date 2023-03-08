package demo.aiChat.pojo.po;

public class AiChatUserMembershipKey {
    private Long aiChatUserId;

    private Integer membershipLevel;

    public Long getAiChatUserId() {
        return aiChatUserId;
    }

    public void setAiChatUserId(Long aiChatUserId) {
        this.aiChatUserId = aiChatUserId;
    }

    public Integer getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(Integer membershipLevel) {
        this.membershipLevel = membershipLevel;
    }
}