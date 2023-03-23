package demo.aiChat.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AiChatUserMembershipExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AiChatUserMembershipExample() {
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

        public Criteria andMembershipLevelIsNull() {
            addCriterion("membership_level is null");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelIsNotNull() {
            addCriterion("membership_level is not null");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelEqualTo(Long value) {
            addCriterion("membership_level =", value, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelNotEqualTo(Long value) {
            addCriterion("membership_level <>", value, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelGreaterThan(Long value) {
            addCriterion("membership_level >", value, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelGreaterThanOrEqualTo(Long value) {
            addCriterion("membership_level >=", value, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelLessThan(Long value) {
            addCriterion("membership_level <", value, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelLessThanOrEqualTo(Long value) {
            addCriterion("membership_level <=", value, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelIn(List<Long> values) {
            addCriterion("membership_level in", values, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelNotIn(List<Long> values) {
            addCriterion("membership_level not in", values, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelBetween(Long value1, Long value2) {
            addCriterion("membership_level between", value1, value2, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipLevelNotBetween(Long value1, Long value2) {
            addCriterion("membership_level not between", value1, value2, "membershipLevel");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionIsNull() {
            addCriterion("membership_version is null");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionIsNotNull() {
            addCriterion("membership_version is not null");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionEqualTo(Integer value) {
            addCriterion("membership_version =", value, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionNotEqualTo(Integer value) {
            addCriterion("membership_version <>", value, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionGreaterThan(Integer value) {
            addCriterion("membership_version >", value, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("membership_version >=", value, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionLessThan(Integer value) {
            addCriterion("membership_version <", value, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionLessThanOrEqualTo(Integer value) {
            addCriterion("membership_version <=", value, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionIn(List<Integer> values) {
            addCriterion("membership_version in", values, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionNotIn(List<Integer> values) {
            addCriterion("membership_version not in", values, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionBetween(Integer value1, Integer value2) {
            addCriterion("membership_version between", value1, value2, "membershipVersion");
            return (Criteria) this;
        }

        public Criteria andMembershipVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("membership_version not between", value1, value2, "membershipVersion");
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

        public Criteria andExpiredTimeIsNull() {
            addCriterion("expired_time is null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIsNotNull() {
            addCriterion("expired_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeEqualTo(LocalDateTime value) {
            addCriterion("expired_time =", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotEqualTo(LocalDateTime value) {
            addCriterion("expired_time <>", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThan(LocalDateTime value) {
            addCriterion("expired_time >", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("expired_time >=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThan(LocalDateTime value) {
            addCriterion("expired_time <", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("expired_time <=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIn(List<LocalDateTime> values) {
            addCriterion("expired_time in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotIn(List<LocalDateTime> values) {
            addCriterion("expired_time not in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("expired_time between", value1, value2, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("expired_time not between", value1, value2, "expiredTime");
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