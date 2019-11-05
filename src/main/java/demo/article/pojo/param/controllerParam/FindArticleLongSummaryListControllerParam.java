package demo.article.pojo.param.controllerParam;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import demo.article.pojo.constant.ArticleConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="查询文章缩写标题参数", description="")
public class FindArticleLongSummaryListControllerParam {

	@ApiModelProperty(value = "频道名")
	private String articleChannelName;
	@ApiModelProperty(value = "频道uuid")
	private String articleChannelUUID;
	@ApiModelProperty(value = "title")
	private String title;
	private Long userId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;
	private Boolean isDelete = false;
	private Boolean isPass = true;
	private Boolean isEdited = false;
	private Boolean isReject = false;
	private Integer limit = ArticleConstant.defaultPageSize;
	private Boolean desc = true;
	private Boolean hasAdminRole = false;
	private Boolean isHot = false;
	private String vcode;
	
	// 以下参数将在V3版本固定后废弃
	// 2018-12-21
	private Integer pageNo = 1;
	private Integer pageSize = 10;

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getArticleChannelName() {
		return articleChannelName;
	}

	public void setArticleChannelName(String articleChannelName) {
		this.articleChannelName = articleChannelName;
	}

	public String getArticleChannelUUID() {
		return articleChannelUUID;
	}

	public void setArticleChannelUUID(String articleChannelUUID) {
		this.articleChannelUUID = articleChannelUUID;
	}

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public Boolean getDesc() {
		return desc;
	}

	public void setDesc(Boolean desc) {
		this.desc = desc;
	}

	public Boolean getHasAdminRole() {
		return hasAdminRole;
	}

	public void setHasAdminRole(Boolean hasAdminRole) {
		this.hasAdminRole = hasAdminRole;
	}

}
