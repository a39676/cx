package demo.finance.cryptoCoin.notice.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CryptoCoinPriceNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CryptoCoinPriceNoticeExample() {
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

        public Criteria andCoinTypeIsNull() {
            addCriterion("coin_type is null");
            return (Criteria) this;
        }

        public Criteria andCoinTypeIsNotNull() {
            addCriterion("coin_type is not null");
            return (Criteria) this;
        }

        public Criteria andCoinTypeEqualTo(Integer value) {
            addCriterion("coin_type =", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotEqualTo(Integer value) {
            addCriterion("coin_type <>", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeGreaterThan(Integer value) {
            addCriterion("coin_type >", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("coin_type >=", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeLessThan(Integer value) {
            addCriterion("coin_type <", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeLessThanOrEqualTo(Integer value) {
            addCriterion("coin_type <=", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeIn(List<Integer> values) {
            addCriterion("coin_type in", values, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotIn(List<Integer> values) {
            addCriterion("coin_type not in", values, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeBetween(Integer value1, Integer value2) {
            addCriterion("coin_type between", value1, value2, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("coin_type not between", value1, value2, "coinType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNull() {
            addCriterion("currency_type is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNotNull() {
            addCriterion("currency_type is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeEqualTo(Integer value) {
            addCriterion("currency_type =", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotEqualTo(Integer value) {
            addCriterion("currency_type <>", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThan(Integer value) {
            addCriterion("currency_type >", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("currency_type >=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThan(Integer value) {
            addCriterion("currency_type <", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("currency_type <=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIn(List<Integer> values) {
            addCriterion("currency_type in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotIn(List<Integer> values) {
            addCriterion("currency_type not in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeBetween(Integer value1, Integer value2) {
            addCriterion("currency_type between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("currency_type not between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andMaxPriceIsNull() {
            addCriterion("max_price is null");
            return (Criteria) this;
        }

        public Criteria andMaxPriceIsNotNull() {
            addCriterion("max_price is not null");
            return (Criteria) this;
        }

        public Criteria andMaxPriceEqualTo(BigDecimal value) {
            addCriterion("max_price =", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceNotEqualTo(BigDecimal value) {
            addCriterion("max_price <>", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceGreaterThan(BigDecimal value) {
            addCriterion("max_price >", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_price >=", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceLessThan(BigDecimal value) {
            addCriterion("max_price <", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_price <=", value, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceIn(List<BigDecimal> values) {
            addCriterion("max_price in", values, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceNotIn(List<BigDecimal> values) {
            addCriterion("max_price not in", values, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_price between", value1, value2, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMaxPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_price not between", value1, value2, "maxPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceIsNull() {
            addCriterion("min_price is null");
            return (Criteria) this;
        }

        public Criteria andMinPriceIsNotNull() {
            addCriterion("min_price is not null");
            return (Criteria) this;
        }

        public Criteria andMinPriceEqualTo(BigDecimal value) {
            addCriterion("min_price =", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceNotEqualTo(BigDecimal value) {
            addCriterion("min_price <>", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceGreaterThan(BigDecimal value) {
            addCriterion("min_price >", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_price >=", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceLessThan(BigDecimal value) {
            addCriterion("min_price <", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_price <=", value, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceIn(List<BigDecimal> values) {
            addCriterion("min_price in", values, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceNotIn(List<BigDecimal> values) {
            addCriterion("min_price not in", values, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_price between", value1, value2, "minPrice");
            return (Criteria) this;
        }

        public Criteria andMinPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_price not between", value1, value2, "minPrice");
            return (Criteria) this;
        }

        public Criteria andTimeUnitIsNull() {
            addCriterion("time_unit is null");
            return (Criteria) this;
        }

        public Criteria andTimeUnitIsNotNull() {
            addCriterion("time_unit is not null");
            return (Criteria) this;
        }

        public Criteria andTimeUnitEqualTo(Integer value) {
            addCriterion("time_unit =", value, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitNotEqualTo(Integer value) {
            addCriterion("time_unit <>", value, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitGreaterThan(Integer value) {
            addCriterion("time_unit >", value, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_unit >=", value, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitLessThan(Integer value) {
            addCriterion("time_unit <", value, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitLessThanOrEqualTo(Integer value) {
            addCriterion("time_unit <=", value, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitIn(List<Integer> values) {
            addCriterion("time_unit in", values, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitNotIn(List<Integer> values) {
            addCriterion("time_unit not in", values, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitBetween(Integer value1, Integer value2) {
            addCriterion("time_unit between", value1, value2, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeUnitNotBetween(Integer value1, Integer value2) {
            addCriterion("time_unit not between", value1, value2, "timeUnit");
            return (Criteria) this;
        }

        public Criteria andTimeRangeIsNull() {
            addCriterion("time_range is null");
            return (Criteria) this;
        }

        public Criteria andTimeRangeIsNotNull() {
            addCriterion("time_range is not null");
            return (Criteria) this;
        }

        public Criteria andTimeRangeEqualTo(Integer value) {
            addCriterion("time_range =", value, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeNotEqualTo(Integer value) {
            addCriterion("time_range <>", value, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeGreaterThan(Integer value) {
            addCriterion("time_range >", value, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_range >=", value, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeLessThan(Integer value) {
            addCriterion("time_range <", value, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeLessThanOrEqualTo(Integer value) {
            addCriterion("time_range <=", value, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeIn(List<Integer> values) {
            addCriterion("time_range in", values, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeNotIn(List<Integer> values) {
            addCriterion("time_range not in", values, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeBetween(Integer value1, Integer value2) {
            addCriterion("time_range between", value1, value2, "timeRange");
            return (Criteria) this;
        }

        public Criteria andTimeRangeNotBetween(Integer value1, Integer value2) {
            addCriterion("time_range not between", value1, value2, "timeRange");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageIsNull() {
            addCriterion("fluctuation_speed_percentage is null");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageIsNotNull() {
            addCriterion("fluctuation_speed_percentage is not null");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageEqualTo(BigDecimal value) {
            addCriterion("fluctuation_speed_percentage =", value, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageNotEqualTo(BigDecimal value) {
            addCriterion("fluctuation_speed_percentage <>", value, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageGreaterThan(BigDecimal value) {
            addCriterion("fluctuation_speed_percentage >", value, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fluctuation_speed_percentage >=", value, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageLessThan(BigDecimal value) {
            addCriterion("fluctuation_speed_percentage <", value, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fluctuation_speed_percentage <=", value, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageIn(List<BigDecimal> values) {
            addCriterion("fluctuation_speed_percentage in", values, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageNotIn(List<BigDecimal> values) {
            addCriterion("fluctuation_speed_percentage not in", values, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fluctuation_speed_percentage between", value1, value2, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andFluctuationSpeedPercentageNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fluctuation_speed_percentage not between", value1, value2, "fluctuationSpeedPercentage");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andNoticeCountIsNull() {
            addCriterion("notice_count is null");
            return (Criteria) this;
        }

        public Criteria andNoticeCountIsNotNull() {
            addCriterion("notice_count is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeCountEqualTo(Integer value) {
            addCriterion("notice_count =", value, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountNotEqualTo(Integer value) {
            addCriterion("notice_count <>", value, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountGreaterThan(Integer value) {
            addCriterion("notice_count >", value, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("notice_count >=", value, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountLessThan(Integer value) {
            addCriterion("notice_count <", value, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountLessThanOrEqualTo(Integer value) {
            addCriterion("notice_count <=", value, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountIn(List<Integer> values) {
            addCriterion("notice_count in", values, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountNotIn(List<Integer> values) {
            addCriterion("notice_count not in", values, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountBetween(Integer value1, Integer value2) {
            addCriterion("notice_count between", value1, value2, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andNoticeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("notice_count not between", value1, value2, "noticeCount");
            return (Criteria) this;
        }

        public Criteria andValidTimeIsNull() {
            addCriterion("valid_time is null");
            return (Criteria) this;
        }

        public Criteria andValidTimeIsNotNull() {
            addCriterion("valid_time is not null");
            return (Criteria) this;
        }

        public Criteria andValidTimeEqualTo(LocalDateTime value) {
            addCriterion("valid_time =", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotEqualTo(LocalDateTime value) {
            addCriterion("valid_time <>", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThan(LocalDateTime value) {
            addCriterion("valid_time >", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("valid_time >=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThan(LocalDateTime value) {
            addCriterion("valid_time <", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("valid_time <=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeIn(List<LocalDateTime> values) {
            addCriterion("valid_time in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotIn(List<LocalDateTime> values) {
            addCriterion("valid_time not in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("valid_time between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("valid_time not between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeIsNull() {
            addCriterion("notice_time is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeIsNotNull() {
            addCriterion("notice_time is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeEqualTo(LocalDateTime value) {
            addCriterion("notice_time =", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeNotEqualTo(LocalDateTime value) {
            addCriterion("notice_time <>", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeGreaterThan(LocalDateTime value) {
            addCriterion("notice_time >", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("notice_time >=", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeLessThan(LocalDateTime value) {
            addCriterion("notice_time <", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("notice_time <=", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeIn(List<LocalDateTime> values) {
            addCriterion("notice_time in", values, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeNotIn(List<LocalDateTime> values) {
            addCriterion("notice_time not in", values, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("notice_time between", value1, value2, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("notice_time not between", value1, value2, "noticeTime");
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