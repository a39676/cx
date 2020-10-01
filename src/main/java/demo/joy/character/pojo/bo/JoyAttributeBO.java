package demo.joy.character.pojo.bo;

public class JoyAttributeBO {

	private Long id;

	private Double health = 1D;
	private Double magic = 1D;

	private Double strong = 1D;
	private Double agile = 1D;
	private Double wisdom = 1D;
	private Double spirit = 1D;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}
