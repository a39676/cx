package demo.ai.aiChat.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AiChatApiKeyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AiChatApiKeyExample() {
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

        public Criteria andApiKeyDecryptIsNull() {
            addCriterion("api_key_decrypt is null");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptIsNotNull() {
            addCriterion("api_key_decrypt is not null");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptEqualTo(Long value) {
            addCriterion("api_key_decrypt =", value, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptNotEqualTo(Long value) {
            addCriterion("api_key_decrypt <>", value, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptGreaterThan(Long value) {
            addCriterion("api_key_decrypt >", value, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptGreaterThanOrEqualTo(Long value) {
            addCriterion("api_key_decrypt >=", value, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptLessThan(Long value) {
            addCriterion("api_key_decrypt <", value, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptLessThanOrEqualTo(Long value) {
            addCriterion("api_key_decrypt <=", value, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptIn(List<Long> values) {
            addCriterion("api_key_decrypt in", values, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptNotIn(List<Long> values) {
            addCriterion("api_key_decrypt not in", values, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptBetween(Long value1, Long value2) {
            addCriterion("api_key_decrypt between", value1, value2, "apiKeyDecrypt");
            return (Criteria) this;
        }

        public Criteria andApiKeyDecryptNotBetween(Long value1, Long value2) {
            addCriterion("api_key_decrypt not between", value1, value2, "apiKeyDecrypt");
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

        public Criteria andLastUsedTimeIsNull() {
            addCriterion("last_used_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeIsNotNull() {
            addCriterion("last_used_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeEqualTo(LocalDateTime value) {
            addCriterion("last_used_time =", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeNotEqualTo(LocalDateTime value) {
            addCriterion("last_used_time <>", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeGreaterThan(LocalDateTime value) {
            addCriterion("last_used_time >", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_used_time >=", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeLessThan(LocalDateTime value) {
            addCriterion("last_used_time <", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_used_time <=", value, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeIn(List<LocalDateTime> values) {
            addCriterion("last_used_time in", values, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeNotIn(List<LocalDateTime> values) {
            addCriterion("last_used_time not in", values, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_used_time between", value1, value2, "lastUsedTime");
            return (Criteria) this;
        }

        public Criteria andLastUsedTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_used_time not between", value1, value2, "lastUsedTime");
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