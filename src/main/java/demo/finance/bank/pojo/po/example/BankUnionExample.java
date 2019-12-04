package demo.finance.bank.pojo.po.example;

import java.util.ArrayList;
import java.util.List;

public class BankUnionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BankUnionExample() {
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

        public Criteria andBankUnionIdIsNull() {
            addCriterion("bank_union_id is null");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdIsNotNull() {
            addCriterion("bank_union_id is not null");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdEqualTo(Long value) {
            addCriterion("bank_union_id =", value, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdNotEqualTo(Long value) {
            addCriterion("bank_union_id <>", value, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdGreaterThan(Long value) {
            addCriterion("bank_union_id >", value, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("bank_union_id >=", value, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdLessThan(Long value) {
            addCriterion("bank_union_id <", value, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdLessThanOrEqualTo(Long value) {
            addCriterion("bank_union_id <=", value, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdIn(List<Long> values) {
            addCriterion("bank_union_id in", values, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdNotIn(List<Long> values) {
            addCriterion("bank_union_id not in", values, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdBetween(Long value1, Long value2) {
            addCriterion("bank_union_id between", value1, value2, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionIdNotBetween(Long value1, Long value2) {
            addCriterion("bank_union_id not between", value1, value2, "bankUnionId");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameIsNull() {
            addCriterion("bank_union_ename is null");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameIsNotNull() {
            addCriterion("bank_union_ename is not null");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameEqualTo(String value) {
            addCriterion("bank_union_ename =", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameNotEqualTo(String value) {
            addCriterion("bank_union_ename <>", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameGreaterThan(String value) {
            addCriterion("bank_union_ename >", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_union_ename >=", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameLessThan(String value) {
            addCriterion("bank_union_ename <", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameLessThanOrEqualTo(String value) {
            addCriterion("bank_union_ename <=", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameLike(String value) {
            addCriterion("bank_union_ename like", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameNotLike(String value) {
            addCriterion("bank_union_ename not like", value, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameIn(List<String> values) {
            addCriterion("bank_union_ename in", values, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameNotIn(List<String> values) {
            addCriterion("bank_union_ename not in", values, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameBetween(String value1, String value2) {
            addCriterion("bank_union_ename between", value1, value2, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameNotBetween(String value1, String value2) {
            addCriterion("bank_union_ename not between", value1, value2, "bankUnionEname");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortIsNull() {
            addCriterion("bank_union_ename_short is null");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortIsNotNull() {
            addCriterion("bank_union_ename_short is not null");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortEqualTo(String value) {
            addCriterion("bank_union_ename_short =", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortNotEqualTo(String value) {
            addCriterion("bank_union_ename_short <>", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortGreaterThan(String value) {
            addCriterion("bank_union_ename_short >", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortGreaterThanOrEqualTo(String value) {
            addCriterion("bank_union_ename_short >=", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortLessThan(String value) {
            addCriterion("bank_union_ename_short <", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortLessThanOrEqualTo(String value) {
            addCriterion("bank_union_ename_short <=", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortLike(String value) {
            addCriterion("bank_union_ename_short like", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortNotLike(String value) {
            addCriterion("bank_union_ename_short not like", value, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortIn(List<String> values) {
            addCriterion("bank_union_ename_short in", values, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortNotIn(List<String> values) {
            addCriterion("bank_union_ename_short not in", values, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortBetween(String value1, String value2) {
            addCriterion("bank_union_ename_short between", value1, value2, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionEnameShortNotBetween(String value1, String value2) {
            addCriterion("bank_union_ename_short not between", value1, value2, "bankUnionEnameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameIsNull() {
            addCriterion("bank_union_chinese_name is null");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameIsNotNull() {
            addCriterion("bank_union_chinese_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameEqualTo(String value) {
            addCriterion("bank_union_chinese_name =", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameNotEqualTo(String value) {
            addCriterion("bank_union_chinese_name <>", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameGreaterThan(String value) {
            addCriterion("bank_union_chinese_name >", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_union_chinese_name >=", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameLessThan(String value) {
            addCriterion("bank_union_chinese_name <", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameLessThanOrEqualTo(String value) {
            addCriterion("bank_union_chinese_name <=", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameLike(String value) {
            addCriterion("bank_union_chinese_name like", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameNotLike(String value) {
            addCriterion("bank_union_chinese_name not like", value, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameIn(List<String> values) {
            addCriterion("bank_union_chinese_name in", values, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameNotIn(List<String> values) {
            addCriterion("bank_union_chinese_name not in", values, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameBetween(String value1, String value2) {
            addCriterion("bank_union_chinese_name between", value1, value2, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameNotBetween(String value1, String value2) {
            addCriterion("bank_union_chinese_name not between", value1, value2, "bankUnionChineseName");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortIsNull() {
            addCriterion("bank_union_chinese_name_short is null");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortIsNotNull() {
            addCriterion("bank_union_chinese_name_short is not null");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortEqualTo(String value) {
            addCriterion("bank_union_chinese_name_short =", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortNotEqualTo(String value) {
            addCriterion("bank_union_chinese_name_short <>", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortGreaterThan(String value) {
            addCriterion("bank_union_chinese_name_short >", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortGreaterThanOrEqualTo(String value) {
            addCriterion("bank_union_chinese_name_short >=", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortLessThan(String value) {
            addCriterion("bank_union_chinese_name_short <", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortLessThanOrEqualTo(String value) {
            addCriterion("bank_union_chinese_name_short <=", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortLike(String value) {
            addCriterion("bank_union_chinese_name_short like", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortNotLike(String value) {
            addCriterion("bank_union_chinese_name_short not like", value, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortIn(List<String> values) {
            addCriterion("bank_union_chinese_name_short in", values, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortNotIn(List<String> values) {
            addCriterion("bank_union_chinese_name_short not in", values, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortBetween(String value1, String value2) {
            addCriterion("bank_union_chinese_name_short between", value1, value2, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andBankUnionChineseNameShortNotBetween(String value1, String value2) {
            addCriterion("bank_union_chinese_name_short not between", value1, value2, "bankUnionChineseNameShort");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionIsNull() {
            addCriterion("common_bank_union is null");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionIsNotNull() {
            addCriterion("common_bank_union is not null");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionEqualTo(Boolean value) {
            addCriterion("common_bank_union =", value, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionNotEqualTo(Boolean value) {
            addCriterion("common_bank_union <>", value, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionGreaterThan(Boolean value) {
            addCriterion("common_bank_union >", value, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionGreaterThanOrEqualTo(Boolean value) {
            addCriterion("common_bank_union >=", value, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionLessThan(Boolean value) {
            addCriterion("common_bank_union <", value, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionLessThanOrEqualTo(Boolean value) {
            addCriterion("common_bank_union <=", value, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionIn(List<Boolean> values) {
            addCriterion("common_bank_union in", values, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionNotIn(List<Boolean> values) {
            addCriterion("common_bank_union not in", values, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionBetween(Boolean value1, Boolean value2) {
            addCriterion("common_bank_union between", value1, value2, "commonBankUnion");
            return (Criteria) this;
        }

        public Criteria andCommonBankUnionNotBetween(Boolean value1, Boolean value2) {
            addCriterion("common_bank_union not between", value1, value2, "commonBankUnion");
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