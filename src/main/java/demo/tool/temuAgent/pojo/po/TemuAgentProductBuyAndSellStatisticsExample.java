package demo.tool.temuAgent.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemuAgentProductBuyAndSellStatisticsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TemuAgentProductBuyAndSellStatisticsExample() {
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

        public Criteria andModelIdIsNull() {
            addCriterion("model_id is null");
            return (Criteria) this;
        }

        public Criteria andModelIdIsNotNull() {
            addCriterion("model_id is not null");
            return (Criteria) this;
        }

        public Criteria andModelIdEqualTo(Long value) {
            addCriterion("model_id =", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotEqualTo(Long value) {
            addCriterion("model_id <>", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdGreaterThan(Long value) {
            addCriterion("model_id >", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdGreaterThanOrEqualTo(Long value) {
            addCriterion("model_id >=", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdLessThan(Long value) {
            addCriterion("model_id <", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdLessThanOrEqualTo(Long value) {
            addCriterion("model_id <=", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdIn(List<Long> values) {
            addCriterion("model_id in", values, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotIn(List<Long> values) {
            addCriterion("model_id not in", values, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdBetween(Long value1, Long value2) {
            addCriterion("model_id between", value1, value2, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotBetween(Long value1, Long value2) {
            addCriterion("model_id not between", value1, value2, "modelId");
            return (Criteria) this;
        }

        public Criteria andBuyCountingIsNull() {
            addCriterion("buy_counting is null");
            return (Criteria) this;
        }

        public Criteria andBuyCountingIsNotNull() {
            addCriterion("buy_counting is not null");
            return (Criteria) this;
        }

        public Criteria andBuyCountingEqualTo(Integer value) {
            addCriterion("buy_counting =", value, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingNotEqualTo(Integer value) {
            addCriterion("buy_counting <>", value, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingGreaterThan(Integer value) {
            addCriterion("buy_counting >", value, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("buy_counting >=", value, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingLessThan(Integer value) {
            addCriterion("buy_counting <", value, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingLessThanOrEqualTo(Integer value) {
            addCriterion("buy_counting <=", value, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingIn(List<Integer> values) {
            addCriterion("buy_counting in", values, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingNotIn(List<Integer> values) {
            addCriterion("buy_counting not in", values, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingBetween(Integer value1, Integer value2) {
            addCriterion("buy_counting between", value1, value2, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andBuyCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("buy_counting not between", value1, value2, "buyCounting");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceIsNull() {
            addCriterion("avg_buy_price is null");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceIsNotNull() {
            addCriterion("avg_buy_price is not null");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceEqualTo(BigDecimal value) {
            addCriterion("avg_buy_price =", value, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceNotEqualTo(BigDecimal value) {
            addCriterion("avg_buy_price <>", value, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceGreaterThan(BigDecimal value) {
            addCriterion("avg_buy_price >", value, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_buy_price >=", value, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceLessThan(BigDecimal value) {
            addCriterion("avg_buy_price <", value, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_buy_price <=", value, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceIn(List<BigDecimal> values) {
            addCriterion("avg_buy_price in", values, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceNotIn(List<BigDecimal> values) {
            addCriterion("avg_buy_price not in", values, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_buy_price between", value1, value2, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andAvgBuyPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_buy_price not between", value1, value2, "avgBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceIsNull() {
            addCriterion("highest_buy_price is null");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceIsNotNull() {
            addCriterion("highest_buy_price is not null");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceEqualTo(BigDecimal value) {
            addCriterion("highest_buy_price =", value, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceNotEqualTo(BigDecimal value) {
            addCriterion("highest_buy_price <>", value, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceGreaterThan(BigDecimal value) {
            addCriterion("highest_buy_price >", value, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("highest_buy_price >=", value, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceLessThan(BigDecimal value) {
            addCriterion("highest_buy_price <", value, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("highest_buy_price <=", value, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceIn(List<BigDecimal> values) {
            addCriterion("highest_buy_price in", values, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceNotIn(List<BigDecimal> values) {
            addCriterion("highest_buy_price not in", values, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("highest_buy_price between", value1, value2, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andHighestBuyPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("highest_buy_price not between", value1, value2, "highestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceIsNull() {
            addCriterion("lowest_buy_price is null");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceIsNotNull() {
            addCriterion("lowest_buy_price is not null");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceEqualTo(BigDecimal value) {
            addCriterion("lowest_buy_price =", value, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceNotEqualTo(BigDecimal value) {
            addCriterion("lowest_buy_price <>", value, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceGreaterThan(BigDecimal value) {
            addCriterion("lowest_buy_price >", value, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_buy_price >=", value, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceLessThan(BigDecimal value) {
            addCriterion("lowest_buy_price <", value, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_buy_price <=", value, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceIn(List<BigDecimal> values) {
            addCriterion("lowest_buy_price in", values, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceNotIn(List<BigDecimal> values) {
            addCriterion("lowest_buy_price not in", values, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_buy_price between", value1, value2, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLowestBuyPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_buy_price not between", value1, value2, "lowestBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceIsNull() {
            addCriterion("last_buy_price is null");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceIsNotNull() {
            addCriterion("last_buy_price is not null");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceEqualTo(BigDecimal value) {
            addCriterion("last_buy_price =", value, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceNotEqualTo(BigDecimal value) {
            addCriterion("last_buy_price <>", value, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceGreaterThan(BigDecimal value) {
            addCriterion("last_buy_price >", value, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("last_buy_price >=", value, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceLessThan(BigDecimal value) {
            addCriterion("last_buy_price <", value, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("last_buy_price <=", value, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceIn(List<BigDecimal> values) {
            addCriterion("last_buy_price in", values, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceNotIn(List<BigDecimal> values) {
            addCriterion("last_buy_price not in", values, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_buy_price between", value1, value2, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andLastBuyPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_buy_price not between", value1, value2, "lastBuyPrice");
            return (Criteria) this;
        }

        public Criteria andSellCountingIsNull() {
            addCriterion("sell_counting is null");
            return (Criteria) this;
        }

        public Criteria andSellCountingIsNotNull() {
            addCriterion("sell_counting is not null");
            return (Criteria) this;
        }

        public Criteria andSellCountingEqualTo(Integer value) {
            addCriterion("sell_counting =", value, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingNotEqualTo(Integer value) {
            addCriterion("sell_counting <>", value, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingGreaterThan(Integer value) {
            addCriterion("sell_counting >", value, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("sell_counting >=", value, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingLessThan(Integer value) {
            addCriterion("sell_counting <", value, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingLessThanOrEqualTo(Integer value) {
            addCriterion("sell_counting <=", value, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingIn(List<Integer> values) {
            addCriterion("sell_counting in", values, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingNotIn(List<Integer> values) {
            addCriterion("sell_counting not in", values, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingBetween(Integer value1, Integer value2) {
            addCriterion("sell_counting between", value1, value2, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andSellCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("sell_counting not between", value1, value2, "sellCounting");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceIsNull() {
            addCriterion("avg_sell_price is null");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceIsNotNull() {
            addCriterion("avg_sell_price is not null");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceEqualTo(BigDecimal value) {
            addCriterion("avg_sell_price =", value, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceNotEqualTo(BigDecimal value) {
            addCriterion("avg_sell_price <>", value, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceGreaterThan(BigDecimal value) {
            addCriterion("avg_sell_price >", value, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_sell_price >=", value, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceLessThan(BigDecimal value) {
            addCriterion("avg_sell_price <", value, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("avg_sell_price <=", value, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceIn(List<BigDecimal> values) {
            addCriterion("avg_sell_price in", values, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceNotIn(List<BigDecimal> values) {
            addCriterion("avg_sell_price not in", values, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_sell_price between", value1, value2, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andAvgSellPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("avg_sell_price not between", value1, value2, "avgSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceIsNull() {
            addCriterion("highest_sell_price is null");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceIsNotNull() {
            addCriterion("highest_sell_price is not null");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceEqualTo(BigDecimal value) {
            addCriterion("highest_sell_price =", value, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceNotEqualTo(BigDecimal value) {
            addCriterion("highest_sell_price <>", value, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceGreaterThan(BigDecimal value) {
            addCriterion("highest_sell_price >", value, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("highest_sell_price >=", value, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceLessThan(BigDecimal value) {
            addCriterion("highest_sell_price <", value, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("highest_sell_price <=", value, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceIn(List<BigDecimal> values) {
            addCriterion("highest_sell_price in", values, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceNotIn(List<BigDecimal> values) {
            addCriterion("highest_sell_price not in", values, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("highest_sell_price between", value1, value2, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andHighestSellPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("highest_sell_price not between", value1, value2, "highestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceIsNull() {
            addCriterion("lowest_sell_price is null");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceIsNotNull() {
            addCriterion("lowest_sell_price is not null");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceEqualTo(BigDecimal value) {
            addCriterion("lowest_sell_price =", value, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceNotEqualTo(BigDecimal value) {
            addCriterion("lowest_sell_price <>", value, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceGreaterThan(BigDecimal value) {
            addCriterion("lowest_sell_price >", value, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_sell_price >=", value, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceLessThan(BigDecimal value) {
            addCriterion("lowest_sell_price <", value, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_sell_price <=", value, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceIn(List<BigDecimal> values) {
            addCriterion("lowest_sell_price in", values, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceNotIn(List<BigDecimal> values) {
            addCriterion("lowest_sell_price not in", values, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_sell_price between", value1, value2, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLowestSellPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_sell_price not between", value1, value2, "lowestSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceIsNull() {
            addCriterion("last_sell_price is null");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceIsNotNull() {
            addCriterion("last_sell_price is not null");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceEqualTo(BigDecimal value) {
            addCriterion("last_sell_price =", value, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceNotEqualTo(BigDecimal value) {
            addCriterion("last_sell_price <>", value, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceGreaterThan(BigDecimal value) {
            addCriterion("last_sell_price >", value, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("last_sell_price >=", value, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceLessThan(BigDecimal value) {
            addCriterion("last_sell_price <", value, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("last_sell_price <=", value, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceIn(List<BigDecimal> values) {
            addCriterion("last_sell_price in", values, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceNotIn(List<BigDecimal> values) {
            addCriterion("last_sell_price not in", values, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_sell_price between", value1, value2, "lastSellPrice");
            return (Criteria) this;
        }

        public Criteria andLastSellPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("last_sell_price not between", value1, value2, "lastSellPrice");
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