package demo.toyParts.educate.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class EducateHomepageDataSummaryDTO {

	private List<EducateHomepageSubjectDataSummaryDTO> subjectDataList = new ArrayList<>();

	public List<EducateHomepageSubjectDataSummaryDTO> getSubjectDataList() {
		return subjectDataList;
	}

	public void setSubjectDataList(List<EducateHomepageSubjectDataSummaryDTO> subjectDataList) {
		this.subjectDataList = subjectDataList;
	}

	@Override
	public String toString() {
		return "EducateHomepageDataSummaryDTO [subjectDataList=" + subjectDataList + "]";
	}

}
