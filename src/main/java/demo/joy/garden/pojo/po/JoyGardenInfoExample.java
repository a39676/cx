package demo.joy.garden.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JoyGardenInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JoyGardenInfoExample() {
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

        public Criteria andGardenNameIsNull() {
            addCriterion("garden_name is null");
            return (Criteria) this;
        }

        public Criteria andGardenNameIsNotNull() {
            addCriterion("garden_name is not null");
            return (Criteria) this;
        }

        public Criteria andGardenNameEqualTo(String value) {
            addCriterion("garden_name =", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameNotEqualTo(String value) {
            addCriterion("garden_name <>", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameGreaterThan(String value) {
            addCriterion("garden_name >", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameGreaterThanOrEqualTo(String value) {
            addCriterion("garden_name >=", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameLessThan(String value) {
            addCriterion("garden_name <", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameLessThanOrEqualTo(String value) {
            addCriterion("garden_name <=", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameLike(String value) {
            addCriterion("garden_name like", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameNotLike(String value) {
            addCriterion("garden_name not like", value, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameIn(List<String> values) {
            addCriterion("garden_name in", values, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameNotIn(List<String> values) {
            addCriterion("garden_name not in", values, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameBetween(String value1, String value2) {
            addCriterion("garden_name between", value1, value2, "gardenName");
            return (Criteria) this;
        }

        public Criteria andGardenNameNotBetween(String value1, String value2) {
            addCriterion("garden_name not between", value1, value2, "gardenName");
            return (Criteria) this;
        }

        public Criteria andFieldCountIsNull() {
            addCriterion("field_count is null");
            return (Criteria) this;
        }

        public Criteria andFieldCountIsNotNull() {
            addCriterion("field_count is not null");
            return (Criteria) this;
        }

        public Criteria andFieldCountEqualTo(Integer value) {
            addCriterion("field_count =", value, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountNotEqualTo(Integer value) {
            addCriterion("field_count <>", value, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountGreaterThan(Integer value) {
            addCriterion("field_count >", value, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("field_count >=", value, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountLessThan(Integer value) {
            addCriterion("field_count <", value, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountLessThanOrEqualTo(Integer value) {
            addCriterion("field_count <=", value, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountIn(List<Integer> values) {
            addCriterion("field_count in", values, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountNotIn(List<Integer> values) {
            addCriterion("field_count not in", values, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountBetween(Integer value1, Integer value2) {
            addCriterion("field_count between", value1, value2, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andFieldCountNotBetween(Integer value1, Integer value2) {
            addCriterion("field_count not between", value1, value2, "fieldCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountIsNull() {
            addCriterion("wetland_count is null");
            return (Criteria) this;
        }

        public Criteria andWetlandCountIsNotNull() {
            addCriterion("wetland_count is not null");
            return (Criteria) this;
        }

        public Criteria andWetlandCountEqualTo(Integer value) {
            addCriterion("wetland_count =", value, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountNotEqualTo(Integer value) {
            addCriterion("wetland_count <>", value, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountGreaterThan(Integer value) {
            addCriterion("wetland_count >", value, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("wetland_count >=", value, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountLessThan(Integer value) {
            addCriterion("wetland_count <", value, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountLessThanOrEqualTo(Integer value) {
            addCriterion("wetland_count <=", value, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountIn(List<Integer> values) {
            addCriterion("wetland_count in", values, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountNotIn(List<Integer> values) {
            addCriterion("wetland_count not in", values, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountBetween(Integer value1, Integer value2) {
            addCriterion("wetland_count between", value1, value2, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWetlandCountNotBetween(Integer value1, Integer value2) {
            addCriterion("wetland_count not between", value1, value2, "wetlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountIsNull() {
            addCriterion("woodland_count is null");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountIsNotNull() {
            addCriterion("woodland_count is not null");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountEqualTo(Integer value) {
            addCriterion("woodland_count =", value, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountNotEqualTo(Integer value) {
            addCriterion("woodland_count <>", value, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountGreaterThan(Integer value) {
            addCriterion("woodland_count >", value, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("woodland_count >=", value, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountLessThan(Integer value) {
            addCriterion("woodland_count <", value, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountLessThanOrEqualTo(Integer value) {
            addCriterion("woodland_count <=", value, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountIn(List<Integer> values) {
            addCriterion("woodland_count in", values, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountNotIn(List<Integer> values) {
            addCriterion("woodland_count not in", values, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountBetween(Integer value1, Integer value2) {
            addCriterion("woodland_count between", value1, value2, "woodlandCount");
            return (Criteria) this;
        }

        public Criteria andWoodlandCountNotBetween(Integer value1, Integer value2) {
            addCriterion("woodland_count not between", value1, value2, "woodlandCount");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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