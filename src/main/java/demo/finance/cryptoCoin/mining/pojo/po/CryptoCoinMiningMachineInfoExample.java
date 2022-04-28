package demo.finance.cryptoCoin.mining.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CryptoCoinMiningMachineInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CryptoCoinMiningMachineInfoExample() {
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

        public Criteria andMiningMachineIdIsNull() {
            addCriterion("mining_machine_id is null");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdIsNotNull() {
            addCriterion("mining_machine_id is not null");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdEqualTo(Long value) {
            addCriterion("mining_machine_id =", value, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdNotEqualTo(Long value) {
            addCriterion("mining_machine_id <>", value, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdGreaterThan(Long value) {
            addCriterion("mining_machine_id >", value, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdGreaterThanOrEqualTo(Long value) {
            addCriterion("mining_machine_id >=", value, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdLessThan(Long value) {
            addCriterion("mining_machine_id <", value, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdLessThanOrEqualTo(Long value) {
            addCriterion("mining_machine_id <=", value, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdIn(List<Long> values) {
            addCriterion("mining_machine_id in", values, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdNotIn(List<Long> values) {
            addCriterion("mining_machine_id not in", values, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdBetween(Long value1, Long value2) {
            addCriterion("mining_machine_id between", value1, value2, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andMiningMachineIdNotBetween(Long value1, Long value2) {
            addCriterion("mining_machine_id not between", value1, value2, "miningMachineId");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateIsNull() {
            addCriterion("handling_fee_rate is null");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateIsNotNull() {
            addCriterion("handling_fee_rate is not null");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateEqualTo(BigDecimal value) {
            addCriterion("handling_fee_rate =", value, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateNotEqualTo(BigDecimal value) {
            addCriterion("handling_fee_rate <>", value, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateGreaterThan(BigDecimal value) {
            addCriterion("handling_fee_rate >", value, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("handling_fee_rate >=", value, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateLessThan(BigDecimal value) {
            addCriterion("handling_fee_rate <", value, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("handling_fee_rate <=", value, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateIn(List<BigDecimal> values) {
            addCriterion("handling_fee_rate in", values, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateNotIn(List<BigDecimal> values) {
            addCriterion("handling_fee_rate not in", values, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("handling_fee_rate between", value1, value2, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andHandlingFeeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("handling_fee_rate not between", value1, value2, "handlingFeeRate");
            return (Criteria) this;
        }

        public Criteria andPartingCountIsNull() {
            addCriterion("parting_count is null");
            return (Criteria) this;
        }

        public Criteria andPartingCountIsNotNull() {
            addCriterion("parting_count is not null");
            return (Criteria) this;
        }

        public Criteria andPartingCountEqualTo(Integer value) {
            addCriterion("parting_count =", value, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountNotEqualTo(Integer value) {
            addCriterion("parting_count <>", value, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountGreaterThan(Integer value) {
            addCriterion("parting_count >", value, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("parting_count >=", value, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountLessThan(Integer value) {
            addCriterion("parting_count <", value, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountLessThanOrEqualTo(Integer value) {
            addCriterion("parting_count <=", value, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountIn(List<Integer> values) {
            addCriterion("parting_count in", values, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountNotIn(List<Integer> values) {
            addCriterion("parting_count not in", values, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountBetween(Integer value1, Integer value2) {
            addCriterion("parting_count between", value1, value2, "partingCount");
            return (Criteria) this;
        }

        public Criteria andPartingCountNotBetween(Integer value1, Integer value2) {
            addCriterion("parting_count not between", value1, value2, "partingCount");
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

        public Criteria andUdpateTimeIsNull() {
            addCriterion("udpate_time is null");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeIsNotNull() {
            addCriterion("udpate_time is not null");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeEqualTo(LocalDateTime value) {
            addCriterion("udpate_time =", value, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("udpate_time <>", value, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeGreaterThan(LocalDateTime value) {
            addCriterion("udpate_time >", value, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("udpate_time >=", value, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeLessThan(LocalDateTime value) {
            addCriterion("udpate_time <", value, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("udpate_time <=", value, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeIn(List<LocalDateTime> values) {
            addCriterion("udpate_time in", values, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("udpate_time not in", values, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("udpate_time between", value1, value2, "udpateTime");
            return (Criteria) this;
        }

        public Criteria andUdpateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("udpate_time not between", value1, value2, "udpateTime");
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