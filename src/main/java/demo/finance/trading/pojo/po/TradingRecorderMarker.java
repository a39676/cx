package demo.finance.trading.pojo.po;

import java.util.Date;

import demo.config.costom_component.EncryptUtil;

public class TradingRecorderMarker {
    private Long tradingId;

    private Date createTime;

    private String marker;

    public Long getTradingId() {
        return tradingId;
    }

    public void setTradingId(Long tradingId) {
        this.tradingId = tradingId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String tradingRecorder) {
        this.marker = EncryptUtil.Sha1(EncryptUtil.ToMd5String(tradingRecorder));
    }
    
    public boolean checkRemark(TradingRecorder tradingRecorder) {

        if (tradingRecorder == null
                || tradingRecorder.getTradingId() == null
                || marker == null 
                || marker.length() <= 0
                || tradingRecorder.getTradingId().equals(tradingId)
                ) {
            return false;
        }
        
        return marker.equals(EncryptUtil.Sha1(EncryptUtil.ToMd5String(tradingRecorder.getInfos())));
    }
}