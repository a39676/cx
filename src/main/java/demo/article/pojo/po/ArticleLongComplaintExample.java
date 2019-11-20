package demo.article.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleLongComplaintExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleLongComplaintExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andArticleIdIsNull() {
            addCriterion("article_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleIdIsNotNull() {
            addCriterion("article_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleIdEqualTo(Long value) {
            addCriterion("article_id =", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotEqualTo(Long value) {
            addCriterion("article_id <>", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThan(Long value) {
            addCriterion("article_id >", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("article_id >=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThan(Long value) {
            addCriterion("article_id <", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdLessThanOrEqualTo(Long value) {
            addCriterion("article_id <=", value, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdIn(List<Long> values) {
            addCriterion("article_id in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotIn(List<Long> values) {
            addCriterion("article_id not in", values, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdBetween(Long value1, Long value2) {
            addCriterion("article_id between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleIdNotBetween(Long value1, Long value2) {
            addCriterion("article_id not between", value1, value2, "articleId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdIsNull() {
            addCriterion("article_creator_id is null");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdIsNotNull() {
            addCriterion("article_creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdEqualTo(Long value) {
            addCriterion("article_creator_id =", value, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdNotEqualTo(Long value) {
            addCriterion("article_creator_id <>", value, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdGreaterThan(Long value) {
            addCriterion("article_creator_id >", value, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("article_creator_id >=", value, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdLessThan(Long value) {
            addCriterion("article_creator_id <", value, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdLessThanOrEqualTo(Long value) {
            addCriterion("article_creator_id <=", value, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdIn(List<Long> values) {
            addCriterion("article_creator_id in", values, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdNotIn(List<Long> values) {
            addCriterion("article_creator_id not in", values, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdBetween(Long value1, Long value2) {
            addCriterion("article_creator_id between", value1, value2, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andArticleCreatorIdNotBetween(Long value1, Long value2) {
            addCriterion("article_creator_id not between", value1, value2, "articleCreatorId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdIsNull() {
            addCriterion("complaint_user_id is null");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdIsNotNull() {
            addCriterion("complaint_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdEqualTo(Long value) {
            addCriterion("complaint_user_id =", value, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdNotEqualTo(Long value) {
            addCriterion("complaint_user_id <>", value, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdGreaterThan(Long value) {
            addCriterion("complaint_user_id >", value, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("complaint_user_id >=", value, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdLessThan(Long value) {
            addCriterion("complaint_user_id <", value, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdLessThanOrEqualTo(Long value) {
            addCriterion("complaint_user_id <=", value, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdIn(List<Long> values) {
            addCriterion("complaint_user_id in", values, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdNotIn(List<Long> values) {
            addCriterion("complaint_user_id not in", values, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdBetween(Long value1, Long value2) {
            addCriterion("complaint_user_id between", value1, value2, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andComplaintUserIdNotBetween(Long value1, Long value2) {
            addCriterion("complaint_user_id not between", value1, value2, "complaintUserId");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIsNull() {
            addCriterion("article_title is null");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIsNotNull() {
            addCriterion("article_title is not null");
            return (Criteria) this;
        }

        public Criteria andArticleTitleEqualTo(String value) {
            addCriterion("article_title =", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotEqualTo(String value) {
            addCriterion("article_title <>", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleGreaterThan(String value) {
            addCriterion("article_title >", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleGreaterThanOrEqualTo(String value) {
            addCriterion("article_title >=", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLessThan(String value) {
            addCriterion("article_title <", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLessThanOrEqualTo(String value) {
            addCriterion("article_title <=", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleLike(String value) {
            addCriterion("article_title like", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotLike(String value) {
            addCriterion("article_title not like", value, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleIn(List<String> values) {
            addCriterion("article_title in", values, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotIn(List<String> values) {
            addCriterion("article_title not in", values, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleBetween(String value1, String value2) {
            addCriterion("article_title between", value1, value2, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andArticleTitleNotBetween(String value1, String value2) {
            addCriterion("article_title not between", value1, value2, "articleTitle");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonIsNull() {
            addCriterion("complaint_reason is null");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonIsNotNull() {
            addCriterion("complaint_reason is not null");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonEqualTo(String value) {
            addCriterion("complaint_reason =", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonNotEqualTo(String value) {
            addCriterion("complaint_reason <>", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonGreaterThan(String value) {
            addCriterion("complaint_reason >", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonGreaterThanOrEqualTo(String value) {
            addCriterion("complaint_reason >=", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonLessThan(String value) {
            addCriterion("complaint_reason <", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonLessThanOrEqualTo(String value) {
            addCriterion("complaint_reason <=", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonLike(String value) {
            addCriterion("complaint_reason like", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonNotLike(String value) {
            addCriterion("complaint_reason not like", value, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonIn(List<String> values) {
            addCriterion("complaint_reason in", values, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonNotIn(List<String> values) {
            addCriterion("complaint_reason not in", values, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonBetween(String value1, String value2) {
            addCriterion("complaint_reason between", value1, value2, "complaintReason");
            return (Criteria) this;
        }

        public Criteria andComplaintReasonNotBetween(String value1, String value2) {
            addCriterion("complaint_reason not between", value1, value2, "complaintReason");
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