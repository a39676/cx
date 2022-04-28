package demo.finance.cryptoCoin.mining.pojo.po;

import java.time.LocalDateTime;

public class CryptoCoinMiningMachine {
    private Long id;

    private Long cryptoId;

    private String machineName;

    private LocalDateTime createTime;

    private LocalDateTime udpateTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Long cryptoId) {
        this.cryptoId = cryptoId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName == null ? null : machineName.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUdpateTime() {
        return udpateTime;
    }

    public void setUdpateTime(LocalDateTime udpateTime) {
        this.udpateTime = udpateTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}