package demo.tool.taobao.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaobaoProductSourceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaobaoProductSourceExample() {
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

        public Criteria andCommodityIdIsNull() {
            addCriterion("commodity_id is null");
            return (Criteria) this;
        }

        public Criteria andCommodityIdIsNotNull() {
            addCriterion("commodity_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityIdEqualTo(Long value) {
            addCriterion("commodity_id =", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdNotEqualTo(Long value) {
            addCriterion("commodity_id <>", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdGreaterThan(Long value) {
            addCriterion("commodity_id >", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdGreaterThanOrEqualTo(Long value) {
            addCriterion("commodity_id >=", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdLessThan(Long value) {
            addCriterion("commodity_id <", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdLessThanOrEqualTo(Long value) {
            addCriterion("commodity_id <=", value, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdIn(List<Long> values) {
            addCriterion("commodity_id in", values, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdNotIn(List<Long> values) {
            addCriterion("commodity_id not in", values, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdBetween(Long value1, Long value2) {
            addCriterion("commodity_id between", value1, value2, "commodityId");
            return (Criteria) this;
        }

        public Criteria andCommodityIdNotBetween(Long value1, Long value2) {
            addCriterion("commodity_id not between", value1, value2, "commodityId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNull() {
            addCriterion("source_id is null");
            return (Criteria) this;
        }

        public Criteria andSourceIdIsNotNull() {
            addCriterion("source_id is not null");
            return (Criteria) this;
        }

        public Criteria andSourceIdEqualTo(Long value) {
            addCriterion("source_id =", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotEqualTo(Long value) {
            addCriterion("source_id <>", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThan(Long value) {
            addCriterion("source_id >", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("source_id >=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThan(Long value) {
            addCriterion("source_id <", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdLessThanOrEqualTo(Long value) {
            addCriterion("source_id <=", value, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdIn(List<Long> values) {
            addCriterion("source_id in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotIn(List<Long> values) {
            addCriterion("source_id not in", values, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdBetween(Long value1, Long value2) {
            addCriterion("source_id between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andSourceIdNotBetween(Long value1, Long value2) {
            addCriterion("source_id not between", value1, value2, "sourceId");
            return (Criteria) this;
        }

        public Criteria andCommodityNameIsNull() {
            addCriterion("commodity_name is null");
            return (Criteria) this;
        }

        public Criteria andCommodityNameIsNotNull() {
            addCriterion("commodity_name is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEqualTo(String value) {
            addCriterion("commodity_name =", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameNotEqualTo(String value) {
            addCriterion("commodity_name <>", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameGreaterThan(String value) {
            addCriterion("commodity_name >", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameGreaterThanOrEqualTo(String value) {
            addCriterion("commodity_name >=", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameLessThan(String value) {
            addCriterion("commodity_name <", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameLessThanOrEqualTo(String value) {
            addCriterion("commodity_name <=", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameLike(String value) {
            addCriterion("commodity_name like", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameNotLike(String value) {
            addCriterion("commodity_name not like", value, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameIn(List<String> values) {
            addCriterion("commodity_name in", values, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameNotIn(List<String> values) {
            addCriterion("commodity_name not in", values, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameBetween(String value1, String value2) {
            addCriterion("commodity_name between", value1, value2, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameNotBetween(String value1, String value2) {
            addCriterion("commodity_name not between", value1, value2, "commodityName");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwIsNull() {
            addCriterion("commodity_name_zh_tw is null");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwIsNotNull() {
            addCriterion("commodity_name_zh_tw is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwEqualTo(String value) {
            addCriterion("commodity_name_zh_tw =", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwNotEqualTo(String value) {
            addCriterion("commodity_name_zh_tw <>", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwGreaterThan(String value) {
            addCriterion("commodity_name_zh_tw >", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwGreaterThanOrEqualTo(String value) {
            addCriterion("commodity_name_zh_tw >=", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwLessThan(String value) {
            addCriterion("commodity_name_zh_tw <", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwLessThanOrEqualTo(String value) {
            addCriterion("commodity_name_zh_tw <=", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwLike(String value) {
            addCriterion("commodity_name_zh_tw like", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwNotLike(String value) {
            addCriterion("commodity_name_zh_tw not like", value, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwIn(List<String> values) {
            addCriterion("commodity_name_zh_tw in", values, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwNotIn(List<String> values) {
            addCriterion("commodity_name_zh_tw not in", values, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwBetween(String value1, String value2) {
            addCriterion("commodity_name_zh_tw between", value1, value2, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameZhTwNotBetween(String value1, String value2) {
            addCriterion("commodity_name_zh_tw not between", value1, value2, "commodityNameZhTw");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnIsNull() {
            addCriterion("commodity_name_en is null");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnIsNotNull() {
            addCriterion("commodity_name_en is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnEqualTo(String value) {
            addCriterion("commodity_name_en =", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnNotEqualTo(String value) {
            addCriterion("commodity_name_en <>", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnGreaterThan(String value) {
            addCriterion("commodity_name_en >", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("commodity_name_en >=", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnLessThan(String value) {
            addCriterion("commodity_name_en <", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnLessThanOrEqualTo(String value) {
            addCriterion("commodity_name_en <=", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnLike(String value) {
            addCriterion("commodity_name_en like", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnNotLike(String value) {
            addCriterion("commodity_name_en not like", value, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnIn(List<String> values) {
            addCriterion("commodity_name_en in", values, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnNotIn(List<String> values) {
            addCriterion("commodity_name_en not in", values, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnBetween(String value1, String value2) {
            addCriterion("commodity_name_en between", value1, value2, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityNameEnNotBetween(String value1, String value2) {
            addCriterion("commodity_name_en not between", value1, value2, "commodityNameEn");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameIsNull() {
            addCriterion("commodity_img_name is null");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameIsNotNull() {
            addCriterion("commodity_img_name is not null");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameEqualTo(String value) {
            addCriterion("commodity_img_name =", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameNotEqualTo(String value) {
            addCriterion("commodity_img_name <>", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameGreaterThan(String value) {
            addCriterion("commodity_img_name >", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameGreaterThanOrEqualTo(String value) {
            addCriterion("commodity_img_name >=", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameLessThan(String value) {
            addCriterion("commodity_img_name <", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameLessThanOrEqualTo(String value) {
            addCriterion("commodity_img_name <=", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameLike(String value) {
            addCriterion("commodity_img_name like", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameNotLike(String value) {
            addCriterion("commodity_img_name not like", value, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameIn(List<String> values) {
            addCriterion("commodity_img_name in", values, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameNotIn(List<String> values) {
            addCriterion("commodity_img_name not in", values, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameBetween(String value1, String value2) {
            addCriterion("commodity_img_name between", value1, value2, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andCommodityImgNameNotBetween(String value1, String value2) {
            addCriterion("commodity_img_name not between", value1, value2, "commodityImgName");
            return (Criteria) this;
        }

        public Criteria andIncludePostageIsNull() {
            addCriterion("include_postage is null");
            return (Criteria) this;
        }

        public Criteria andIncludePostageIsNotNull() {
            addCriterion("include_postage is not null");
            return (Criteria) this;
        }

        public Criteria andIncludePostageEqualTo(Boolean value) {
            addCriterion("include_postage =", value, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageNotEqualTo(Boolean value) {
            addCriterion("include_postage <>", value, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageGreaterThan(Boolean value) {
            addCriterion("include_postage >", value, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageGreaterThanOrEqualTo(Boolean value) {
            addCriterion("include_postage >=", value, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageLessThan(Boolean value) {
            addCriterion("include_postage <", value, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageLessThanOrEqualTo(Boolean value) {
            addCriterion("include_postage <=", value, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageIn(List<Boolean> values) {
            addCriterion("include_postage in", values, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageNotIn(List<Boolean> values) {
            addCriterion("include_postage not in", values, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageBetween(Boolean value1, Boolean value2) {
            addCriterion("include_postage between", value1, value2, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIncludePostageNotBetween(Boolean value1, Boolean value2) {
            addCriterion("include_postage not between", value1, value2, "includePostage");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNull() {
            addCriterion("is_available is null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIsNotNull() {
            addCriterion("is_available is not null");
            return (Criteria) this;
        }

        public Criteria andIsAvailableEqualTo(Boolean value) {
            addCriterion("is_available =", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotEqualTo(Boolean value) {
            addCriterion("is_available <>", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThan(Boolean value) {
            addCriterion("is_available >", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_available >=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThan(Boolean value) {
            addCriterion("is_available <", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableLessThanOrEqualTo(Boolean value) {
            addCriterion("is_available <=", value, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableIn(List<Boolean> values) {
            addCriterion("is_available in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotIn(List<Boolean> values) {
            addCriterion("is_available not in", values, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableBetween(Boolean value1, Boolean value2) {
            addCriterion("is_available between", value1, value2, "isAvailable");
            return (Criteria) this;
        }

        public Criteria andIsAvailableNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_available not between", value1, value2, "isAvailable");
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