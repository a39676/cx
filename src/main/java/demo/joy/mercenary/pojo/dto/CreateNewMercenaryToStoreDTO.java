package demo.joy.mercenary.pojo.dto;

import java.util.List;

public class CreateNewMercenaryToStoreDTO {

	private String name;

	private Double health;
	private Double magic;

	private Double strong;
	private Double agile;
	private Double wisdom;
	private Double spirit;

	private Long imgId;

	private String description;

	private Integer maxSale = 0;

	private Integer skillCount = 0;
	private List<Long> skillIdList;

	public Double getHealth() {
		return health;
	}

	public void setHealth(Double health) {
		this.health = health;
	}

	public Double getMagic() {
		return magic;
	}

	public void setMagic(Double magic) {
		this.magic = magic;
	}

	public Double getStrong() {
		return strong;
	}

	public void setStrong(Double strong) {
		this.strong = strong;
	}

	public Double getAgile() {
		return agile;
	}

	public void setAgile(Double agile) {
		this.agile = agile;
	}

	public Double getWisdom() {
		return wisdom;
	}

	public void setWisdom(Double wisdom) {
		this.wisdom = wisdom;
	}

	public Double getSpirit() {
		return spirit;
	}

	public void setSpirit(Double spirit) {
		this.spirit = spirit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getMaxSale() {
		return maxSale;
	}

	public void setMaxSale(Integer maxSale) {
		this.maxSale = maxSale;
	}

	public Integer getSkillCount() {
		return skillCount;
	}

	public void setSkillCount(Integer skillCount) {
		this.skillCount = skillCount;
	}

	public List<Long> getSkillIdList() {
		return skillIdList;
	}

	public void setSkillIdList(List<Long> skillIdList) {
		this.skillIdList = skillIdList;
	}

	@Override
	public String toString() {
		return "CreateNewMercenaryToStoreDTO [name=" + name + ", health=" + health + ", magic=" + magic + ", strong="
				+ strong + ", agile=" + agile + ", wisdom=" + wisdom + ", spirit=" + spirit + ", imgId=" + imgId
				+ ", description=" + description + ", maxSale=" + maxSale + ", skillCount=" + skillCount
				+ ", skillIdList=" + skillIdList + "]";
	}

}
