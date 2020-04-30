package demo.article.articleComment.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleCommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleCommentExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameIsNull() {
            addCriterion("tmp_nick_name is null");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameIsNotNull() {
            addCriterion("tmp_nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameEqualTo(String value) {
            addCriterion("tmp_nick_name =", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameNotEqualTo(String value) {
            addCriterion("tmp_nick_name <>", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameGreaterThan(String value) {
            addCriterion("tmp_nick_name >", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("tmp_nick_name >=", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameLessThan(String value) {
            addCriterion("tmp_nick_name <", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameLessThanOrEqualTo(String value) {
            addCriterion("tmp_nick_name <=", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameLike(String value) {
            addCriterion("tmp_nick_name like", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameNotLike(String value) {
            addCriterion("tmp_nick_name not like", value, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameIn(List<String> values) {
            addCriterion("tmp_nick_name in", values, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameNotIn(List<String> values) {
            addCriterion("tmp_nick_name not in", values, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameBetween(String value1, String value2) {
            addCriterion("tmp_nick_name between", value1, value2, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpNickNameNotBetween(String value1, String value2) {
            addCriterion("tmp_nick_name not between", value1, value2, "tmpNickName");
            return (Criteria) this;
        }

        public Criteria andTmpEmailIsNull() {
            addCriterion("tmp_email is null");
            return (Criteria) this;
        }

        public Criteria andTmpEmailIsNotNull() {
            addCriterion("tmp_email is not null");
            return (Criteria) this;
        }

        public Criteria andTmpEmailEqualTo(String value) {
            addCriterion("tmp_email =", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailNotEqualTo(String value) {
            addCriterion("tmp_email <>", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailGreaterThan(String value) {
            addCriterion("tmp_email >", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailGreaterThanOrEqualTo(String value) {
            addCriterion("tmp_email >=", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailLessThan(String value) {
            addCriterion("tmp_email <", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailLessThanOrEqualTo(String value) {
            addCriterion("tmp_email <=", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailLike(String value) {
            addCriterion("tmp_email like", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailNotLike(String value) {
            addCriterion("tmp_email not like", value, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailIn(List<String> values) {
            addCriterion("tmp_email in", values, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailNotIn(List<String> values) {
            addCriterion("tmp_email not in", values, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailBetween(String value1, String value2) {
            addCriterion("tmp_email between", value1, value2, "tmpEmail");
            return (Criteria) this;
        }

        public Criteria andTmpEmailNotBetween(String value1, String value2) {
            addCriterion("tmp_email not between", value1, value2, "tmpEmail");
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

        public Criteria andPathIsNull() {
            addCriterion("path is null");
            return (Criteria) this;
        }

        public Criteria andPathIsNotNull() {
            addCriterion("path is not null");
            return (Criteria) this;
        }

        public Criteria andPathEqualTo(String value) {
            addCriterion("path =", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotEqualTo(String value) {
            addCriterion("path <>", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThan(String value) {
            addCriterion("path >", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathGreaterThanOrEqualTo(String value) {
            addCriterion("path >=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThan(String value) {
            addCriterion("path <", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLessThanOrEqualTo(String value) {
            addCriterion("path <=", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathLike(String value) {
            addCriterion("path like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotLike(String value) {
            addCriterion("path not like", value, "path");
            return (Criteria) this;
        }

        public Criteria andPathIn(List<String> values) {
            addCriterion("path in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotIn(List<String> values) {
            addCriterion("path not in", values, "path");
            return (Criteria) this;
        }

        public Criteria andPathBetween(String value1, String value2) {
            addCriterion("path between", value1, value2, "path");
            return (Criteria) this;
        }

        public Criteria andPathNotBetween(String value1, String value2) {
            addCriterion("path not between", value1, value2, "path");
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

        public Criteria andReplyOfIsNull() {
            addCriterion("reply_of is null");
            return (Criteria) this;
        }

        public Criteria andReplyOfIsNotNull() {
            addCriterion("reply_of is not null");
            return (Criteria) this;
        }

        public Criteria andReplyOfEqualTo(Long value) {
            addCriterion("reply_of =", value, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfNotEqualTo(Long value) {
            addCriterion("reply_of <>", value, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfGreaterThan(Long value) {
            addCriterion("reply_of >", value, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfGreaterThanOrEqualTo(Long value) {
            addCriterion("reply_of >=", value, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfLessThan(Long value) {
            addCriterion("reply_of <", value, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfLessThanOrEqualTo(Long value) {
            addCriterion("reply_of <=", value, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfIn(List<Long> values) {
            addCriterion("reply_of in", values, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfNotIn(List<Long> values) {
            addCriterion("reply_of not in", values, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfBetween(Long value1, Long value2) {
            addCriterion("reply_of between", value1, value2, "replyOf");
            return (Criteria) this;
        }

        public Criteria andReplyOfNotBetween(Long value1, Long value2) {
            addCriterion("reply_of not between", value1, value2, "replyOf");
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

        public Criteria andIsPassIsNull() {
            addCriterion("is_pass is null");
            return (Criteria) this;
        }

        public Criteria andIsPassIsNotNull() {
            addCriterion("is_pass is not null");
            return (Criteria) this;
        }

        public Criteria andIsPassEqualTo(Boolean value) {
            addCriterion("is_pass =", value, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassNotEqualTo(Boolean value) {
            addCriterion("is_pass <>", value, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassGreaterThan(Boolean value) {
            addCriterion("is_pass >", value, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_pass >=", value, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassLessThan(Boolean value) {
            addCriterion("is_pass <", value, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassLessThanOrEqualTo(Boolean value) {
            addCriterion("is_pass <=", value, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassIn(List<Boolean> values) {
            addCriterion("is_pass in", values, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassNotIn(List<Boolean> values) {
            addCriterion("is_pass not in", values, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pass between", value1, value2, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsPassNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_pass not between", value1, value2, "isPass");
            return (Criteria) this;
        }

        public Criteria andIsRejectIsNull() {
            addCriterion("is_reject is null");
            return (Criteria) this;
        }

        public Criteria andIsRejectIsNotNull() {
            addCriterion("is_reject is not null");
            return (Criteria) this;
        }

        public Criteria andIsRejectEqualTo(Boolean value) {
            addCriterion("is_reject =", value, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectNotEqualTo(Boolean value) {
            addCriterion("is_reject <>", value, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectGreaterThan(Boolean value) {
            addCriterion("is_reject >", value, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_reject >=", value, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectLessThan(Boolean value) {
            addCriterion("is_reject <", value, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectLessThanOrEqualTo(Boolean value) {
            addCriterion("is_reject <=", value, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectIn(List<Boolean> values) {
            addCriterion("is_reject in", values, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectNotIn(List<Boolean> values) {
            addCriterion("is_reject not in", values, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectBetween(Boolean value1, Boolean value2) {
            addCriterion("is_reject between", value1, value2, "isReject");
            return (Criteria) this;
        }

        public Criteria andIsRejectNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_reject not between", value1, value2, "isReject");
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