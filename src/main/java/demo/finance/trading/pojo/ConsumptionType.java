package demo.finance.trading.pojo;

public class ConsumptionType {
    private Integer consumptionType;

    private String consumptionName;

    public Integer getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(Integer consumptionType) {
        this.consumptionType = consumptionType;
    }

    public String getConsumptionName() {
        return consumptionName;
    }

    public void setConsumptionName(String consumptionName) {
        this.consumptionName = consumptionName == null ? null : consumptionName.trim();
    }
}