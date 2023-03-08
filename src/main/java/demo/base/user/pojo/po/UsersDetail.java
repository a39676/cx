package demo.base.user.pojo.po;

import java.time.LocalDateTime;

public class UsersDetail {
    private Long userId;

    private String nickName;

    private String lastLoginIp;

    private Integer gender;

    private Long qq;

    private String email;

    private Long mobile;

    private LocalDateTime lastLoginTime;

    private LocalDateTime modifyTime;

    private Integer modifyCount;

    private String reservationInformation;

    private String registIp;

    private Integer privateLevel;

    private String headImage;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(Integer modifyCount) {
        this.modifyCount = modifyCount;
    }

    public String getReservationInformation() {
        return reservationInformation;
    }

    public void setReservationInformation(String reservationInformation) {
        this.reservationInformation = reservationInformation == null ? null : reservationInformation.trim();
    }

    public String getRegistIp() {
        return registIp;
    }

    public void setRegistIp(String registIp) {
        this.registIp = registIp == null ? null : registIp.trim();
    }

    public Integer getPrivateLevel() {
        return privateLevel;
    }

    public void setPrivateLevel(Integer privateLevel) {
        this.privateLevel = privateLevel;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }
}