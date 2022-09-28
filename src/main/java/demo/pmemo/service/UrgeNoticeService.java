package demo.pmemo.service;

public interface UrgeNoticeService {

	void receiveUpdateMsgWebhook(String unknowContent);

	void setUpdateMsgWebhook(String secretToken);


}
