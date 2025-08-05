package demo.tool.temuAgent.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemuAgentProductFlowHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TemuAgentProductFlowHistoryExample() {
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

        public Criteria andModelIdIsNull() {
            addCriterion("model_id is null");
            return (Criteria) this;
        }

        public Criteria andModelIdIsNotNull() {
            addCriterion("model_id is not null");
            return (Criteria) this;
        }

        public Criteria andModelIdEqualTo(Long value) {
            addCriterion("model_id =", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotEqualTo(Long value) {
            addCriterion("model_id <>", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdGreaterThan(Long value) {
            addCriterion("model_id >", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdGreaterThanOrEqualTo(Long value) {
            addCriterion("model_id >=", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdLessThan(Long value) {
            addCriterion("model_id <", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdLessThanOrEqualTo(Long value) {
            addCriterion("model_id <=", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdIn(List<Long> values) {
            addCriterion("model_id in", values, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotIn(List<Long> values) {
            addCriterion("model_id not in", values, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdBetween(Long value1, Long value2) {
            addCriterion("model_id between", value1, value2, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotBetween(Long value1, Long value2) {
            addCriterion("model_id not between", value1, value2, "modelId");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeIsNull() {
            addCriterion("flow_type_code is null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeIsNotNull() {
            addCriterion("flow_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeEqualTo(Integer value) {
            addCriterion("flow_type_code =", value, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeNotEqualTo(Integer value) {
            addCriterion("flow_type_code <>", value, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeGreaterThan(Integer value) {
            addCriterion("flow_type_code >", value, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("flow_type_code >=", value, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeLessThan(Integer value) {
            addCriterion("flow_type_code <", value, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeLessThanOrEqualTo(Integer value) {
            addCriterion("flow_type_code <=", value, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeIn(List<Integer> values) {
            addCriterion("flow_type_code in", values, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeNotIn(List<Integer> values) {
            addCriterion("flow_type_code not in", values, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeBetween(Integer value1, Integer value2) {
            addCriterion("flow_type_code between", value1, value2, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowTypeCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("flow_type_code not between", value1, value2, "flowTypeCode");
            return (Criteria) this;
        }

        public Criteria andFlowCountingIsNull() {
            addCriterion("flow_counting is null");
            return (Criteria) this;
        }

        public Criteria andFlowCountingIsNotNull() {
            addCriterion("flow_counting is not null");
            return (Criteria) this;
        }

        public Criteria andFlowCountingEqualTo(Integer value) {
            addCriterion("flow_counting =", value, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingNotEqualTo(Integer value) {
            addCriterion("flow_counting <>", value, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingGreaterThan(Integer value) {
            addCriterion("flow_counting >", value, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("flow_counting >=", value, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingLessThan(Integer value) {
            addCriterion("flow_counting <", value, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingLessThanOrEqualTo(Integer value) {
            addCriterion("flow_counting <=", value, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingIn(List<Integer> values) {
            addCriterion("flow_counting in", values, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingNotIn(List<Integer> values) {
            addCriterion("flow_counting not in", values, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingBetween(Integer value1, Integer value2) {
            addCriterion("flow_counting between", value1, value2, "flowCounting");
            return (Criteria) this;
        }

        public Criteria andFlowCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("flow_counting not between", value1, value2, "flowCounting");
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