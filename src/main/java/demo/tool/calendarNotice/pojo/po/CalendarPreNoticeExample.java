package demo.tool.calendarNotice.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarPreNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CalendarPreNoticeExample() {
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

        public Criteria andBindNoticeIdIsNull() {
            addCriterion("bind_notice_id is null");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdIsNotNull() {
            addCriterion("bind_notice_id is not null");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdEqualTo(Long value) {
            addCriterion("bind_notice_id =", value, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdNotEqualTo(Long value) {
            addCriterion("bind_notice_id <>", value, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdGreaterThan(Long value) {
            addCriterion("bind_notice_id >", value, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("bind_notice_id >=", value, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdLessThan(Long value) {
            addCriterion("bind_notice_id <", value, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdLessThanOrEqualTo(Long value) {
            addCriterion("bind_notice_id <=", value, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdIn(List<Long> values) {
            addCriterion("bind_notice_id in", values, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdNotIn(List<Long> values) {
            addCriterion("bind_notice_id not in", values, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdBetween(Long value1, Long value2) {
            addCriterion("bind_notice_id between", value1, value2, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andBindNoticeIdNotBetween(Long value1, Long value2) {
            addCriterion("bind_notice_id not between", value1, value2, "bindNoticeId");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitIsNull() {
            addCriterion("repeat_time_unit is null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitIsNotNull() {
            addCriterion("repeat_time_unit is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitEqualTo(Integer value) {
            addCriterion("repeat_time_unit =", value, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitNotEqualTo(Integer value) {
            addCriterion("repeat_time_unit <>", value, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitGreaterThan(Integer value) {
            addCriterion("repeat_time_unit >", value, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitGreaterThanOrEqualTo(Integer value) {
            addCriterion("repeat_time_unit >=", value, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitLessThan(Integer value) {
            addCriterion("repeat_time_unit <", value, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitLessThanOrEqualTo(Integer value) {
            addCriterion("repeat_time_unit <=", value, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitIn(List<Integer> values) {
            addCriterion("repeat_time_unit in", values, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitNotIn(List<Integer> values) {
            addCriterion("repeat_time_unit not in", values, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitBetween(Integer value1, Integer value2) {
            addCriterion("repeat_time_unit between", value1, value2, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeUnitNotBetween(Integer value1, Integer value2) {
            addCriterion("repeat_time_unit not between", value1, value2, "repeatTimeUnit");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeIsNull() {
            addCriterion("repeat_time_range is null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeIsNotNull() {
            addCriterion("repeat_time_range is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeEqualTo(Integer value) {
            addCriterion("repeat_time_range =", value, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeNotEqualTo(Integer value) {
            addCriterion("repeat_time_range <>", value, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeGreaterThan(Integer value) {
            addCriterion("repeat_time_range >", value, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("repeat_time_range >=", value, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeLessThan(Integer value) {
            addCriterion("repeat_time_range <", value, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeLessThanOrEqualTo(Integer value) {
            addCriterion("repeat_time_range <=", value, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeIn(List<Integer> values) {
            addCriterion("repeat_time_range in", values, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeNotIn(List<Integer> values) {
            addCriterion("repeat_time_range not in", values, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeBetween(Integer value1, Integer value2) {
            addCriterion("repeat_time_range between", value1, value2, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatTimeRangeNotBetween(Integer value1, Integer value2) {
            addCriterion("repeat_time_range not between", value1, value2, "repeatTimeRange");
            return (Criteria) this;
        }

        public Criteria andRepeatCountIsNull() {
            addCriterion("repeat_count is null");
            return (Criteria) this;
        }

        public Criteria andRepeatCountIsNotNull() {
            addCriterion("repeat_count is not null");
            return (Criteria) this;
        }

        public Criteria andRepeatCountEqualTo(Integer value) {
            addCriterion("repeat_count =", value, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountNotEqualTo(Integer value) {
            addCriterion("repeat_count <>", value, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountGreaterThan(Integer value) {
            addCriterion("repeat_count >", value, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("repeat_count >=", value, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountLessThan(Integer value) {
            addCriterion("repeat_count <", value, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountLessThanOrEqualTo(Integer value) {
            addCriterion("repeat_count <=", value, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountIn(List<Integer> values) {
            addCriterion("repeat_count in", values, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountNotIn(List<Integer> values) {
            addCriterion("repeat_count not in", values, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountBetween(Integer value1, Integer value2) {
            addCriterion("repeat_count between", value1, value2, "repeatCount");
            return (Criteria) this;
        }

        public Criteria andRepeatCountNotBetween(Integer value1, Integer value2) {
            addCriterion("repeat_count not between", value1, value2, "repeatCount");
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

        public Criteria andNoticeTimeIsNull() {
            addCriterion("notice_time is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeIsNotNull() {
            addCriterion("notice_time is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeEqualTo(LocalDateTime value) {
            addCriterion("notice_time =", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeNotEqualTo(LocalDateTime value) {
            addCriterion("notice_time <>", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeGreaterThan(LocalDateTime value) {
            addCriterion("notice_time >", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("notice_time >=", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeLessThan(LocalDateTime value) {
            addCriterion("notice_time <", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("notice_time <=", value, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeIn(List<LocalDateTime> values) {
            addCriterion("notice_time in", values, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeNotIn(List<LocalDateTime> values) {
            addCriterion("notice_time not in", values, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("notice_time between", value1, value2, "noticeTime");
            return (Criteria) this;
        }

        public Criteria andNoticeTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("notice_time not between", value1, value2, "noticeTime");
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