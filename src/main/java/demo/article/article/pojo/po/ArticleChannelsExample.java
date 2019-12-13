package demo.article.article.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleChannelsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ArticleChannelsExample() {
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

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(Long value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(Long value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(Long value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(Long value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(Long value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(Long value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<Long> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<Long> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(Long value1, Long value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(Long value1, Long value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNull() {
            addCriterion("channel_name is null");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("channel_name is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("channel_name =", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("channel_name <>", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("channel_name >", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_name >=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("channel_name <", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("channel_name <=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("channel_name like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("channel_name not like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameIn(List<String> values) {
            addCriterion("channel_name in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotIn(List<String> values) {
            addCriterion("channel_name not in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("channel_name between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("channel_name not between", value1, value2, "channelName");
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

        public Criteria andChannelPointIsNull() {
            addCriterion("channel_point is null");
            return (Criteria) this;
        }

        public Criteria andChannelPointIsNotNull() {
            addCriterion("channel_point is not null");
            return (Criteria) this;
        }

        public Criteria andChannelPointEqualTo(Long value) {
            addCriterion("channel_point =", value, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointNotEqualTo(Long value) {
            addCriterion("channel_point <>", value, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointGreaterThan(Long value) {
            addCriterion("channel_point >", value, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointGreaterThanOrEqualTo(Long value) {
            addCriterion("channel_point >=", value, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointLessThan(Long value) {
            addCriterion("channel_point <", value, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointLessThanOrEqualTo(Long value) {
            addCriterion("channel_point <=", value, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointIn(List<Long> values) {
            addCriterion("channel_point in", values, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointNotIn(List<Long> values) {
            addCriterion("channel_point not in", values, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointBetween(Long value1, Long value2) {
            addCriterion("channel_point between", value1, value2, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelPointNotBetween(Long value1, Long value2) {
            addCriterion("channel_point not between", value1, value2, "channelPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointIsNull() {
            addCriterion("channel_flash_point is null");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointIsNotNull() {
            addCriterion("channel_flash_point is not null");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointEqualTo(Integer value) {
            addCriterion("channel_flash_point =", value, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointNotEqualTo(Integer value) {
            addCriterion("channel_flash_point <>", value, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointGreaterThan(Integer value) {
            addCriterion("channel_flash_point >", value, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel_flash_point >=", value, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointLessThan(Integer value) {
            addCriterion("channel_flash_point <", value, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointLessThanOrEqualTo(Integer value) {
            addCriterion("channel_flash_point <=", value, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointIn(List<Integer> values) {
            addCriterion("channel_flash_point in", values, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointNotIn(List<Integer> values) {
            addCriterion("channel_flash_point not in", values, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointBetween(Integer value1, Integer value2) {
            addCriterion("channel_flash_point between", value1, value2, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andChannelFlashPointNotBetween(Integer value1, Integer value2) {
            addCriterion("channel_flash_point not between", value1, value2, "channelFlashPoint");
            return (Criteria) this;
        }

        public Criteria andWeightsIsNull() {
            addCriterion("weights is null");
            return (Criteria) this;
        }

        public Criteria andWeightsIsNotNull() {
            addCriterion("weights is not null");
            return (Criteria) this;
        }

        public Criteria andWeightsEqualTo(Integer value) {
            addCriterion("weights =", value, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsNotEqualTo(Integer value) {
            addCriterion("weights <>", value, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsGreaterThan(Integer value) {
            addCriterion("weights >", value, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsGreaterThanOrEqualTo(Integer value) {
            addCriterion("weights >=", value, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsLessThan(Integer value) {
            addCriterion("weights <", value, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsLessThanOrEqualTo(Integer value) {
            addCriterion("weights <=", value, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsIn(List<Integer> values) {
            addCriterion("weights in", values, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsNotIn(List<Integer> values) {
            addCriterion("weights not in", values, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsBetween(Integer value1, Integer value2) {
            addCriterion("weights between", value1, value2, "weights");
            return (Criteria) this;
        }

        public Criteria andWeightsNotBetween(Integer value1, Integer value2) {
            addCriterion("weights not between", value1, value2, "weights");
            return (Criteria) this;
        }

        public Criteria andChannelTypeIsNull() {
            addCriterion("channel_type is null");
            return (Criteria) this;
        }

        public Criteria andChannelTypeIsNotNull() {
            addCriterion("channel_type is not null");
            return (Criteria) this;
        }

        public Criteria andChannelTypeEqualTo(Integer value) {
            addCriterion("channel_type =", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeNotEqualTo(Integer value) {
            addCriterion("channel_type <>", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeGreaterThan(Integer value) {
            addCriterion("channel_type >", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel_type >=", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeLessThan(Integer value) {
            addCriterion("channel_type <", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeLessThanOrEqualTo(Integer value) {
            addCriterion("channel_type <=", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeIn(List<Integer> values) {
            addCriterion("channel_type in", values, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeNotIn(List<Integer> values) {
            addCriterion("channel_type not in", values, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeBetween(Integer value1, Integer value2) {
            addCriterion("channel_type between", value1, value2, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("channel_type not between", value1, value2, "channelType");
            return (Criteria) this;
        }

        public Criteria andIsFlashIsNull() {
            addCriterion("is_flash is null");
            return (Criteria) this;
        }

        public Criteria andIsFlashIsNotNull() {
            addCriterion("is_flash is not null");
            return (Criteria) this;
        }

        public Criteria andIsFlashEqualTo(Boolean value) {
            addCriterion("is_flash =", value, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashNotEqualTo(Boolean value) {
            addCriterion("is_flash <>", value, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashGreaterThan(Boolean value) {
            addCriterion("is_flash >", value, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_flash >=", value, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashLessThan(Boolean value) {
            addCriterion("is_flash <", value, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashLessThanOrEqualTo(Boolean value) {
            addCriterion("is_flash <=", value, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashIn(List<Boolean> values) {
            addCriterion("is_flash in", values, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashNotIn(List<Boolean> values) {
            addCriterion("is_flash not in", values, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashBetween(Boolean value1, Boolean value2) {
            addCriterion("is_flash between", value1, value2, "isFlash");
            return (Criteria) this;
        }

        public Criteria andIsFlashNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_flash not between", value1, value2, "isFlash");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
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