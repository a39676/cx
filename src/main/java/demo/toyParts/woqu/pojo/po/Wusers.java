package demo.toyParts.woqu.pojo.po;

import java.util.Date;

public class Wusers {
	private Long mobile;

	private String nickName;

	private String cnName;

	private Integer level;

	private Integer score;

	private Integer gender;

	private Integer geographicalId;

	private String job;

	private Date birth;

	private String belongStore;

	private String belongStaff;

	private String source;

	private Long introduceMobile;

	private Date createDate;

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName == null ? null : cnName.trim();
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getGeographicalId() {
		return geographicalId;
	}

	public void setGeographicalId(Integer geographicalId) {
		this.geographicalId = geographicalId;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job == null ? null : job.trim();
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getBelongStore() {
		return belongStore;
	}

	public void setBelongStore(String belongStore) {
		this.belongStore = belongStore == null ? null : belongStore.trim();
	}

	public String getBelongStaff() {
		return belongStaff;
	}

	public void setBelongStaff(String belongStaff) {
		this.belongStaff = belongStaff == null ? null : belongStaff.trim();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source == null ? null : source.trim();
	}

	public Long getIntroduceMobile() {
		return introduceMobile;
	}

	public void setIntroduceMobile(Long introduceMobile) {
		this.introduceMobile = introduceMobile;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Wusers [mobile=" + mobile + ", nickName=" + nickName + ", cnName=" + cnName + ", level=" + level
				+ ", score=" + score + ", gender=" + gender + ", geographicalId=" + geographicalId + ", job=" + job
				+ ", birth=" + birth + ", belongStore=" + belongStore + ", belongStaff=" + belongStaff + ", source="
				+ source + ", introduceMobile=" + introduceMobile + ", createDate=" + createDate + "]";
	}

}