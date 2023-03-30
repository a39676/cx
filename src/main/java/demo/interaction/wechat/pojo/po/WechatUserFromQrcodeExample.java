package demo.interaction.wechat.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class WechatUserFromQrcodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WechatUserFromQrcodeExample() {
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

        public Criteria andWechatUserIdIsNull() {
            addCriterion("wechat_user_id is null");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdIsNotNull() {
            addCriterion("wechat_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdEqualTo(Long value) {
            addCriterion("wechat_user_id =", value, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdNotEqualTo(Long value) {
            addCriterion("wechat_user_id <>", value, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdGreaterThan(Long value) {
            addCriterion("wechat_user_id >", value, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("wechat_user_id >=", value, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdLessThan(Long value) {
            addCriterion("wechat_user_id <", value, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdLessThanOrEqualTo(Long value) {
            addCriterion("wechat_user_id <=", value, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdIn(List<Long> values) {
            addCriterion("wechat_user_id in", values, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdNotIn(List<Long> values) {
            addCriterion("wechat_user_id not in", values, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdBetween(Long value1, Long value2) {
            addCriterion("wechat_user_id between", value1, value2, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andWechatUserIdNotBetween(Long value1, Long value2) {
            addCriterion("wechat_user_id not between", value1, value2, "wechatUserId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIsNull() {
            addCriterion("qrcode_id is null");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIsNotNull() {
            addCriterion("qrcode_id is not null");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdEqualTo(Integer value) {
            addCriterion("qrcode_id =", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotEqualTo(Integer value) {
            addCriterion("qrcode_id <>", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdGreaterThan(Integer value) {
            addCriterion("qrcode_id >", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("qrcode_id >=", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdLessThan(Integer value) {
            addCriterion("qrcode_id <", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdLessThanOrEqualTo(Integer value) {
            addCriterion("qrcode_id <=", value, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdIn(List<Integer> values) {
            addCriterion("qrcode_id in", values, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotIn(List<Integer> values) {
            addCriterion("qrcode_id not in", values, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_id between", value1, value2, "qrcodeId");
            return (Criteria) this;
        }

        public Criteria andQrcodeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("qrcode_id not between", value1, value2, "qrcodeId");
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