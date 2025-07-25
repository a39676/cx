package demo.tool.temuAgent.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemuAgentProductFlowStatisticsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TemuAgentProductFlowStatisticsExample() {
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

        public Criteria andStockingCountingIsNull() {
            addCriterion("stocking_counting is null");
            return (Criteria) this;
        }

        public Criteria andStockingCountingIsNotNull() {
            addCriterion("stocking_counting is not null");
            return (Criteria) this;
        }

        public Criteria andStockingCountingEqualTo(Integer value) {
            addCriterion("stocking_counting =", value, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingNotEqualTo(Integer value) {
            addCriterion("stocking_counting <>", value, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingGreaterThan(Integer value) {
            addCriterion("stocking_counting >", value, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("stocking_counting >=", value, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingLessThan(Integer value) {
            addCriterion("stocking_counting <", value, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingLessThanOrEqualTo(Integer value) {
            addCriterion("stocking_counting <=", value, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingIn(List<Integer> values) {
            addCriterion("stocking_counting in", values, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingNotIn(List<Integer> values) {
            addCriterion("stocking_counting not in", values, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingBetween(Integer value1, Integer value2) {
            addCriterion("stocking_counting between", value1, value2, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("stocking_counting not between", value1, value2, "stockingCounting");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeIsNull() {
            addCriterion("stocking_update_time is null");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeIsNotNull() {
            addCriterion("stocking_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("stocking_update_time =", value, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("stocking_update_time <>", value, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("stocking_update_time >", value, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("stocking_update_time >=", value, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("stocking_update_time <", value, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("stocking_update_time <=", value, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("stocking_update_time in", values, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("stocking_update_time not in", values, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("stocking_update_time between", value1, value2, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andStockingUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("stocking_update_time not between", value1, value2, "stockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingIsNull() {
            addCriterion("international_stocking_counting is null");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingIsNotNull() {
            addCriterion("international_stocking_counting is not null");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingEqualTo(Integer value) {
            addCriterion("international_stocking_counting =", value, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingNotEqualTo(Integer value) {
            addCriterion("international_stocking_counting <>", value, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingGreaterThan(Integer value) {
            addCriterion("international_stocking_counting >", value, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("international_stocking_counting >=", value, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingLessThan(Integer value) {
            addCriterion("international_stocking_counting <", value, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingLessThanOrEqualTo(Integer value) {
            addCriterion("international_stocking_counting <=", value, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingIn(List<Integer> values) {
            addCriterion("international_stocking_counting in", values, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingNotIn(List<Integer> values) {
            addCriterion("international_stocking_counting not in", values, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingBetween(Integer value1, Integer value2) {
            addCriterion("international_stocking_counting between", value1, value2, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("international_stocking_counting not between", value1, value2, "internationalStockingCounting");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeIsNull() {
            addCriterion("international_stocking_update_time is null");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeIsNotNull() {
            addCriterion("international_stocking_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("international_stocking_update_time =", value, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("international_stocking_update_time <>", value, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("international_stocking_update_time >", value, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("international_stocking_update_time >=", value, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("international_stocking_update_time <", value, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("international_stocking_update_time <=", value, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("international_stocking_update_time in", values, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("international_stocking_update_time not in", values, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("international_stocking_update_time between", value1, value2, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andInternationalStockingUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("international_stocking_update_time not between", value1, value2, "internationalStockingUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledCountingIsNull() {
            addCriterion("selled_counting is null");
            return (Criteria) this;
        }

        public Criteria andSelledCountingIsNotNull() {
            addCriterion("selled_counting is not null");
            return (Criteria) this;
        }

        public Criteria andSelledCountingEqualTo(Integer value) {
            addCriterion("selled_counting =", value, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingNotEqualTo(Integer value) {
            addCriterion("selled_counting <>", value, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingGreaterThan(Integer value) {
            addCriterion("selled_counting >", value, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("selled_counting >=", value, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingLessThan(Integer value) {
            addCriterion("selled_counting <", value, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingLessThanOrEqualTo(Integer value) {
            addCriterion("selled_counting <=", value, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingIn(List<Integer> values) {
            addCriterion("selled_counting in", values, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingNotIn(List<Integer> values) {
            addCriterion("selled_counting not in", values, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingBetween(Integer value1, Integer value2) {
            addCriterion("selled_counting between", value1, value2, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("selled_counting not between", value1, value2, "selledCounting");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeIsNull() {
            addCriterion("selled_update_time is null");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeIsNotNull() {
            addCriterion("selled_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("selled_update_time =", value, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("selled_update_time <>", value, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("selled_update_time >", value, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("selled_update_time >=", value, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("selled_update_time <", value, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("selled_update_time <=", value, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("selled_update_time in", values, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("selled_update_time not in", values, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("selled_update_time between", value1, value2, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andSelledUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("selled_update_time not between", value1, value2, "selledUpdateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingIsNull() {
            addCriterion("repackage_counting is null");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingIsNotNull() {
            addCriterion("repackage_counting is not null");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingEqualTo(Integer value) {
            addCriterion("repackage_counting =", value, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingNotEqualTo(Integer value) {
            addCriterion("repackage_counting <>", value, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingGreaterThan(Integer value) {
            addCriterion("repackage_counting >", value, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingGreaterThanOrEqualTo(Integer value) {
            addCriterion("repackage_counting >=", value, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingLessThan(Integer value) {
            addCriterion("repackage_counting <", value, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingLessThanOrEqualTo(Integer value) {
            addCriterion("repackage_counting <=", value, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingIn(List<Integer> values) {
            addCriterion("repackage_counting in", values, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingNotIn(List<Integer> values) {
            addCriterion("repackage_counting not in", values, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingBetween(Integer value1, Integer value2) {
            addCriterion("repackage_counting between", value1, value2, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageCountingNotBetween(Integer value1, Integer value2) {
            addCriterion("repackage_counting not between", value1, value2, "repackageCounting");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeIsNull() {
            addCriterion("repackage_udpate_time is null");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeIsNotNull() {
            addCriterion("repackage_udpate_time is not null");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeEqualTo(LocalDateTime value) {
            addCriterion("repackage_udpate_time =", value, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("repackage_udpate_time <>", value, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeGreaterThan(LocalDateTime value) {
            addCriterion("repackage_udpate_time >", value, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("repackage_udpate_time >=", value, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeLessThan(LocalDateTime value) {
            addCriterion("repackage_udpate_time <", value, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("repackage_udpate_time <=", value, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeIn(List<LocalDateTime> values) {
            addCriterion("repackage_udpate_time in", values, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("repackage_udpate_time not in", values, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("repackage_udpate_time between", value1, value2, "repackageUdpateTime");
            return (Criteria) this;
        }

        public Criteria andRepackageUdpateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("repackage_udpate_time not between", value1, value2, "repackageUdpateTime");
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