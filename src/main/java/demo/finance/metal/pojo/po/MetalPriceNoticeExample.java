package demo.finance.metal.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MetalPriceNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MetalPriceNoticeExample() {
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

        public Criteria andMetalTypeIsNull() {
            addCriterion("metal_type is null");
            return (Criteria) this;
        }

        public Criteria andMetalTypeIsNotNull() {
            addCriterion("metal_type is not null");
            return (Criteria) this;
        }

        public Criteria andMetalTypeEqualTo(Integer value) {
            addCriterion("metal_type =", value, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeNotEqualTo(Integer value) {
            addCriterion("metal_type <>", value, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeGreaterThan(Integer value) {
            addCriterion("metal_type >", value, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("metal_type >=", value, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeLessThan(Integer value) {
            addCriterion("metal_type <", value, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeLessThanOrEqualTo(Integer value) {
            addCriterion("metal_type <=", value, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeIn(List<Integer> values) {
            addCriterion("metal_type in", values, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeNotIn(List<Integer> values) {
            addCriterion("metal_type not in", values, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeBetween(Integer value1, Integer value2) {
            addCriterion("metal_type between", value1, value2, "metalType");
            return (Criteria) this;
        }

        public Criteria andMetalTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("metal_type not between", value1, value2, "metalType");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceIsNull() {
            addCriterion("max_gram_price is null");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceIsNotNull() {
            addCriterion("max_gram_price is not null");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceEqualTo(BigDecimal value) {
            addCriterion("max_gram_price =", value, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceNotEqualTo(BigDecimal value) {
            addCriterion("max_gram_price <>", value, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceGreaterThan(BigDecimal value) {
            addCriterion("max_gram_price >", value, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_gram_price >=", value, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceLessThan(BigDecimal value) {
            addCriterion("max_gram_price <", value, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_gram_price <=", value, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceIn(List<BigDecimal> values) {
            addCriterion("max_gram_price in", values, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceNotIn(List<BigDecimal> values) {
            addCriterion("max_gram_price not in", values, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_gram_price between", value1, value2, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMaxGramPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_gram_price not between", value1, value2, "maxGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceIsNull() {
            addCriterion("min_gram_price is null");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceIsNotNull() {
            addCriterion("min_gram_price is not null");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceEqualTo(BigDecimal value) {
            addCriterion("min_gram_price =", value, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceNotEqualTo(BigDecimal value) {
            addCriterion("min_gram_price <>", value, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceGreaterThan(BigDecimal value) {
            addCriterion("min_gram_price >", value, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_gram_price >=", value, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceLessThan(BigDecimal value) {
            addCriterion("min_gram_price <", value, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_gram_price <=", value, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceIn(List<BigDecimal> values) {
            addCriterion("min_gram_price in", values, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceNotIn(List<BigDecimal> values) {
            addCriterion("min_gram_price not in", values, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_gram_price between", value1, value2, "minGramPrice");
            return (Criteria) this;
        }

        public Criteria andMinGramPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_gram_price not between", value1, value2, "minGramPrice");
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