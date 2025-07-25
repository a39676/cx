package demo.tool.temuAgent.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemuAgentProductModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TemuAgentProductModelExample() {
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

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Long value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Long value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Long value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Long value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Long value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Long value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Long> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Long> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Long value1, Long value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Long value1, Long value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIsNull() {
            addCriterion("unit_type_code is null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIsNotNull() {
            addCriterion("unit_type_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeEqualTo(Integer value) {
            addCriterion("unit_type_code =", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotEqualTo(Integer value) {
            addCriterion("unit_type_code <>", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeGreaterThan(Integer value) {
            addCriterion("unit_type_code >", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_type_code >=", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLessThan(Integer value) {
            addCriterion("unit_type_code <", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeLessThanOrEqualTo(Integer value) {
            addCriterion("unit_type_code <=", value, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeIn(List<Integer> values) {
            addCriterion("unit_type_code in", values, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotIn(List<Integer> values) {
            addCriterion("unit_type_code not in", values, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_code between", value1, value2, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitTypeCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_type_code not between", value1, value2, "unitTypeCode");
            return (Criteria) this;
        }

        public Criteria andUnitCountingIsNull() {
            addCriterion("unit_counting is null");
            return (Criteria) this;
        }

        public Criteria andUnitCountingIsNotNull() {
            addCriterion("unit_counting is not null");
            return (Criteria) this;
        }

        public Criteria andUnitCountingEqualTo(Integer value) {
            addCriterion("unit_counting =", value, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingNotEqualTo(Integer value) {
            addCriterion("unit_counting <>", value, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingGreaterThan(Integer value) {
            addCriterion("unit_counting >", value, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("unit_counting >=", value, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingLessThan(Integer value) {
            addCriterion("unit_counting <", value, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingLessThanOrEqualTo(Integer value) {
            addCriterion("unit_counting <=", value, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingIn(List<Integer> values) {
            addCriterion("unit_counting in", values, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingNotIn(List<Integer> values) {
            addCriterion("unit_counting not in", values, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingBetween(Integer value1, Integer value2) {
            addCriterion("unit_counting between", value1, value2, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andUnitCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("unit_counting not between", value1, value2, "unitCounting");
            return (Criteria) this;
        }

        public Criteria andSpuIsNull() {
            addCriterion("spu is null");
            return (Criteria) this;
        }

        public Criteria andSpuIsNotNull() {
            addCriterion("spu is not null");
            return (Criteria) this;
        }

        public Criteria andSpuEqualTo(String value) {
            addCriterion("spu =", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuNotEqualTo(String value) {
            addCriterion("spu <>", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuGreaterThan(String value) {
            addCriterion("spu >", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuGreaterThanOrEqualTo(String value) {
            addCriterion("spu >=", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuLessThan(String value) {
            addCriterion("spu <", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuLessThanOrEqualTo(String value) {
            addCriterion("spu <=", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuLike(String value) {
            addCriterion("spu like", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuNotLike(String value) {
            addCriterion("spu not like", value, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuIn(List<String> values) {
            addCriterion("spu in", values, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuNotIn(List<String> values) {
            addCriterion("spu not in", values, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuBetween(String value1, String value2) {
            addCriterion("spu between", value1, value2, "spu");
            return (Criteria) this;
        }

        public Criteria andSpuNotBetween(String value1, String value2) {
            addCriterion("spu not between", value1, value2, "spu");
            return (Criteria) this;
        }

        public Criteria andSkuIsNull() {
            addCriterion("sku is null");
            return (Criteria) this;
        }

        public Criteria andSkuIsNotNull() {
            addCriterion("sku is not null");
            return (Criteria) this;
        }

        public Criteria andSkuEqualTo(String value) {
            addCriterion("sku =", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotEqualTo(String value) {
            addCriterion("sku <>", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThan(String value) {
            addCriterion("sku >", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThanOrEqualTo(String value) {
            addCriterion("sku >=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThan(String value) {
            addCriterion("sku <", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThanOrEqualTo(String value) {
            addCriterion("sku <=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLike(String value) {
            addCriterion("sku like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotLike(String value) {
            addCriterion("sku not like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuIn(List<String> values) {
            addCriterion("sku in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotIn(List<String> values) {
            addCriterion("sku not in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuBetween(String value1, String value2) {
            addCriterion("sku between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotBetween(String value1, String value2) {
            addCriterion("sku not between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andSkcIsNull() {
            addCriterion("skc is null");
            return (Criteria) this;
        }

        public Criteria andSkcIsNotNull() {
            addCriterion("skc is not null");
            return (Criteria) this;
        }

        public Criteria andSkcEqualTo(String value) {
            addCriterion("skc =", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcNotEqualTo(String value) {
            addCriterion("skc <>", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcGreaterThan(String value) {
            addCriterion("skc >", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcGreaterThanOrEqualTo(String value) {
            addCriterion("skc >=", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcLessThan(String value) {
            addCriterion("skc <", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcLessThanOrEqualTo(String value) {
            addCriterion("skc <=", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcLike(String value) {
            addCriterion("skc like", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcNotLike(String value) {
            addCriterion("skc not like", value, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcIn(List<String> values) {
            addCriterion("skc in", values, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcNotIn(List<String> values) {
            addCriterion("skc not in", values, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcBetween(String value1, String value2) {
            addCriterion("skc between", value1, value2, "skc");
            return (Criteria) this;
        }

        public Criteria andSkcNotBetween(String value1, String value2) {
            addCriterion("skc not between", value1, value2, "skc");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNull() {
            addCriterion("purchase_price is null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNotNull() {
            addCriterion("purchase_price is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceEqualTo(BigDecimal value) {
            addCriterion("purchase_price =", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotEqualTo(BigDecimal value) {
            addCriterion("purchase_price <>", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThan(BigDecimal value) {
            addCriterion("purchase_price >", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price >=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThan(BigDecimal value) {
            addCriterion("purchase_price <", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price <=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIn(List<BigDecimal> values) {
            addCriterion("purchase_price in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotIn(List<BigDecimal> values) {
            addCriterion("purchase_price not in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price not between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceIsNull() {
            addCriterion("decleared_price is null");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceIsNotNull() {
            addCriterion("decleared_price is not null");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceEqualTo(BigDecimal value) {
            addCriterion("decleared_price =", value, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceNotEqualTo(BigDecimal value) {
            addCriterion("decleared_price <>", value, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceGreaterThan(BigDecimal value) {
            addCriterion("decleared_price >", value, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("decleared_price >=", value, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceLessThan(BigDecimal value) {
            addCriterion("decleared_price <", value, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("decleared_price <=", value, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceIn(List<BigDecimal> values) {
            addCriterion("decleared_price in", values, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceNotIn(List<BigDecimal> values) {
            addCriterion("decleared_price not in", values, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("decleared_price between", value1, value2, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andDeclearedPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("decleared_price not between", value1, value2, "declearedPrice");
            return (Criteria) this;
        }

        public Criteria andPackingFeeIsNull() {
            addCriterion("packing_fee is null");
            return (Criteria) this;
        }

        public Criteria andPackingFeeIsNotNull() {
            addCriterion("packing_fee is not null");
            return (Criteria) this;
        }

        public Criteria andPackingFeeEqualTo(BigDecimal value) {
            addCriterion("packing_fee =", value, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeNotEqualTo(BigDecimal value) {
            addCriterion("packing_fee <>", value, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeGreaterThan(BigDecimal value) {
            addCriterion("packing_fee >", value, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("packing_fee >=", value, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeLessThan(BigDecimal value) {
            addCriterion("packing_fee <", value, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("packing_fee <=", value, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeIn(List<BigDecimal> values) {
            addCriterion("packing_fee in", values, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeNotIn(List<BigDecimal> values) {
            addCriterion("packing_fee not in", values, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("packing_fee between", value1, value2, "packingFee");
            return (Criteria) this;
        }

        public Criteria andPackingFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("packing_fee not between", value1, value2, "packingFee");
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