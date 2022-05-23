package demo.joy.garden.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JoyGardenPlantGrowingStageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JoyGardenPlantGrowingStageExample() {
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

        public Criteria andPlantIdIsNull() {
            addCriterion("plant_id is null");
            return (Criteria) this;
        }

        public Criteria andPlantIdIsNotNull() {
            addCriterion("plant_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlantIdEqualTo(Long value) {
            addCriterion("plant_id =", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdNotEqualTo(Long value) {
            addCriterion("plant_id <>", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdGreaterThan(Long value) {
            addCriterion("plant_id >", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("plant_id >=", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdLessThan(Long value) {
            addCriterion("plant_id <", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdLessThanOrEqualTo(Long value) {
            addCriterion("plant_id <=", value, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdIn(List<Long> values) {
            addCriterion("plant_id in", values, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdNotIn(List<Long> values) {
            addCriterion("plant_id not in", values, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdBetween(Long value1, Long value2) {
            addCriterion("plant_id between", value1, value2, "plantId");
            return (Criteria) this;
        }

        public Criteria andPlantIdNotBetween(Long value1, Long value2) {
            addCriterion("plant_id not between", value1, value2, "plantId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdIsNull() {
            addCriterion("next_stage_id is null");
            return (Criteria) this;
        }

        public Criteria andNextStageIdIsNotNull() {
            addCriterion("next_stage_id is not null");
            return (Criteria) this;
        }

        public Criteria andNextStageIdEqualTo(Long value) {
            addCriterion("next_stage_id =", value, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdNotEqualTo(Long value) {
            addCriterion("next_stage_id <>", value, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdGreaterThan(Long value) {
            addCriterion("next_stage_id >", value, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdGreaterThanOrEqualTo(Long value) {
            addCriterion("next_stage_id >=", value, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdLessThan(Long value) {
            addCriterion("next_stage_id <", value, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdLessThanOrEqualTo(Long value) {
            addCriterion("next_stage_id <=", value, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdIn(List<Long> values) {
            addCriterion("next_stage_id in", values, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdNotIn(List<Long> values) {
            addCriterion("next_stage_id not in", values, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdBetween(Long value1, Long value2) {
            addCriterion("next_stage_id between", value1, value2, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andNextStageIdNotBetween(Long value1, Long value2) {
            addCriterion("next_stage_id not between", value1, value2, "nextStageId");
            return (Criteria) this;
        }

        public Criteria andStageNameIsNull() {
            addCriterion("stage_name is null");
            return (Criteria) this;
        }

        public Criteria andStageNameIsNotNull() {
            addCriterion("stage_name is not null");
            return (Criteria) this;
        }

        public Criteria andStageNameEqualTo(String value) {
            addCriterion("stage_name =", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameNotEqualTo(String value) {
            addCriterion("stage_name <>", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameGreaterThan(String value) {
            addCriterion("stage_name >", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameGreaterThanOrEqualTo(String value) {
            addCriterion("stage_name >=", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameLessThan(String value) {
            addCriterion("stage_name <", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameLessThanOrEqualTo(String value) {
            addCriterion("stage_name <=", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameLike(String value) {
            addCriterion("stage_name like", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameNotLike(String value) {
            addCriterion("stage_name not like", value, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameIn(List<String> values) {
            addCriterion("stage_name in", values, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameNotIn(List<String> values) {
            addCriterion("stage_name not in", values, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameBetween(String value1, String value2) {
            addCriterion("stage_name between", value1, value2, "stageName");
            return (Criteria) this;
        }

        public Criteria andStageNameNotBetween(String value1, String value2) {
            addCriterion("stage_name not between", value1, value2, "stageName");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteIsNull() {
            addCriterion("living_minute is null");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteIsNotNull() {
            addCriterion("living_minute is not null");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteEqualTo(Integer value) {
            addCriterion("living_minute =", value, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteNotEqualTo(Integer value) {
            addCriterion("living_minute <>", value, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteGreaterThan(Integer value) {
            addCriterion("living_minute >", value, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("living_minute >=", value, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteLessThan(Integer value) {
            addCriterion("living_minute <", value, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("living_minute <=", value, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteIn(List<Integer> values) {
            addCriterion("living_minute in", values, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteNotIn(List<Integer> values) {
            addCriterion("living_minute not in", values, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteBetween(Integer value1, Integer value2) {
            addCriterion("living_minute between", value1, value2, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andLivingMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("living_minute not between", value1, value2, "livingMinute");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathIsNull() {
            addCriterion("img_url_path is null");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathIsNotNull() {
            addCriterion("img_url_path is not null");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathEqualTo(String value) {
            addCriterion("img_url_path =", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathNotEqualTo(String value) {
            addCriterion("img_url_path <>", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathGreaterThan(String value) {
            addCriterion("img_url_path >", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathGreaterThanOrEqualTo(String value) {
            addCriterion("img_url_path >=", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathLessThan(String value) {
            addCriterion("img_url_path <", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathLessThanOrEqualTo(String value) {
            addCriterion("img_url_path <=", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathLike(String value) {
            addCriterion("img_url_path like", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathNotLike(String value) {
            addCriterion("img_url_path not like", value, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathIn(List<String> values) {
            addCriterion("img_url_path in", values, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathNotIn(List<String> values) {
            addCriterion("img_url_path not in", values, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathBetween(String value1, String value2) {
            addCriterion("img_url_path between", value1, value2, "imgUrlPath");
            return (Criteria) this;
        }

        public Criteria andImgUrlPathNotBetween(String value1, String value2) {
            addCriterion("img_url_path not between", value1, value2, "imgUrlPath");
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