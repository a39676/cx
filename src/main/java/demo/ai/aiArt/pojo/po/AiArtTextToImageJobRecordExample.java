package demo.ai.aiArt.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AiArtTextToImageJobRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AiArtTextToImageJobRecordExample() {
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

        public Criteria andAiUserIdIsNull() {
            addCriterion("ai_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAiUserIdIsNotNull() {
            addCriterion("ai_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAiUserIdEqualTo(Long value) {
            addCriterion("ai_user_id =", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdNotEqualTo(Long value) {
            addCriterion("ai_user_id <>", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdGreaterThan(Long value) {
            addCriterion("ai_user_id >", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ai_user_id >=", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdLessThan(Long value) {
            addCriterion("ai_user_id <", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdLessThanOrEqualTo(Long value) {
            addCriterion("ai_user_id <=", value, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdIn(List<Long> values) {
            addCriterion("ai_user_id in", values, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdNotIn(List<Long> values) {
            addCriterion("ai_user_id not in", values, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdBetween(Long value1, Long value2) {
            addCriterion("ai_user_id between", value1, value2, "aiUserId");
            return (Criteria) this;
        }

        public Criteria andAiUserIdNotBetween(Long value1, Long value2) {
            addCriterion("ai_user_id not between", value1, value2, "aiUserId");
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

        public Criteria andJobStatusIsNull() {
            addCriterion("job_status is null");
            return (Criteria) this;
        }

        public Criteria andJobStatusIsNotNull() {
            addCriterion("job_status is not null");
            return (Criteria) this;
        }

        public Criteria andJobStatusEqualTo(Byte value) {
            addCriterion("job_status =", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusNotEqualTo(Byte value) {
            addCriterion("job_status <>", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusGreaterThan(Byte value) {
            addCriterion("job_status >", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("job_status >=", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusLessThan(Byte value) {
            addCriterion("job_status <", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusLessThanOrEqualTo(Byte value) {
            addCriterion("job_status <=", value, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusIn(List<Byte> values) {
            addCriterion("job_status in", values, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusNotIn(List<Byte> values) {
            addCriterion("job_status not in", values, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusBetween(Byte value1, Byte value2) {
            addCriterion("job_status between", value1, value2, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andJobStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("job_status not between", value1, value2, "jobStatus");
            return (Criteria) this;
        }

        public Criteria andRunCountIsNull() {
            addCriterion("run_count is null");
            return (Criteria) this;
        }

        public Criteria andRunCountIsNotNull() {
            addCriterion("run_count is not null");
            return (Criteria) this;
        }

        public Criteria andRunCountEqualTo(Integer value) {
            addCriterion("run_count =", value, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountNotEqualTo(Integer value) {
            addCriterion("run_count <>", value, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountGreaterThan(Integer value) {
            addCriterion("run_count >", value, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("run_count >=", value, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountLessThan(Integer value) {
            addCriterion("run_count <", value, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountLessThanOrEqualTo(Integer value) {
            addCriterion("run_count <=", value, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountIn(List<Integer> values) {
            addCriterion("run_count in", values, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountNotIn(List<Integer> values) {
            addCriterion("run_count not in", values, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountBetween(Integer value1, Integer value2) {
            addCriterion("run_count between", value1, value2, "runCount");
            return (Criteria) this;
        }

        public Criteria andRunCountNotBetween(Integer value1, Integer value2) {
            addCriterion("run_count not between", value1, value2, "runCount");
            return (Criteria) this;
        }

        public Criteria andIsFromApiIsNull() {
            addCriterion("is_from_api is null");
            return (Criteria) this;
        }

        public Criteria andIsFromApiIsNotNull() {
            addCriterion("is_from_api is not null");
            return (Criteria) this;
        }

        public Criteria andIsFromApiEqualTo(Boolean value) {
            addCriterion("is_from_api =", value, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiNotEqualTo(Boolean value) {
            addCriterion("is_from_api <>", value, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiGreaterThan(Boolean value) {
            addCriterion("is_from_api >", value, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_from_api >=", value, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiLessThan(Boolean value) {
            addCriterion("is_from_api <", value, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiLessThanOrEqualTo(Boolean value) {
            addCriterion("is_from_api <=", value, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiIn(List<Boolean> values) {
            addCriterion("is_from_api in", values, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiNotIn(List<Boolean> values) {
            addCriterion("is_from_api not in", values, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiBetween(Boolean value1, Boolean value2) {
            addCriterion("is_from_api between", value1, value2, "isFromApi");
            return (Criteria) this;
        }

        public Criteria andIsFromApiNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_from_api not between", value1, value2, "isFromApi");
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