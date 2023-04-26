package demo.ai.aiArt.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AiArtGeneratingRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AiArtGeneratingRecordExample() {
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

        public Criteria andAiUserIdIsNull() {
            addCriterion("ai_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAiUserIdIsNotNull() {
            addCriterion("ai_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAiUserIdEqualTo(Long value) {
            addCriterion("ai_user_id =", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdNotEqualTo(Long value) {
            addCriterion("ai_user_id <>", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdGreaterThan(Long value) {
            addCriterion("ai_user_id >", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ai_user_id >=", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdLessThan(Long value) {
            addCriterion("ai_user_id <", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdLessThanOrEqualTo(Long value) {
            addCriterion("ai_user_id <=", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdIn(List<Long> values) {
            addCriterion("ai_user_id in", values, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdNotIn(List<Long> values) {
            addCriterion("ai_user_id not in", values, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdBetween(Long value1, Long value2) {
            addCriterion("ai_user_id between", value1, value2, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdNotBetween(Long value1, Long value2) {
            addCriterion("ai_user_id not between", value1, value2, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andTokensIsNull() {
            addCriterion("tokens is null");
            return (Criteria) this;
        }

        public Criteria andTokensIsNotNull() {
            addCriterion("tokens is not null");
            return (Criteria) this;
        }

        public Criteria andTokensEqualTo(BigDecimal value) {
            addCriterion("tokens =", value, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensNotEqualTo(BigDecimal value) {
            addCriterion("tokens <>", value, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensGreaterThan(BigDecimal value) {
            addCriterion("tokens >", value, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tokens >=", value, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensLessThan(BigDecimal value) {
            addCriterion("tokens <", value, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tokens <=", value, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensIn(List<BigDecimal> values) {
            addCriterion("tokens in", values, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensNotIn(List<BigDecimal> values) {
            addCriterion("tokens not in", values, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tokens between", value1, value2, "tokens");
            return (Criteria) this;
        }

        public Criteria andTokensNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tokens not between", value1, value2, "tokens");
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

        public Criteria andIsWarningIsNull() {
            addCriterion("is_warning is null");
            return (Criteria) this;
        }

        public Criteria andIsWarningIsNotNull() {
            addCriterion("is_warning is not null");
            return (Criteria) this;
        }

        public Criteria andIsWarningEqualTo(Boolean value) {
            addCriterion("is_warning =", value, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningNotEqualTo(Boolean value) {
            addCriterion("is_warning <>", value, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningGreaterThan(Boolean value) {
            addCriterion("is_warning >", value, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_warning >=", value, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningLessThan(Boolean value) {
            addCriterion("is_warning <", value, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningLessThanOrEqualTo(Boolean value) {
            addCriterion("is_warning <=", value, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningIn(List<Boolean> values) {
            addCriterion("is_warning in", values, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningNotIn(List<Boolean> values) {
            addCriterion("is_warning not in", values, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningBetween(Boolean value1, Boolean value2) {
            addCriterion("is_warning between", value1, value2, "isWarning");
            return (Criteria) this;
        }

        public Criteria andIsWarningNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_warning not between", value1, value2, "isWarning");
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