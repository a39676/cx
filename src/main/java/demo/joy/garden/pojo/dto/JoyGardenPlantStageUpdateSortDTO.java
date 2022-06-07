package demo.joy.garden.pojo.dto;

public class JoyGardenPlantStageUpdateSortDTO {

	private String stagePK;

	/**
	 * 1 = ascending sort; 0 = descnding sort
	 */
	private Integer flag;

	public String getStagePK() {
		return stagePK;
	}

	public void setStagePK(String stagePK) {
		this.stagePK = stagePK;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "JoyGardenPlantStageUpdateSortDTO [stagePK=" + stagePK + ", flag=" + flag + "]";
	}

}
