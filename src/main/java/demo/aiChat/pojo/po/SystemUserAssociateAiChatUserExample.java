package demo.aiChat.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class SystemUserAssociateAiChatUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SystemUserAssociateAiChatUserExample() {
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

        public Criteria andSystemUserIdIsNull() {
            addCriterion("system_user_id is null");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdIsNotNull() {
            addCriterion("system_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdEqualTo(Long value) {
            addCriterion("system_user_id =", value, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdNotEqualTo(Long value) {
            addCriterion("system_user_id <>", value, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdGreaterThan(Long value) {
            addCriterion("system_user_id >", value, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("system_user_id >=", value, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdLessThan(Long value) {
            addCriterion("system_user_id <", value, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdLessThanOrEqualTo(Long value) {
            addCriterion("system_user_id <=", value, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdIn(List<Long> values) {
            addCriterion("system_user_id in", values, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdNotIn(List<Long> values) {
            addCriterion("system_user_id not in", values, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdBetween(Long value1, Long value2) {
            addCriterion("system_user_id between", value1, value2, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andSystemUserIdNotBetween(Long value1, Long value2) {
            addCriterion("system_user_id not between", value1, value2, "systemUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdIsNull() {
            addCriterion("ai_chat_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdIsNotNull() {
            addCriterion("ai_chat_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdEqualTo(Long value) {
            addCriterion("ai_chat_user_id =", value, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdNotEqualTo(Long value) {
            addCriterion("ai_chat_user_id <>", value, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdGreaterThan(Long value) {
            addCriterion("ai_chat_user_id >", value, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ai_chat_user_id >=", value, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdLessThan(Long value) {
            addCriterion("ai_chat_user_id <", value, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdLessThanOrEqualTo(Long value) {
            addCriterion("ai_chat_user_id <=", value, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdIn(List<Long> values) {
            addCriterion("ai_chat_user_id in", values, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdNotIn(List<Long> values) {
            addCriterion("ai_chat_user_id not in", values, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdBetween(Long value1, Long value2) {
            addCriterion("ai_chat_user_id between", value1, value2, "aiChatUserId");
            return (Criteria) this;
        }

        public Criteria andAiChatUserIdNotBetween(Long value1, Long value2) {
            addCriterion("ai_chat_user_id not between", value1, value2, "aiChatUserId");
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