package demo.base.system.service;

public interface IpRecordService {

	void updateDenyRecord(Long ipNum, Long time);

	void updateAllowRecord(Long ipNum, Long time);

	void updateIsValid();

	boolean isDeny(Long ipNum);

	boolean isAllow(Long ipNum);

}
