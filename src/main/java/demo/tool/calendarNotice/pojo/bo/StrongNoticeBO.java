package demo.tool.calendarNotice.pojo.bo;

public class StrongNoticeBO {

	private Long id;
	private String noticeContent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	@Override
	public String toString() {
		return "StrongNoticeBO [id=" + id + ", noticeContent=" + noticeContent + "]";
	}

}
