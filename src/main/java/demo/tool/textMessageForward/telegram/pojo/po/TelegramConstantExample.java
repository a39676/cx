package demo.tool.textMessageForward.telegram.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TelegramConstantExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TelegramConstantExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andConstantnameIsNull() {
            addCriterion("constantName is null");
            return (Criteria) this;
        }

        public Criteria andConstantnameIsNotNull() {
            addCriterion("constantName is not null");
            return (Criteria) this;
        }

        public Criteria andConstantnameEqualTo(String value) {
            addCriterion("constantName =", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameNotEqualTo(String value) {
            addCriterion("constantName <>", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameGreaterThan(String value) {
            addCriterion("constantName >", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameGreaterThanOrEqualTo(String value) {
            addCriterion("constantName >=", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameLessThan(String value) {
            addCriterion("constantName <", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameLessThanOrEqualTo(String value) {
            addCriterion("constantName <=", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameLike(String value) {
            addCriterion("constantName like", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameNotLike(String value) {
            addCriterion("constantName not like", value, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameIn(List<String> values) {
            addCriterion("constantName in", values, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameNotIn(List<String> values) {
            addCriterion("constantName not in", values, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameBetween(String value1, String value2) {
            addCriterion("constantName between", value1, value2, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantnameNotBetween(String value1, String value2) {
            addCriterion("constantName not between", value1, value2, "constantname");
            return (Criteria) this;
        }

        public Criteria andConstantvalueIsNull() {
            addCriterion("constantValue is null");
            return (Criteria) this;
        }

        public Criteria andConstantvalueIsNotNull() {
            addCriterion("constantValue is not null");
            return (Criteria) this;
        }

        public Criteria andConstantvalueEqualTo(String value) {
            addCriterion("constantValue =", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueNotEqualTo(String value) {
            addCriterion("constantValue <>", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueGreaterThan(String value) {
            addCriterion("constantValue >", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueGreaterThanOrEqualTo(String value) {
            addCriterion("constantValue >=", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueLessThan(String value) {
            addCriterion("constantValue <", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueLessThanOrEqualTo(String value) {
            addCriterion("constantValue <=", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueLike(String value) {
            addCriterion("constantValue like", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueNotLike(String value) {
            addCriterion("constantValue not like", value, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueIn(List<String> values) {
            addCriterion("constantValue in", values, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueNotIn(List<String> values) {
            addCriterion("constantValue not in", values, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueBetween(String value1, String value2) {
            addCriterion("constantValue between", value1, value2, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andConstantvalueNotBetween(String value1, String value2) {
            addCriterion("constantValue not between", value1, value2, "constantvalue");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(LocalDateTime value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(LocalDateTime value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(LocalDateTime value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(LocalDateTime value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<LocalDateTime> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<LocalDateTime> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNull() {
            addCriterion("isDelete is null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIsNotNull() {
            addCriterion("isDelete is not null");
            return (Criteria) this;
        }

        public Criteria andIsdeleteEqualTo(Boolean value) {
            addCriterion("isDelete =", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotEqualTo(Boolean value) {
            addCriterion("isDelete <>", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThan(Boolean value) {
            addCriterion("isDelete >", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isDelete >=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThan(Boolean value) {
            addCriterion("isDelete <", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("isDelete <=", value, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteIn(List<Boolean> values) {
            addCriterion("isDelete in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotIn(List<Boolean> values) {
            addCriterion("isDelete not in", values, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("isDelete between", value1, value2, "isdelete");
            return (Criteria) this;
        }

        public Criteria andIsdeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isDelete not between", value1, value2, "isdelete");
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