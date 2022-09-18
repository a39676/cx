package demo.finance.currencyExchangeRate.data.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CurrencyExchangeRate1dayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurrencyExchangeRate1dayExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromIsNull() {
            addCriterion("currency_from is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromIsNotNull() {
            addCriterion("currency_from is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromEqualTo(Integer value) {
            addCriterion("currency_from =", value, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromNotEqualTo(Integer value) {
            addCriterion("currency_from <>", value, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromGreaterThan(Integer value) {
            addCriterion("currency_from >", value, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("currency_from >=", value, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromLessThan(Integer value) {
            addCriterion("currency_from <", value, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromLessThanOrEqualTo(Integer value) {
            addCriterion("currency_from <=", value, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromIn(List<Integer> values) {
            addCriterion("currency_from in", values, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromNotIn(List<Integer> values) {
            addCriterion("currency_from not in", values, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromBetween(Integer value1, Integer value2) {
            addCriterion("currency_from between", value1, value2, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyFromNotBetween(Integer value1, Integer value2) {
            addCriterion("currency_from not between", value1, value2, "currencyFrom");
            return (Criteria) this;
        }

        public Criteria andCurrencyToIsNull() {
            addCriterion("currency_to is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyToIsNotNull() {
            addCriterion("currency_to is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyToEqualTo(Integer value) {
            addCriterion("currency_to =", value, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToNotEqualTo(Integer value) {
            addCriterion("currency_to <>", value, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToGreaterThan(Integer value) {
            addCriterion("currency_to >", value, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToGreaterThanOrEqualTo(Integer value) {
            addCriterion("currency_to >=", value, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToLessThan(Integer value) {
            addCriterion("currency_to <", value, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToLessThanOrEqualTo(Integer value) {
            addCriterion("currency_to <=", value, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToIn(List<Integer> values) {
            addCriterion("currency_to in", values, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToNotIn(List<Integer> values) {
            addCriterion("currency_to not in", values, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToBetween(Integer value1, Integer value2) {
            addCriterion("currency_to between", value1, value2, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andCurrencyToNotBetween(Integer value1, Integer value2) {
            addCriterion("currency_to not between", value1, value2, "currencyTo");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceIsNull() {
            addCriterion("sell_avg_price is null");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceIsNotNull() {
            addCriterion("sell_avg_price is not null");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceEqualTo(BigDecimal value) {
            addCriterion("sell_avg_price =", value, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceNotEqualTo(BigDecimal value) {
            addCriterion("sell_avg_price <>", value, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceGreaterThan(BigDecimal value) {
            addCriterion("sell_avg_price >", value, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sell_avg_price >=", value, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceLessThan(BigDecimal value) {
            addCriterion("sell_avg_price <", value, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sell_avg_price <=", value, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceIn(List<BigDecimal> values) {
            addCriterion("sell_avg_price in", values, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceNotIn(List<BigDecimal> values) {
            addCriterion("sell_avg_price not in", values, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sell_avg_price between", value1, value2, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellAvgPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sell_avg_price not between", value1, value2, "sellAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceIsNull() {
            addCriterion("buy_avg_price is null");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceIsNotNull() {
            addCriterion("buy_avg_price is not null");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceEqualTo(BigDecimal value) {
            addCriterion("buy_avg_price =", value, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceNotEqualTo(BigDecimal value) {
            addCriterion("buy_avg_price <>", value, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceGreaterThan(BigDecimal value) {
            addCriterion("buy_avg_price >", value, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_avg_price >=", value, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceLessThan(BigDecimal value) {
            addCriterion("buy_avg_price <", value, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_avg_price <=", value, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceIn(List<BigDecimal> values) {
            addCriterion("buy_avg_price in", values, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceNotIn(List<BigDecimal> values) {
            addCriterion("buy_avg_price not in", values, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_avg_price between", value1, value2, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andBuyAvgPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_avg_price not between", value1, value2, "buyAvgPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceIsNull() {
            addCriterion("sell_high_price is null");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceIsNotNull() {
            addCriterion("sell_high_price is not null");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceEqualTo(BigDecimal value) {
            addCriterion("sell_high_price =", value, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceNotEqualTo(BigDecimal value) {
            addCriterion("sell_high_price <>", value, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceGreaterThan(BigDecimal value) {
            addCriterion("sell_high_price >", value, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sell_high_price >=", value, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceLessThan(BigDecimal value) {
            addCriterion("sell_high_price <", value, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sell_high_price <=", value, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceIn(List<BigDecimal> values) {
            addCriterion("sell_high_price in", values, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceNotIn(List<BigDecimal> values) {
            addCriterion("sell_high_price not in", values, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sell_high_price between", value1, value2, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellHighPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sell_high_price not between", value1, value2, "sellHighPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceIsNull() {
            addCriterion("sell_low_price is null");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceIsNotNull() {
            addCriterion("sell_low_price is not null");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceEqualTo(BigDecimal value) {
            addCriterion("sell_low_price =", value, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceNotEqualTo(BigDecimal value) {
            addCriterion("sell_low_price <>", value, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceGreaterThan(BigDecimal value) {
            addCriterion("sell_low_price >", value, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sell_low_price >=", value, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceLessThan(BigDecimal value) {
            addCriterion("sell_low_price <", value, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sell_low_price <=", value, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceIn(List<BigDecimal> values) {
            addCriterion("sell_low_price in", values, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceNotIn(List<BigDecimal> values) {
            addCriterion("sell_low_price not in", values, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sell_low_price between", value1, value2, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andSellLowPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sell_low_price not between", value1, value2, "sellLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceIsNull() {
            addCriterion("buy_high_price is null");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceIsNotNull() {
            addCriterion("buy_high_price is not null");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceEqualTo(BigDecimal value) {
            addCriterion("buy_high_price =", value, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceNotEqualTo(BigDecimal value) {
            addCriterion("buy_high_price <>", value, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceGreaterThan(BigDecimal value) {
            addCriterion("buy_high_price >", value, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_high_price >=", value, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceLessThan(BigDecimal value) {
            addCriterion("buy_high_price <", value, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_high_price <=", value, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceIn(List<BigDecimal> values) {
            addCriterion("buy_high_price in", values, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceNotIn(List<BigDecimal> values) {
            addCriterion("buy_high_price not in", values, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_high_price between", value1, value2, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyHighPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_high_price not between", value1, value2, "buyHighPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceIsNull() {
            addCriterion("buy_low_price is null");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceIsNotNull() {
            addCriterion("buy_low_price is not null");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceEqualTo(BigDecimal value) {
            addCriterion("buy_low_price =", value, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceNotEqualTo(BigDecimal value) {
            addCriterion("buy_low_price <>", value, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceGreaterThan(BigDecimal value) {
            addCriterion("buy_low_price >", value, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_low_price >=", value, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceLessThan(BigDecimal value) {
            addCriterion("buy_low_price <", value, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("buy_low_price <=", value, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceIn(List<BigDecimal> values) {
            addCriterion("buy_low_price in", values, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceNotIn(List<BigDecimal> values) {
            addCriterion("buy_low_price not in", values, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_low_price between", value1, value2, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andBuyLowPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("buy_low_price not between", value1, value2, "buyLowPrice");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(LocalDateTime value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(LocalDateTime value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(LocalDateTime value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(LocalDateTime value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<LocalDateTime> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<LocalDateTime> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(LocalDateTime value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(LocalDateTime value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(LocalDateTime value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(LocalDateTime value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<LocalDateTime> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<LocalDateTime> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}