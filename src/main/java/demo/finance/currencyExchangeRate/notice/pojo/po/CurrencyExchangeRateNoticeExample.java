package demo.finance.currencyExchangeRate.notice.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CurrencyExchangeRateNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurrencyExchangeRateNoticeExample() {
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

        public Criteria andMaxRateIsNull() {
            addCriterion("max_rate is null");
            return (Criteria) this;
        }

        public Criteria andMaxRateIsNotNull() {
            addCriterion("max_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMaxRateEqualTo(BigDecimal value) {
            addCriterion("max_rate =", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateNotEqualTo(BigDecimal value) {
            addCriterion("max_rate <>", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateGreaterThan(BigDecimal value) {
            addCriterion("max_rate >", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_rate >=", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateLessThan(BigDecimal value) {
            addCriterion("max_rate <", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_rate <=", value, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateIn(List<BigDecimal> values) {
            addCriterion("max_rate in", values, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateNotIn(List<BigDecimal> values) {
            addCriterion("max_rate not in", values, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_rate between", value1, value2, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_rate not between", value1, value2, "maxRate");
            return (Criteria) this;
        }

        public Criteria andMinRateIsNull() {
            addCriterion("min_rate is null");
            return (Criteria) this;
        }

        public Criteria andMinRateIsNotNull() {
            addCriterion("min_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMinRateEqualTo(BigDecimal value) {
            addCriterion("min_rate =", value, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateNotEqualTo(BigDecimal value) {
            addCriterion("min_rate <>", value, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateGreaterThan(BigDecimal value) {
            addCriterion("min_rate >", value, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_rate >=", value, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateLessThan(BigDecimal value) {
            addCriterion("min_rate <", value, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_rate <=", value, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateIn(List<BigDecimal> values) {
            addCriterion("min_rate in", values, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateNotIn(List<BigDecimal> values) {
            addCriterion("min_rate not in", values, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_rate between", value1, value2, "minRate");
            return (Criteria) this;
        }

        public Criteria andMinRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_rate not between", value1, value2, "minRate");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchIsNull() {
            addCriterion("time_unit_of_data_watch is null");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchIsNotNull() {
            addCriterion("time_unit_of_data_watch is not null");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchEqualTo(Integer value) {
            addCriterion("time_unit_of_data_watch =", value, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchNotEqualTo(Integer value) {
            addCriterion("time_unit_of_data_watch <>", value, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchGreaterThan(Integer value) {
            addCriterion("time_unit_of_data_watch >", value, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_unit_of_data_watch >=", value, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchLessThan(Integer value) {
            addCriterion("time_unit_of_data_watch <", value, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchLessThanOrEqualTo(Integer value) {
            addCriterion("time_unit_of_data_watch <=", value, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchIn(List<Integer> values) {
            addCriterion("time_unit_of_data_watch in", values, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchNotIn(List<Integer> values) {
            addCriterion("time_unit_of_data_watch not in", values, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchBetween(Integer value1, Integer value2) {
            addCriterion("time_unit_of_data_watch between", value1, value2, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfDataWatchNotBetween(Integer value1, Integer value2) {
            addCriterion("time_unit_of_data_watch not between", value1, value2, "timeUnitOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchIsNull() {
            addCriterion("time_range_of_data_watch is null");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchIsNotNull() {
            addCriterion("time_range_of_data_watch is not null");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchEqualTo(Integer value) {
            addCriterion("time_range_of_data_watch =", value, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchNotEqualTo(Integer value) {
            addCriterion("time_range_of_data_watch <>", value, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchGreaterThan(Integer value) {
            addCriterion("time_range_of_data_watch >", value, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_range_of_data_watch >=", value, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchLessThan(Integer value) {
            addCriterion("time_range_of_data_watch <", value, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchLessThanOrEqualTo(Integer value) {
            addCriterion("time_range_of_data_watch <=", value, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchIn(List<Integer> values) {
            addCriterion("time_range_of_data_watch in", values, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchNotIn(List<Integer> values) {
            addCriterion("time_range_of_data_watch not in", values, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchBetween(Integer value1, Integer value2) {
            addCriterion("time_range_of_data_watch between", value1, value2, "timeRangeOfDataWatch");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfDataWatchNotBetween(Integer value1, Integer value2) {
            addCriterion("time_range_of_data_watch not between", value1, value2, "timeRangeOfDataWatch");
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

        public Criteria andTelegramChatIdIsNull() {
            addCriterion("telegram_chat_id is null");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdIsNotNull() {
            addCriterion("telegram_chat_id is not null");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdEqualTo(Long value) {
            addCriterion("telegram_chat_id =", value, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdNotEqualTo(Long value) {
            addCriterion("telegram_chat_id <>", value, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdGreaterThan(Long value) {
            addCriterion("telegram_chat_id >", value, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdGreaterThanOrEqualTo(Long value) {
            addCriterion("telegram_chat_id >=", value, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdLessThan(Long value) {
            addCriterion("telegram_chat_id <", value, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdLessThanOrEqualTo(Long value) {
            addCriterion("telegram_chat_id <=", value, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdIn(List<Long> values) {
            addCriterion("telegram_chat_id in", values, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdNotIn(List<Long> values) {
            addCriterion("telegram_chat_id not in", values, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdBetween(Long value1, Long value2) {
            addCriterion("telegram_chat_id between", value1, value2, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramChatIdNotBetween(Long value1, Long value2) {
            addCriterion("telegram_chat_id not between", value1, value2, "telegramChatId");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameIsNull() {
            addCriterion("telegram_bot_name is null");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameIsNotNull() {
            addCriterion("telegram_bot_name is not null");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameEqualTo(String value) {
            addCriterion("telegram_bot_name =", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameNotEqualTo(String value) {
            addCriterion("telegram_bot_name <>", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameGreaterThan(String value) {
            addCriterion("telegram_bot_name >", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameGreaterThanOrEqualTo(String value) {
            addCriterion("telegram_bot_name >=", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameLessThan(String value) {
            addCriterion("telegram_bot_name <", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameLessThanOrEqualTo(String value) {
            addCriterion("telegram_bot_name <=", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameLike(String value) {
            addCriterion("telegram_bot_name like", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameNotLike(String value) {
            addCriterion("telegram_bot_name not like", value, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameIn(List<String> values) {
            addCriterion("telegram_bot_name in", values, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameNotIn(List<String> values) {
            addCriterion("telegram_bot_name not in", values, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameBetween(String value1, String value2) {
            addCriterion("telegram_bot_name between", value1, value2, "telegramBotName");
            return (Criteria) this;
        }

        public Criteria andTelegramBotNameNotBetween(String value1, String value2) {
            addCriterion("telegram_bot_name not between", value1, value2, "telegramBotName");
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

        public Criteria andTimeUnitOfNoticeIntervalIsNull() {
            addCriterion("time_unit_of_notice_interval is null");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalIsNotNull() {
            addCriterion("time_unit_of_notice_interval is not null");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalEqualTo(Integer value) {
            addCriterion("time_unit_of_notice_interval =", value, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalNotEqualTo(Integer value) {
            addCriterion("time_unit_of_notice_interval <>", value, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalGreaterThan(Integer value) {
            addCriterion("time_unit_of_notice_interval >", value, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_unit_of_notice_interval >=", value, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalLessThan(Integer value) {
            addCriterion("time_unit_of_notice_interval <", value, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalLessThanOrEqualTo(Integer value) {
            addCriterion("time_unit_of_notice_interval <=", value, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalIn(List<Integer> values) {
            addCriterion("time_unit_of_notice_interval in", values, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalNotIn(List<Integer> values) {
            addCriterion("time_unit_of_notice_interval not in", values, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalBetween(Integer value1, Integer value2) {
            addCriterion("time_unit_of_notice_interval between", value1, value2, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeUnitOfNoticeIntervalNotBetween(Integer value1, Integer value2) {
            addCriterion("time_unit_of_notice_interval not between", value1, value2, "timeUnitOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalIsNull() {
            addCriterion("time_range_of_notice_interval is null");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalIsNotNull() {
            addCriterion("time_range_of_notice_interval is not null");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalEqualTo(Integer value) {
            addCriterion("time_range_of_notice_interval =", value, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalNotEqualTo(Integer value) {
            addCriterion("time_range_of_notice_interval <>", value, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalGreaterThan(Integer value) {
            addCriterion("time_range_of_notice_interval >", value, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalGreaterThanOrEqualTo(Integer value) {
            addCriterion("time_range_of_notice_interval >=", value, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalLessThan(Integer value) {
            addCriterion("time_range_of_notice_interval <", value, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalLessThanOrEqualTo(Integer value) {
            addCriterion("time_range_of_notice_interval <=", value, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalIn(List<Integer> values) {
            addCriterion("time_range_of_notice_interval in", values, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalNotIn(List<Integer> values) {
            addCriterion("time_range_of_notice_interval not in", values, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalBetween(Integer value1, Integer value2) {
            addCriterion("time_range_of_notice_interval between", value1, value2, "timeRangeOfNoticeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeRangeOfNoticeIntervalNotBetween(Integer value1, Integer value2) {
            addCriterion("time_range_of_notice_interval not between", value1, value2, "timeRangeOfNoticeInterval");
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

        public Criteria andNextNoticeTimeIsNull() {
            addCriterion("next_notice_time is null");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeIsNotNull() {
            addCriterion("next_notice_time is not null");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeEqualTo(LocalDateTime value) {
            addCriterion("next_notice_time =", value, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeNotEqualTo(LocalDateTime value) {
            addCriterion("next_notice_time <>", value, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeGreaterThan(LocalDateTime value) {
            addCriterion("next_notice_time >", value, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("next_notice_time >=", value, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeLessThan(LocalDateTime value) {
            addCriterion("next_notice_time <", value, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("next_notice_time <=", value, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeIn(List<LocalDateTime> values) {
            addCriterion("next_notice_time in", values, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeNotIn(List<LocalDateTime> values) {
            addCriterion("next_notice_time not in", values, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("next_notice_time between", value1, value2, "nextNoticeTime");
            return (Criteria) this;
        }

        public Criteria andNextNoticeTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("next_notice_time not between", value1, value2, "nextNoticeTime");
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