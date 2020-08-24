package demo.joy.character.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JoyCharacterDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JoyCharacterDetailExample() {
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

        public Criteria andSceneIdIsNull() {
            addCriterion("scene_id is null");
            return (Criteria) this;
        }

        public Criteria andSceneIdIsNotNull() {
            addCriterion("scene_id is not null");
            return (Criteria) this;
        }

        public Criteria andSceneIdEqualTo(Long value) {
            addCriterion("scene_id =", value, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdNotEqualTo(Long value) {
            addCriterion("scene_id <>", value, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdGreaterThan(Long value) {
            addCriterion("scene_id >", value, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdGreaterThanOrEqualTo(Long value) {
            addCriterion("scene_id >=", value, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdLessThan(Long value) {
            addCriterion("scene_id <", value, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdLessThanOrEqualTo(Long value) {
            addCriterion("scene_id <=", value, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdIn(List<Long> values) {
            addCriterion("scene_id in", values, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdNotIn(List<Long> values) {
            addCriterion("scene_id not in", values, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdBetween(Long value1, Long value2) {
            addCriterion("scene_id between", value1, value2, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneIdNotBetween(Long value1, Long value2) {
            addCriterion("scene_id not between", value1, value2, "sceneId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdIsNull() {
            addCriterion("scene_owner_id is null");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdIsNotNull() {
            addCriterion("scene_owner_id is not null");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdEqualTo(Long value) {
            addCriterion("scene_owner_id =", value, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdNotEqualTo(Long value) {
            addCriterion("scene_owner_id <>", value, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdGreaterThan(Long value) {
            addCriterion("scene_owner_id >", value, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("scene_owner_id >=", value, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdLessThan(Long value) {
            addCriterion("scene_owner_id <", value, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdLessThanOrEqualTo(Long value) {
            addCriterion("scene_owner_id <=", value, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdIn(List<Long> values) {
            addCriterion("scene_owner_id in", values, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdNotIn(List<Long> values) {
            addCriterion("scene_owner_id not in", values, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdBetween(Long value1, Long value2) {
            addCriterion("scene_owner_id between", value1, value2, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andSceneOwnerIdNotBetween(Long value1, Long value2) {
            addCriterion("scene_owner_id not between", value1, value2, "sceneOwnerId");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelIsNull() {
            addCriterion("character_level is null");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelIsNotNull() {
            addCriterion("character_level is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelEqualTo(Integer value) {
            addCriterion("character_level =", value, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelNotEqualTo(Integer value) {
            addCriterion("character_level <>", value, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelGreaterThan(Integer value) {
            addCriterion("character_level >", value, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("character_level >=", value, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelLessThan(Integer value) {
            addCriterion("character_level <", value, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelLessThanOrEqualTo(Integer value) {
            addCriterion("character_level <=", value, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelIn(List<Integer> values) {
            addCriterion("character_level in", values, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelNotIn(List<Integer> values) {
            addCriterion("character_level not in", values, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelBetween(Integer value1, Integer value2) {
            addCriterion("character_level between", value1, value2, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("character_level not between", value1, value2, "characterLevel");
            return (Criteria) this;
        }

        public Criteria andCharacterExpIsNull() {
            addCriterion("character_exp is null");
            return (Criteria) this;
        }

        public Criteria andCharacterExpIsNotNull() {
            addCriterion("character_exp is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterExpEqualTo(Integer value) {
            addCriterion("character_exp =", value, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpNotEqualTo(Integer value) {
            addCriterion("character_exp <>", value, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpGreaterThan(Integer value) {
            addCriterion("character_exp >", value, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpGreaterThanOrEqualTo(Integer value) {
            addCriterion("character_exp >=", value, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpLessThan(Integer value) {
            addCriterion("character_exp <", value, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpLessThanOrEqualTo(Integer value) {
            addCriterion("character_exp <=", value, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpIn(List<Integer> values) {
            addCriterion("character_exp in", values, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpNotIn(List<Integer> values) {
            addCriterion("character_exp not in", values, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpBetween(Integer value1, Integer value2) {
            addCriterion("character_exp between", value1, value2, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterExpNotBetween(Integer value1, Integer value2) {
            addCriterion("character_exp not between", value1, value2, "characterExp");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathIsNull() {
            addCriterion("character_img_path is null");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathIsNotNull() {
            addCriterion("character_img_path is not null");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathEqualTo(String value) {
            addCriterion("character_img_path =", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathNotEqualTo(String value) {
            addCriterion("character_img_path <>", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathGreaterThan(String value) {
            addCriterion("character_img_path >", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathGreaterThanOrEqualTo(String value) {
            addCriterion("character_img_path >=", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathLessThan(String value) {
            addCriterion("character_img_path <", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathLessThanOrEqualTo(String value) {
            addCriterion("character_img_path <=", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathLike(String value) {
            addCriterion("character_img_path like", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathNotLike(String value) {
            addCriterion("character_img_path not like", value, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathIn(List<String> values) {
            addCriterion("character_img_path in", values, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathNotIn(List<String> values) {
            addCriterion("character_img_path not in", values, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathBetween(String value1, String value2) {
            addCriterion("character_img_path between", value1, value2, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andCharacterImgPathNotBetween(String value1, String value2) {
            addCriterion("character_img_path not between", value1, value2, "characterImgPath");
            return (Criteria) this;
        }

        public Criteria andEditTimeIsNull() {
            addCriterion("edit_time is null");
            return (Criteria) this;
        }

        public Criteria andEditTimeIsNotNull() {
            addCriterion("edit_time is not null");
            return (Criteria) this;
        }

        public Criteria andEditTimeEqualTo(LocalDateTime value) {
            addCriterion("edit_time =", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotEqualTo(LocalDateTime value) {
            addCriterion("edit_time <>", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeGreaterThan(LocalDateTime value) {
            addCriterion("edit_time >", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("edit_time >=", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeLessThan(LocalDateTime value) {
            addCriterion("edit_time <", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("edit_time <=", value, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeIn(List<LocalDateTime> values) {
            addCriterion("edit_time in", values, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotIn(List<LocalDateTime> values) {
            addCriterion("edit_time not in", values, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("edit_time between", value1, value2, "editTime");
            return (Criteria) this;
        }

        public Criteria andEditTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("edit_time not between", value1, value2, "editTime");
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