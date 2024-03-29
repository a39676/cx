package demo.interaction.wechatPay.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WechatPayJsApiHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WechatPayJsApiHistoryExample() {
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

        public Criteria andOpenIdIsNull() {
            addCriterion("open_id is null");
            return (Criteria) this;
        }

        public Criteria andOpenIdIsNotNull() {
            addCriterion("open_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpenIdEqualTo(String value) {
            addCriterion("open_id =", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotEqualTo(String value) {
            addCriterion("open_id <>", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThan(String value) {
            addCriterion("open_id >", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdGreaterThanOrEqualTo(String value) {
            addCriterion("open_id >=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThan(String value) {
            addCriterion("open_id <", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLessThanOrEqualTo(String value) {
            addCriterion("open_id <=", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdLike(String value) {
            addCriterion("open_id like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotLike(String value) {
            addCriterion("open_id not like", value, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdIn(List<String> values) {
            addCriterion("open_id in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotIn(List<String> values) {
            addCriterion("open_id not in", values, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdBetween(String value1, String value2) {
            addCriterion("open_id between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andOpenIdNotBetween(String value1, String value2) {
            addCriterion("open_id not between", value1, value2, "openId");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessIsNull() {
            addCriterion("is_pay_success is null");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessIsNotNull() {
            addCriterion("is_pay_success is not null");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessEqualTo(Boolean value) {
            addCriterion("is_pay_success =", value, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessNotEqualTo(Boolean value) {
            addCriterion("is_pay_success <>", value, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessGreaterThan(Boolean value) {
            addCriterion("is_pay_success >", value, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_pay_success >=", value, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessLessThan(Boolean value) {
            addCriterion("is_pay_success <", value, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessLessThanOrEqualTo(Boolean value) {
            addCriterion("is_pay_success <=", value, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessIn(List<Boolean> values) {
            addCriterion("is_pay_success in", values, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessNotIn(List<Boolean> values) {
            addCriterion("is_pay_success not in", values, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pay_success between", value1, value2, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsPaySuccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pay_success not between", value1, value2, "isPaySuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessIsNull() {
            addCriterion("is_handle_success is null");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessIsNotNull() {
            addCriterion("is_handle_success is not null");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessEqualTo(Boolean value) {
            addCriterion("is_handle_success =", value, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessNotEqualTo(Boolean value) {
            addCriterion("is_handle_success <>", value, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessGreaterThan(Boolean value) {
            addCriterion("is_handle_success >", value, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_handle_success >=", value, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessLessThan(Boolean value) {
            addCriterion("is_handle_success <", value, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessLessThanOrEqualTo(Boolean value) {
            addCriterion("is_handle_success <=", value, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessIn(List<Boolean> values) {
            addCriterion("is_handle_success in", values, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessNotIn(List<Boolean> values) {
            addCriterion("is_handle_success not in", values, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessBetween(Boolean value1, Boolean value2) {
            addCriterion("is_handle_success between", value1, value2, "isHandleSuccess");
            return (Criteria) this;
        }

        public Criteria andIsHandleSuccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_handle_success not between", value1, value2, "isHandleSuccess");
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