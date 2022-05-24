package demo.joy.image.common.pojo.dto;

public class JoyImageUploadDTO {

	private String src;

	private Integer moduleCode;

	private Integer subModuleCode;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(Integer moduleCode) {
		this.moduleCode = moduleCode;
	}

	public Integer getSubModuleCode() {
		return subModuleCode;
	}

	public void setSubModuleCode(Integer subModuleCode) {
		this.subModuleCode = subModuleCode;
	}

	@Override
	public String toString() {
		return "JoyImageUploadDTO [src=" + src + ", moduleCode=" + moduleCode + ", subModuleCode=" + subModuleCode
				+ "]";
	}

}
