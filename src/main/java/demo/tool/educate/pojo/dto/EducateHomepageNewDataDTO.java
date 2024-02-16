package demo.tool.educate.pojo.dto;

import java.util.List;

public class EducateHomepageNewDataDTO {

	private List<EducateHomepageNewDataSubDTO> mathDataList;
	private List<EducateHomepageNewDataSubDTO> chineseDataList;
	private List<EducateHomepageNewDataSubDTO> englishDataList;

	public List<EducateHomepageNewDataSubDTO> getMathDataList() {
		return mathDataList;
	}

	public void setMathDataList(List<EducateHomepageNewDataSubDTO> mathDataList) {
		this.mathDataList = mathDataList;
	}

	public List<EducateHomepageNewDataSubDTO> getChineseDataList() {
		return chineseDataList;
	}

	public void setChineseDataList(List<EducateHomepageNewDataSubDTO> chineseDataList) {
		this.chineseDataList = chineseDataList;
	}

	public List<EducateHomepageNewDataSubDTO> getEnglishDataList() {
		return englishDataList;
	}

	public void setEnglishDataList(List<EducateHomepageNewDataSubDTO> englishDataList) {
		this.englishDataList = englishDataList;
	}

	@Override
	public String toString() {
		return "EducateHomepageNewDataDTO [mathDataList=" + mathDataList + ", chineseDataList=" + chineseDataList
				+ ", englishDataList=" + englishDataList + "]";
	}

}
