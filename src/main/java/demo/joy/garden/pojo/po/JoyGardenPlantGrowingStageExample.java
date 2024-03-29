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

        public Criteria andStageSequenceIsNull() {
            addCriterion("stage_sequence is null");
            return (Criteria) this;
        }

        public Criteria andStageSequenceIsNotNull() {
            addCriterion("stage_sequence is not null");
            return (Criteria) this;
        }

        public Criteria andStageSequenceEqualTo(Integer value) {
            addCriterion("stage_sequence =", value, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceNotEqualTo(Integer value) {
            addCriterion("stage_sequence <>", value, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceGreaterThan(Integer value) {
            addCriterion("stage_sequence >", value, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceGreaterThanOrEqualTo(Integer value) {
            addCriterion("stage_sequence >=", value, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceLessThan(Integer value) {
            addCriterion("stage_sequence <", value, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceLessThanOrEqualTo(Integer value) {
            addCriterion("stage_sequence <=", value, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceIn(List<Integer> values) {
            addCriterion("stage_sequence in", values, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceNotIn(List<Integer> values) {
            addCriterion("stage_sequence not in", values, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceBetween(Integer value1, Integer value2) {
            addCriterion("stage_sequence between", value1, value2, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andStageSequenceNotBetween(Integer value1, Integer value2) {
            addCriterion("stage_sequence not between", value1, value2, "stageSequence");
            return (Criteria) this;
        }

        public Criteria andIsLastStageIsNull() {
            addCriterion("is_last_stage is null");
            return (Criteria) this;
        }

        public Criteria andIsLastStageIsNotNull() {
            addCriterion("is_last_stage is not null");
            return (Criteria) this;
        }

        public Criteria andIsLastStageEqualTo(Boolean value) {
            addCriterion("is_last_stage =", value, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageNotEqualTo(Boolean value) {
            addCriterion("is_last_stage <>", value, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageGreaterThan(Boolean value) {
            addCriterion("is_last_stage >", value, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_last_stage >=", value, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageLessThan(Boolean value) {
            addCriterion("is_last_stage <", value, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageLessThanOrEqualTo(Boolean value) {
            addCriterion("is_last_stage <=", value, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageIn(List<Boolean> values) {
            addCriterion("is_last_stage in", values, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageNotIn(List<Boolean> values) {
            addCriterion("is_last_stage not in", values, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageBetween(Boolean value1, Boolean value2) {
            addCriterion("is_last_stage between", value1, value2, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsLastStageNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_last_stage not between", value1, value2, "isLastStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageIsNull() {
            addCriterion("is_cycle_stage is null");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageIsNotNull() {
            addCriterion("is_cycle_stage is not null");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageEqualTo(Boolean value) {
            addCriterion("is_cycle_stage =", value, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageNotEqualTo(Boolean value) {
            addCriterion("is_cycle_stage <>", value, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageGreaterThan(Boolean value) {
            addCriterion("is_cycle_stage >", value, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_cycle_stage >=", value, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageLessThan(Boolean value) {
            addCriterion("is_cycle_stage <", value, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageLessThanOrEqualTo(Boolean value) {
            addCriterion("is_cycle_stage <=", value, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageIn(List<Boolean> values) {
            addCriterion("is_cycle_stage in", values, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageNotIn(List<Boolean> values) {
            addCriterion("is_cycle_stage not in", values, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cycle_stage between", value1, value2, "isCycleStage");
            return (Criteria) this;
        }

        public Criteria andIsCycleStageNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_cycle_stage not between", value1, value2, "isCycleStage");
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

        public Criteria andImgIdIsNull() {
            addCriterion("img_id is null");
            return (Criteria) this;
        }

        public Criteria andImgIdIsNotNull() {
            addCriterion("img_id is not null");
            return (Criteria) this;
        }

        public Criteria andImgIdEqualTo(Long value) {
            addCriterion("img_id =", value, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdNotEqualTo(Long value) {
            addCriterion("img_id <>", value, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdGreaterThan(Long value) {
            addCriterion("img_id >", value, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdGreaterThanOrEqualTo(Long value) {
            addCriterion("img_id >=", value, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdLessThan(Long value) {
            addCriterion("img_id <", value, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdLessThanOrEqualTo(Long value) {
            addCriterion("img_id <=", value, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdIn(List<Long> values) {
            addCriterion("img_id in", values, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdNotIn(List<Long> values) {
            addCriterion("img_id not in", values, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdBetween(Long value1, Long value2) {
            addCriterion("img_id between", value1, value2, "imgId");
            return (Criteria) this;
        }

        public Criteria andImgIdNotBetween(Long value1, Long value2) {
            addCriterion("img_id not between", value1, value2, "imgId");
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