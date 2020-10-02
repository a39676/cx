package demo.joy.mercenary.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JoyMercenaryStoreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public JoyMercenaryStoreExample() {
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

        public Criteria andMercenaryNameIsNull() {
            addCriterion("mercenary_name is null");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameIsNotNull() {
            addCriterion("mercenary_name is not null");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameEqualTo(String value) {
            addCriterion("mercenary_name =", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameNotEqualTo(String value) {
            addCriterion("mercenary_name <>", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameGreaterThan(String value) {
            addCriterion("mercenary_name >", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameGreaterThanOrEqualTo(String value) {
            addCriterion("mercenary_name >=", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameLessThan(String value) {
            addCriterion("mercenary_name <", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameLessThanOrEqualTo(String value) {
            addCriterion("mercenary_name <=", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameLike(String value) {
            addCriterion("mercenary_name like", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameNotLike(String value) {
            addCriterion("mercenary_name not like", value, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameIn(List<String> values) {
            addCriterion("mercenary_name in", values, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameNotIn(List<String> values) {
            addCriterion("mercenary_name not in", values, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameBetween(String value1, String value2) {
            addCriterion("mercenary_name between", value1, value2, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andMercenaryNameNotBetween(String value1, String value2) {
            addCriterion("mercenary_name not between", value1, value2, "mercenaryName");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Boolean value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Boolean value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Boolean value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Boolean value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Boolean value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Boolean value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Boolean> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Boolean> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Boolean value1, Boolean value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Boolean value1, Boolean value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andMaxSaleIsNull() {
            addCriterion("max_sale is null");
            return (Criteria) this;
        }

        public Criteria andMaxSaleIsNotNull() {
            addCriterion("max_sale is not null");
            return (Criteria) this;
        }

        public Criteria andMaxSaleEqualTo(Integer value) {
            addCriterion("max_sale =", value, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleNotEqualTo(Integer value) {
            addCriterion("max_sale <>", value, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleGreaterThan(Integer value) {
            addCriterion("max_sale >", value, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_sale >=", value, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleLessThan(Integer value) {
            addCriterion("max_sale <", value, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleLessThanOrEqualTo(Integer value) {
            addCriterion("max_sale <=", value, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleIn(List<Integer> values) {
            addCriterion("max_sale in", values, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleNotIn(List<Integer> values) {
            addCriterion("max_sale not in", values, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleBetween(Integer value1, Integer value2) {
            addCriterion("max_sale between", value1, value2, "maxSale");
            return (Criteria) this;
        }

        public Criteria andMaxSaleNotBetween(Integer value1, Integer value2) {
            addCriterion("max_sale not between", value1, value2, "maxSale");
            return (Criteria) this;
        }

        public Criteria andSkillCountIsNull() {
            addCriterion("skill_count is null");
            return (Criteria) this;
        }

        public Criteria andSkillCountIsNotNull() {
            addCriterion("skill_count is not null");
            return (Criteria) this;
        }

        public Criteria andSkillCountEqualTo(Integer value) {
            addCriterion("skill_count =", value, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountNotEqualTo(Integer value) {
            addCriterion("skill_count <>", value, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountGreaterThan(Integer value) {
            addCriterion("skill_count >", value, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("skill_count >=", value, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountLessThan(Integer value) {
            addCriterion("skill_count <", value, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountLessThanOrEqualTo(Integer value) {
            addCriterion("skill_count <=", value, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountIn(List<Integer> values) {
            addCriterion("skill_count in", values, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountNotIn(List<Integer> values) {
            addCriterion("skill_count not in", values, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountBetween(Integer value1, Integer value2) {
            addCriterion("skill_count between", value1, value2, "skillCount");
            return (Criteria) this;
        }

        public Criteria andSkillCountNotBetween(Integer value1, Integer value2) {
            addCriterion("skill_count not between", value1, value2, "skillCount");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andAttributePathIsNull() {
            addCriterion("attribute_path is null");
            return (Criteria) this;
        }

        public Criteria andAttributePathIsNotNull() {
            addCriterion("attribute_path is not null");
            return (Criteria) this;
        }

        public Criteria andAttributePathEqualTo(String value) {
            addCriterion("attribute_path =", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathNotEqualTo(String value) {
            addCriterion("attribute_path <>", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathGreaterThan(String value) {
            addCriterion("attribute_path >", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathGreaterThanOrEqualTo(String value) {
            addCriterion("attribute_path >=", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathLessThan(String value) {
            addCriterion("attribute_path <", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathLessThanOrEqualTo(String value) {
            addCriterion("attribute_path <=", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathLike(String value) {
            addCriterion("attribute_path like", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathNotLike(String value) {
            addCriterion("attribute_path not like", value, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathIn(List<String> values) {
            addCriterion("attribute_path in", values, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathNotIn(List<String> values) {
            addCriterion("attribute_path not in", values, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathBetween(String value1, String value2) {
            addCriterion("attribute_path between", value1, value2, "attributePath");
            return (Criteria) this;
        }

        public Criteria andAttributePathNotBetween(String value1, String value2) {
            addCriterion("attribute_path not between", value1, value2, "attributePath");
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