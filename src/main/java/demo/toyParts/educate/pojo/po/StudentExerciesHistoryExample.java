package demo.toyParts.educate.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentExerciesHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudentExerciesHistoryExample() {
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

        public Criteria andExerciesIdIsNull() {
            addCriterion("exercies_id is null");
            return (Criteria) this;
        }

        public Criteria andExerciesIdIsNotNull() {
            addCriterion("exercies_id is not null");
            return (Criteria) this;
        }

        public Criteria andExerciesIdEqualTo(Long value) {
            addCriterion("exercies_id =", value, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdNotEqualTo(Long value) {
            addCriterion("exercies_id <>", value, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdGreaterThan(Long value) {
            addCriterion("exercies_id >", value, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdGreaterThanOrEqualTo(Long value) {
            addCriterion("exercies_id >=", value, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdLessThan(Long value) {
            addCriterion("exercies_id <", value, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdLessThanOrEqualTo(Long value) {
            addCriterion("exercies_id <=", value, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdIn(List<Long> values) {
            addCriterion("exercies_id in", values, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdNotIn(List<Long> values) {
            addCriterion("exercies_id not in", values, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdBetween(Long value1, Long value2) {
            addCriterion("exercies_id between", value1, value2, "exerciesId");
            return (Criteria) this;
        }

        public Criteria andExerciesIdNotBetween(Long value1, Long value2) {
            addCriterion("exercies_id not between", value1, value2, "exerciesId");
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

        public Criteria andSubjectTypeIsNull() {
            addCriterion("subject_type is null");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeIsNotNull() {
            addCriterion("subject_type is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeEqualTo(Long value) {
            addCriterion("subject_type =", value, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeNotEqualTo(Long value) {
            addCriterion("subject_type <>", value, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeGreaterThan(Long value) {
            addCriterion("subject_type >", value, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("subject_type >=", value, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeLessThan(Long value) {
            addCriterion("subject_type <", value, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeLessThanOrEqualTo(Long value) {
            addCriterion("subject_type <=", value, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeIn(List<Long> values) {
            addCriterion("subject_type in", values, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeNotIn(List<Long> values) {
            addCriterion("subject_type not in", values, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeBetween(Long value1, Long value2) {
            addCriterion("subject_type between", value1, value2, "subjectType");
            return (Criteria) this;
        }

        public Criteria andSubjectTypeNotBetween(Long value1, Long value2) {
            addCriterion("subject_type not between", value1, value2, "subjectType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeIsNull() {
            addCriterion("grade_type is null");
            return (Criteria) this;
        }

        public Criteria andGradeTypeIsNotNull() {
            addCriterion("grade_type is not null");
            return (Criteria) this;
        }

        public Criteria andGradeTypeEqualTo(Long value) {
            addCriterion("grade_type =", value, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeNotEqualTo(Long value) {
            addCriterion("grade_type <>", value, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeGreaterThan(Long value) {
            addCriterion("grade_type >", value, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("grade_type >=", value, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeLessThan(Long value) {
            addCriterion("grade_type <", value, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeLessThanOrEqualTo(Long value) {
            addCriterion("grade_type <=", value, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeIn(List<Long> values) {
            addCriterion("grade_type in", values, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeNotIn(List<Long> values) {
            addCriterion("grade_type not in", values, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeBetween(Long value1, Long value2) {
            addCriterion("grade_type between", value1, value2, "gradeType");
            return (Criteria) this;
        }

        public Criteria andGradeTypeNotBetween(Long value1, Long value2) {
            addCriterion("grade_type not between", value1, value2, "gradeType");
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

        public Criteria andCompeletionTimeIsNull() {
            addCriterion("compeletion_time is null");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeIsNotNull() {
            addCriterion("compeletion_time is not null");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeEqualTo(LocalDateTime value) {
            addCriterion("compeletion_time =", value, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeNotEqualTo(LocalDateTime value) {
            addCriterion("compeletion_time <>", value, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeGreaterThan(LocalDateTime value) {
            addCriterion("compeletion_time >", value, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("compeletion_time >=", value, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeLessThan(LocalDateTime value) {
            addCriterion("compeletion_time <", value, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("compeletion_time <=", value, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeIn(List<LocalDateTime> values) {
            addCriterion("compeletion_time in", values, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeNotIn(List<LocalDateTime> values) {
            addCriterion("compeletion_time not in", values, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("compeletion_time between", value1, value2, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andCompeletionTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("compeletion_time not between", value1, value2, "compeletionTime");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(BigDecimal value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(BigDecimal value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(BigDecimal value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(BigDecimal value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<BigDecimal> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<BigDecimal> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("score not between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andPointsIsNull() {
            addCriterion("points is null");
            return (Criteria) this;
        }

        public Criteria andPointsIsNotNull() {
            addCriterion("points is not null");
            return (Criteria) this;
        }

        public Criteria andPointsEqualTo(BigDecimal value) {
            addCriterion("points =", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotEqualTo(BigDecimal value) {
            addCriterion("points <>", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThan(BigDecimal value) {
            addCriterion("points >", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("points >=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThan(BigDecimal value) {
            addCriterion("points <", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("points <=", value, "points");
            return (Criteria) this;
        }

        public Criteria andPointsIn(List<BigDecimal> values) {
            addCriterion("points in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotIn(List<BigDecimal> values) {
            addCriterion("points not in", values, "points");
            return (Criteria) this;
        }

        public Criteria andPointsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andPointsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("points not between", value1, value2, "points");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("file_path is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("file_path is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("file_path =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("file_path <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("file_path >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("file_path >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("file_path <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("file_path <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("file_path like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("file_path not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("file_path in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("file_path not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("file_path between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("file_path not between", value1, value2, "filePath");
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