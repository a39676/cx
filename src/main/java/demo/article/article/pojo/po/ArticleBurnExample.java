package demo.article.article.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleBurnExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleBurnExample() {
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

        public Criteria andReadCountIsNull() {
            addCriterion("read_count is null");
            return (Criteria) this;
        }

        public Criteria andReadCountIsNotNull() {
            addCriterion("read_count is not null");
            return (Criteria) this;
        }

        public Criteria andReadCountEqualTo(Integer value) {
            addCriterion("read_count =", value, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountNotEqualTo(Integer value) {
            addCriterion("read_count <>", value, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountGreaterThan(Integer value) {
            addCriterion("read_count >", value, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("read_count >=", value, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountLessThan(Integer value) {
            addCriterion("read_count <", value, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountLessThanOrEqualTo(Integer value) {
            addCriterion("read_count <=", value, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountIn(List<Integer> values) {
            addCriterion("read_count in", values, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountNotIn(List<Integer> values) {
            addCriterion("read_count not in", values, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountBetween(Integer value1, Integer value2) {
            addCriterion("read_count between", value1, value2, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadCountNotBetween(Integer value1, Integer value2) {
            addCriterion("read_count not between", value1, value2, "readCount");
            return (Criteria) this;
        }

        public Criteria andReadLimitIsNull() {
            addCriterion("read_limit is null");
            return (Criteria) this;
        }

        public Criteria andReadLimitIsNotNull() {
            addCriterion("read_limit is not null");
            return (Criteria) this;
        }

        public Criteria andReadLimitEqualTo(Integer value) {
            addCriterion("read_limit =", value, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitNotEqualTo(Integer value) {
            addCriterion("read_limit <>", value, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitGreaterThan(Integer value) {
            addCriterion("read_limit >", value, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("read_limit >=", value, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitLessThan(Integer value) {
            addCriterion("read_limit <", value, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitLessThanOrEqualTo(Integer value) {
            addCriterion("read_limit <=", value, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitIn(List<Integer> values) {
            addCriterion("read_limit in", values, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitNotIn(List<Integer> values) {
            addCriterion("read_limit not in", values, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitBetween(Integer value1, Integer value2) {
            addCriterion("read_limit between", value1, value2, "readLimit");
            return (Criteria) this;
        }

        public Criteria andReadLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("read_limit not between", value1, value2, "readLimit");
            return (Criteria) this;
        }

        public Criteria andValidTimeIsNull() {
            addCriterion("valid_time is null");
            return (Criteria) this;
        }

        public Criteria andValidTimeIsNotNull() {
            addCriterion("valid_time is not null");
            return (Criteria) this;
        }

        public Criteria andValidTimeEqualTo(LocalDateTime value) {
            addCriterion("valid_time =", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotEqualTo(LocalDateTime value) {
            addCriterion("valid_time <>", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThan(LocalDateTime value) {
            addCriterion("valid_time >", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("valid_time >=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThan(LocalDateTime value) {
            addCriterion("valid_time <", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("valid_time <=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeIn(List<LocalDateTime> values) {
            addCriterion("valid_time in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotIn(List<LocalDateTime> values) {
            addCriterion("valid_time not in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("valid_time between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("valid_time not between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andReadKeyIsNull() {
            addCriterion("read_key is null");
            return (Criteria) this;
        }

        public Criteria andReadKeyIsNotNull() {
            addCriterion("read_key is not null");
            return (Criteria) this;
        }

        public Criteria andReadKeyEqualTo(Long value) {
            addCriterion("read_key =", value, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyNotEqualTo(Long value) {
            addCriterion("read_key <>", value, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyGreaterThan(Long value) {
            addCriterion("read_key >", value, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyGreaterThanOrEqualTo(Long value) {
            addCriterion("read_key >=", value, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyLessThan(Long value) {
            addCriterion("read_key <", value, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyLessThanOrEqualTo(Long value) {
            addCriterion("read_key <=", value, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyIn(List<Long> values) {
            addCriterion("read_key in", values, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyNotIn(List<Long> values) {
            addCriterion("read_key not in", values, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyBetween(Long value1, Long value2) {
            addCriterion("read_key between", value1, value2, "readKey");
            return (Criteria) this;
        }

        public Criteria andReadKeyNotBetween(Long value1, Long value2) {
            addCriterion("read_key not between", value1, value2, "readKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyIsNull() {
            addCriterion("burn_key is null");
            return (Criteria) this;
        }

        public Criteria andBurnKeyIsNotNull() {
            addCriterion("burn_key is not null");
            return (Criteria) this;
        }

        public Criteria andBurnKeyEqualTo(Long value) {
            addCriterion("burn_key =", value, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyNotEqualTo(Long value) {
            addCriterion("burn_key <>", value, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyGreaterThan(Long value) {
            addCriterion("burn_key >", value, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyGreaterThanOrEqualTo(Long value) {
            addCriterion("burn_key >=", value, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyLessThan(Long value) {
            addCriterion("burn_key <", value, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyLessThanOrEqualTo(Long value) {
            addCriterion("burn_key <=", value, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyIn(List<Long> values) {
            addCriterion("burn_key in", values, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyNotIn(List<Long> values) {
            addCriterion("burn_key not in", values, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyBetween(Long value1, Long value2) {
            addCriterion("burn_key between", value1, value2, "burnKey");
            return (Criteria) this;
        }

        public Criteria andBurnKeyNotBetween(Long value1, Long value2) {
            addCriterion("burn_key not between", value1, value2, "burnKey");
            return (Criteria) this;
        }

        public Criteria andIsBurnedIsNull() {
            addCriterion("is_burned is null");
            return (Criteria) this;
        }

        public Criteria andIsBurnedIsNotNull() {
            addCriterion("is_burned is not null");
            return (Criteria) this;
        }

        public Criteria andIsBurnedEqualTo(Boolean value) {
            addCriterion("is_burned =", value, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedNotEqualTo(Boolean value) {
            addCriterion("is_burned <>", value, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedGreaterThan(Boolean value) {
            addCriterion("is_burned >", value, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_burned >=", value, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedLessThan(Boolean value) {
            addCriterion("is_burned <", value, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_burned <=", value, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedIn(List<Boolean> values) {
            addCriterion("is_burned in", values, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedNotIn(List<Boolean> values) {
            addCriterion("is_burned not in", values, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_burned between", value1, value2, "isBurned");
            return (Criteria) this;
        }

        public Criteria andIsBurnedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_burned not between", value1, value2, "isBurned");
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