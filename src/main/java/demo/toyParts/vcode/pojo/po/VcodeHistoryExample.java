package demo.toyParts.vcode.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VcodeHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VcodeHistoryExample() {
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

        public Criteria andCodeIdIsNull() {
            addCriterion("code_id is null");
            return (Criteria) this;
        }

        public Criteria andCodeIdIsNotNull() {
            addCriterion("code_id is not null");
            return (Criteria) this;
        }

        public Criteria andCodeIdEqualTo(Long value) {
            addCriterion("code_id =", value, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdNotEqualTo(Long value) {
            addCriterion("code_id <>", value, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdGreaterThan(Long value) {
            addCriterion("code_id >", value, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("code_id >=", value, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdLessThan(Long value) {
            addCriterion("code_id <", value, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdLessThanOrEqualTo(Long value) {
            addCriterion("code_id <=", value, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdIn(List<Long> values) {
            addCriterion("code_id in", values, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdNotIn(List<Long> values) {
            addCriterion("code_id not in", values, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdBetween(Long value1, Long value2) {
            addCriterion("code_id between", value1, value2, "codeId");
            return (Criteria) this;
        }

        public Criteria andCodeIdNotBetween(Long value1, Long value2) {
            addCriterion("code_id not between", value1, value2, "codeId");
            return (Criteria) this;
        }

        public Criteria andVisitTimeIsNull() {
            addCriterion("visit_time is null");
            return (Criteria) this;
        }

        public Criteria andVisitTimeIsNotNull() {
            addCriterion("visit_time is not null");
            return (Criteria) this;
        }

        public Criteria andVisitTimeEqualTo(LocalDateTime value) {
            addCriterion("visit_time =", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeNotEqualTo(LocalDateTime value) {
            addCriterion("visit_time <>", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeGreaterThan(LocalDateTime value) {
            addCriterion("visit_time >", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("visit_time >=", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeLessThan(LocalDateTime value) {
            addCriterion("visit_time <", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("visit_time <=", value, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeIn(List<LocalDateTime> values) {
            addCriterion("visit_time in", values, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeNotIn(List<LocalDateTime> values) {
            addCriterion("visit_time not in", values, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("visit_time between", value1, value2, "visitTime");
            return (Criteria) this;
        }

        public Criteria andVisitTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("visit_time not between", value1, value2, "visitTime");
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