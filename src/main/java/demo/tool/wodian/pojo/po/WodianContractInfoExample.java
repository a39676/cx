package demo.tool.wodian.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WodianContractInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WodianContractInfoExample() {
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

        public Criteria andClientIdIsNull() {
            addCriterion("client_id is null");
            return (Criteria) this;
        }

        public Criteria andClientIdIsNotNull() {
            addCriterion("client_id is not null");
            return (Criteria) this;
        }

        public Criteria andClientIdEqualTo(Long value) {
            addCriterion("client_id =", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotEqualTo(Long value) {
            addCriterion("client_id <>", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThan(Long value) {
            addCriterion("client_id >", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThanOrEqualTo(Long value) {
            addCriterion("client_id >=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThan(Long value) {
            addCriterion("client_id <", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThanOrEqualTo(Long value) {
            addCriterion("client_id <=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdIn(List<Long> values) {
            addCriterion("client_id in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotIn(List<Long> values) {
            addCriterion("client_id not in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdBetween(Long value1, Long value2) {
            addCriterion("client_id between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotBetween(Long value1, Long value2) {
            addCriterion("client_id not between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNull() {
            addCriterion("salesman_id is null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNotNull() {
            addCriterion("salesman_id is not null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdEqualTo(Long value) {
            addCriterion("salesman_id =", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotEqualTo(Long value) {
            addCriterion("salesman_id <>", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThan(Long value) {
            addCriterion("salesman_id >", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThanOrEqualTo(Long value) {
            addCriterion("salesman_id >=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThan(Long value) {
            addCriterion("salesman_id <", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThanOrEqualTo(Long value) {
            addCriterion("salesman_id <=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIn(List<Long> values) {
            addCriterion("salesman_id in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotIn(List<Long> values) {
            addCriterion("salesman_id not in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdBetween(Long value1, Long value2) {
            addCriterion("salesman_id between", value1, value2, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotBetween(Long value1, Long value2) {
            addCriterion("salesman_id not between", value1, value2, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdIsNull() {
            addCriterion("merchants_id is null");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdIsNotNull() {
            addCriterion("merchants_id is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdEqualTo(Long value) {
            addCriterion("merchants_id =", value, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdNotEqualTo(Long value) {
            addCriterion("merchants_id <>", value, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdGreaterThan(Long value) {
            addCriterion("merchants_id >", value, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("merchants_id >=", value, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdLessThan(Long value) {
            addCriterion("merchants_id <", value, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdLessThanOrEqualTo(Long value) {
            addCriterion("merchants_id <=", value, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdIn(List<Long> values) {
            addCriterion("merchants_id in", values, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdNotIn(List<Long> values) {
            addCriterion("merchants_id not in", values, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdBetween(Long value1, Long value2) {
            addCriterion("merchants_id between", value1, value2, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andMerchantsIdNotBetween(Long value1, Long value2) {
            addCriterion("merchants_id not between", value1, value2, "merchantsId");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeIsNull() {
            addCriterion("contract_create_time is null");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeIsNotNull() {
            addCriterion("contract_create_time is not null");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("contract_create_time =", value, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("contract_create_time <>", value, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("contract_create_time >", value, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("contract_create_time >=", value, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeLessThan(LocalDateTime value) {
            addCriterion("contract_create_time <", value, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("contract_create_time <=", value, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("contract_create_time in", values, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("contract_create_time not in", values, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("contract_create_time between", value1, value2, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("contract_create_time not between", value1, value2, "contractCreateTime");
            return (Criteria) this;
        }

        public Criteria andContractAmountIsNull() {
            addCriterion("contract_amount is null");
            return (Criteria) this;
        }

        public Criteria andContractAmountIsNotNull() {
            addCriterion("contract_amount is not null");
            return (Criteria) this;
        }

        public Criteria andContractAmountEqualTo(BigDecimal value) {
            addCriterion("contract_amount =", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountNotEqualTo(BigDecimal value) {
            addCriterion("contract_amount <>", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountGreaterThan(BigDecimal value) {
            addCriterion("contract_amount >", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_amount >=", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountLessThan(BigDecimal value) {
            addCriterion("contract_amount <", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_amount <=", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountIn(List<BigDecimal> values) {
            addCriterion("contract_amount in", values, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountNotIn(List<BigDecimal> values) {
            addCriterion("contract_amount not in", values, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_amount between", value1, value2, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_amount not between", value1, value2, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientIsNull() {
            addCriterion("goldcoin_for_client is null");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientIsNotNull() {
            addCriterion("goldcoin_for_client is not null");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_client =", value, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientNotEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_client <>", value, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientGreaterThan(BigDecimal value) {
            addCriterion("goldcoin_for_client >", value, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_client >=", value, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientLessThan(BigDecimal value) {
            addCriterion("goldcoin_for_client <", value, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientLessThanOrEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_client <=", value, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientIn(List<BigDecimal> values) {
            addCriterion("goldcoin_for_client in", values, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientNotIn(List<BigDecimal> values) {
            addCriterion("goldcoin_for_client not in", values, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goldcoin_for_client between", value1, value2, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForClientNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goldcoin_for_client not between", value1, value2, "goldcoinForClient");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsIsNull() {
            addCriterion("goldcoin_for_merchants is null");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsIsNotNull() {
            addCriterion("goldcoin_for_merchants is not null");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_merchants =", value, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsNotEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_merchants <>", value, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsGreaterThan(BigDecimal value) {
            addCriterion("goldcoin_for_merchants >", value, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_merchants >=", value, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsLessThan(BigDecimal value) {
            addCriterion("goldcoin_for_merchants <", value, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("goldcoin_for_merchants <=", value, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsIn(List<BigDecimal> values) {
            addCriterion("goldcoin_for_merchants in", values, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsNotIn(List<BigDecimal> values) {
            addCriterion("goldcoin_for_merchants not in", values, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goldcoin_for_merchants between", value1, value2, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andGoldcoinForMerchantsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goldcoin_for_merchants not between", value1, value2, "goldcoinForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientIsNull() {
            addCriterion("integral_for_client is null");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientIsNotNull() {
            addCriterion("integral_for_client is not null");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientEqualTo(BigDecimal value) {
            addCriterion("integral_for_client =", value, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientNotEqualTo(BigDecimal value) {
            addCriterion("integral_for_client <>", value, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientGreaterThan(BigDecimal value) {
            addCriterion("integral_for_client >", value, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("integral_for_client >=", value, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientLessThan(BigDecimal value) {
            addCriterion("integral_for_client <", value, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientLessThanOrEqualTo(BigDecimal value) {
            addCriterion("integral_for_client <=", value, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientIn(List<BigDecimal> values) {
            addCriterion("integral_for_client in", values, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientNotIn(List<BigDecimal> values) {
            addCriterion("integral_for_client not in", values, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integral_for_client between", value1, value2, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForClientNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integral_for_client not between", value1, value2, "integralForClient");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsIsNull() {
            addCriterion("integral_for_merchants is null");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsIsNotNull() {
            addCriterion("integral_for_merchants is not null");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsEqualTo(BigDecimal value) {
            addCriterion("integral_for_merchants =", value, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsNotEqualTo(BigDecimal value) {
            addCriterion("integral_for_merchants <>", value, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsGreaterThan(BigDecimal value) {
            addCriterion("integral_for_merchants >", value, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("integral_for_merchants >=", value, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsLessThan(BigDecimal value) {
            addCriterion("integral_for_merchants <", value, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("integral_for_merchants <=", value, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsIn(List<BigDecimal> values) {
            addCriterion("integral_for_merchants in", values, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsNotIn(List<BigDecimal> values) {
            addCriterion("integral_for_merchants not in", values, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integral_for_merchants between", value1, value2, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andIntegralForMerchantsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("integral_for_merchants not between", value1, value2, "integralForMerchants");
            return (Criteria) this;
        }

        public Criteria andPartCountsIsNull() {
            addCriterion("part_counts is null");
            return (Criteria) this;
        }

        public Criteria andPartCountsIsNotNull() {
            addCriterion("part_counts is not null");
            return (Criteria) this;
        }

        public Criteria andPartCountsEqualTo(Integer value) {
            addCriterion("part_counts =", value, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsNotEqualTo(Integer value) {
            addCriterion("part_counts <>", value, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsGreaterThan(Integer value) {
            addCriterion("part_counts >", value, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("part_counts >=", value, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsLessThan(Integer value) {
            addCriterion("part_counts <", value, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsLessThanOrEqualTo(Integer value) {
            addCriterion("part_counts <=", value, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsIn(List<Integer> values) {
            addCriterion("part_counts in", values, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsNotIn(List<Integer> values) {
            addCriterion("part_counts not in", values, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsBetween(Integer value1, Integer value2) {
            addCriterion("part_counts between", value1, value2, "partCounts");
            return (Criteria) this;
        }

        public Criteria andPartCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("part_counts not between", value1, value2, "partCounts");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(BigDecimal value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(BigDecimal value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(BigDecimal value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(BigDecimal value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<BigDecimal> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<BigDecimal> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("version not between", value1, value2, "version");
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