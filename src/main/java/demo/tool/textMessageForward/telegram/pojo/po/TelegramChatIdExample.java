package demo.tool.textMessageForward.telegram.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TelegramChatIdExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TelegramChatIdExample() {
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

        public Criteria andChatIdIsNull() {
            addCriterion("chat_id is null");
            return (Criteria) this;
        }

        public Criteria andChatIdIsNotNull() {
            addCriterion("chat_id is not null");
            return (Criteria) this;
        }

        public Criteria andChatIdEqualTo(String value) {
            addCriterion("chat_id =", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotEqualTo(String value) {
            addCriterion("chat_id <>", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdGreaterThan(String value) {
            addCriterion("chat_id >", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdGreaterThanOrEqualTo(String value) {
            addCriterion("chat_id >=", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLessThan(String value) {
            addCriterion("chat_id <", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLessThanOrEqualTo(String value) {
            addCriterion("chat_id <=", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLike(String value) {
            addCriterion("chat_id like", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotLike(String value) {
            addCriterion("chat_id not like", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdIn(List<String> values) {
            addCriterion("chat_id in", values, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotIn(List<String> values) {
            addCriterion("chat_id not in", values, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdBetween(String value1, String value2) {
            addCriterion("chat_id between", value1, value2, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotBetween(String value1, String value2) {
            addCriterion("chat_id not between", value1, value2, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatUserNameIsNull() {
            addCriterion("chat_user_name is null");
            return (Criteria) this;
        }

        public Criteria andChatUserNameIsNotNull() {
            addCriterion("chat_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andChatUserNameEqualTo(String value) {
            addCriterion("chat_user_name =", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameNotEqualTo(String value) {
            addCriterion("chat_user_name <>", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameGreaterThan(String value) {
            addCriterion("chat_user_name >", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("chat_user_name >=", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameLessThan(String value) {
            addCriterion("chat_user_name <", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameLessThanOrEqualTo(String value) {
            addCriterion("chat_user_name <=", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameLike(String value) {
            addCriterion("chat_user_name like", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameNotLike(String value) {
            addCriterion("chat_user_name not like", value, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameIn(List<String> values) {
            addCriterion("chat_user_name in", values, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameNotIn(List<String> values) {
            addCriterion("chat_user_name not in", values, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameBetween(String value1, String value2) {
            addCriterion("chat_user_name between", value1, value2, "chatUserName");
            return (Criteria) this;
        }

        public Criteria andChatUserNameNotBetween(String value1, String value2) {
            addCriterion("chat_user_name not between", value1, value2, "chatUserName");
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