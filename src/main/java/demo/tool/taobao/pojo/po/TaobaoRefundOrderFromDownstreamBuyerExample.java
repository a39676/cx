package demo.tool.taobao.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaobaoRefundOrderFromDownstreamBuyerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaobaoRefundOrderFromDownstreamBuyerExample() {
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

        public Criteria andRefundOrderIdIsNull() {
            addCriterion("refund_order_id is null");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdIsNotNull() {
            addCriterion("refund_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdEqualTo(Long value) {
            addCriterion("refund_order_id =", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdNotEqualTo(Long value) {
            addCriterion("refund_order_id <>", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdGreaterThan(Long value) {
            addCriterion("refund_order_id >", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("refund_order_id >=", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdLessThan(Long value) {
            addCriterion("refund_order_id <", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("refund_order_id <=", value, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdIn(List<Long> values) {
            addCriterion("refund_order_id in", values, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdNotIn(List<Long> values) {
            addCriterion("refund_order_id not in", values, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdBetween(Long value1, Long value2) {
            addCriterion("refund_order_id between", value1, value2, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andRefundOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("refund_order_id not between", value1, value2, "refundOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdIsNull() {
            addCriterion("source_order_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdIsNotNull() {
            addCriterion("source_order_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdEqualTo(Long value) {
            addCriterion("source_order_id =", value, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdNotEqualTo(Long value) {
            addCriterion("source_order_id <>", value, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdGreaterThan(Long value) {
            addCriterion("source_order_id >", value, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("source_order_id >=", value, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdLessThan(Long value) {
            addCriterion("source_order_id <", value, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("source_order_id <=", value, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdIn(List<Long> values) {
            addCriterion("source_order_id in", values, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdNotIn(List<Long> values) {
            addCriterion("source_order_id not in", values, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdBetween(Long value1, Long value2) {
            addCriterion("source_order_id between", value1, value2, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andSourceOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("source_order_id not between", value1, value2, "sourceOrderId");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeIsNull() {
            addCriterion("refund_create_time is null");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeIsNotNull() {
            addCriterion("refund_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("refund_create_time =", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("refund_create_time <>", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("refund_create_time >", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("refund_create_time >=", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeLessThan(LocalDateTime value) {
            addCriterion("refund_create_time <", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("refund_create_time <=", value, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("refund_create_time in", values, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("refund_create_time not in", values, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("refund_create_time between", value1, value2, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRefundCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("refund_create_time not between", value1, value2, "refundCreateTime");
            return (Criteria) this;
        }

        public Criteria andRegionId1IsNull() {
            addCriterion("region_id_1 is null");
            return (Criteria) this;
        }

        public Criteria andRegionId1IsNotNull() {
            addCriterion("region_id_1 is not null");
            return (Criteria) this;
        }

        public Criteria andRegionId1EqualTo(Integer value) {
            addCriterion("region_id_1 =", value, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1NotEqualTo(Integer value) {
            addCriterion("region_id_1 <>", value, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1GreaterThan(Integer value) {
            addCriterion("region_id_1 >", value, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1GreaterThanOrEqualTo(Integer value) {
            addCriterion("region_id_1 >=", value, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1LessThan(Integer value) {
            addCriterion("region_id_1 <", value, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1LessThanOrEqualTo(Integer value) {
            addCriterion("region_id_1 <=", value, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1In(List<Integer> values) {
            addCriterion("region_id_1 in", values, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1NotIn(List<Integer> values) {
            addCriterion("region_id_1 not in", values, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1Between(Integer value1, Integer value2) {
            addCriterion("region_id_1 between", value1, value2, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId1NotBetween(Integer value1, Integer value2) {
            addCriterion("region_id_1 not between", value1, value2, "regionId1");
            return (Criteria) this;
        }

        public Criteria andRegionId2IsNull() {
            addCriterion("region_id_2 is null");
            return (Criteria) this;
        }

        public Criteria andRegionId2IsNotNull() {
            addCriterion("region_id_2 is not null");
            return (Criteria) this;
        }

        public Criteria andRegionId2EqualTo(Integer value) {
            addCriterion("region_id_2 =", value, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2NotEqualTo(Integer value) {
            addCriterion("region_id_2 <>", value, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2GreaterThan(Integer value) {
            addCriterion("region_id_2 >", value, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2GreaterThanOrEqualTo(Integer value) {
            addCriterion("region_id_2 >=", value, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2LessThan(Integer value) {
            addCriterion("region_id_2 <", value, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2LessThanOrEqualTo(Integer value) {
            addCriterion("region_id_2 <=", value, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2In(List<Integer> values) {
            addCriterion("region_id_2 in", values, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2NotIn(List<Integer> values) {
            addCriterion("region_id_2 not in", values, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2Between(Integer value1, Integer value2) {
            addCriterion("region_id_2 between", value1, value2, "regionId2");
            return (Criteria) this;
        }

        public Criteria andRegionId2NotBetween(Integer value1, Integer value2) {
            addCriterion("region_id_2 not between", value1, value2, "regionId2");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdIsNull() {
            addCriterion("after_transit_region_id is null");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdIsNotNull() {
            addCriterion("after_transit_region_id is not null");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdEqualTo(Integer value) {
            addCriterion("after_transit_region_id =", value, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdNotEqualTo(Integer value) {
            addCriterion("after_transit_region_id <>", value, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdGreaterThan(Integer value) {
            addCriterion("after_transit_region_id >", value, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("after_transit_region_id >=", value, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdLessThan(Integer value) {
            addCriterion("after_transit_region_id <", value, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdLessThanOrEqualTo(Integer value) {
            addCriterion("after_transit_region_id <=", value, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdIn(List<Integer> values) {
            addCriterion("after_transit_region_id in", values, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdNotIn(List<Integer> values) {
            addCriterion("after_transit_region_id not in", values, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdBetween(Integer value1, Integer value2) {
            addCriterion("after_transit_region_id between", value1, value2, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andAfterTransitRegionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("after_transit_region_id not between", value1, value2, "afterTransitRegionId");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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