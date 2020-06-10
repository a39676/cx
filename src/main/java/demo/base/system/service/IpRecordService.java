package demo.base.system.service;

import java.util.List;

public interface IpRecordService {

	void updateDenyRecord(Long ipNum, Long time);

	void updateAllowRecord(Long ipNum, Long time);

	void updateIsValid();

	boolean isDeny(Long ipNum);

	boolean isAllow(Long ipNum);

	List<Long> getAllDenyList();

	void deleteExpiredDenyRecord();

}
