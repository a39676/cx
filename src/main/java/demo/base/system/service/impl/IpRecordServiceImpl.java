package demo.base.system.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.IpRecordMapper;
import demo.base.system.pojo.po.IpRecord;
import demo.base.system.pojo.po.IpRecordExample;
import demo.base.system.pojo.type.IpReocrdType;
import demo.base.system.service.IpRecordService;
import demo.baseCommon.service.CommonService;

@Service
public class IpRecordServiceImpl extends CommonService implements IpRecordService {

	@Autowired
	private IpRecordMapper ipRecordMapper;
	
	private Integer normalValidTimeDay = 3;
	
	@Override
	public void updateDenyRecord(Long ipNum, Long time) {
		if(ipNum == null || ipNum.equals(0L)) {
			return;
		}
		
		IpRecord po = ipRecordMapper.selectByPrimaryKey(ipNum);
		boolean insertFlag = false;
		if(po == null) {
			insertFlag = true;
			po = new IpRecord();
			po.setIp(ipNum);
		}
		
		po.setIsValid(true);
		if(time == null) {
			po.setValidTime(LocalDateTime.now().plusDays(normalValidTimeDay));
		} else {
			po.setValidTime(LocalDateTime.now().plusSeconds(time));
		}
		po.setRecordType(IpReocrdType.deny.getCode());
		
		if(insertFlag) {
			ipRecordMapper.insertSelective(po);
		} else {
			ipRecordMapper.updateByPrimaryKeySelective(po);
		}
	}
	
	@Override
	public void updateAllowRecord(Long ipNum, Long time) {
		if(ipNum == null || ipNum.equals(0L)) {
			return;
		}
		
		IpRecord po = ipRecordMapper.selectByPrimaryKey(ipNum);
		boolean insertFlag = false;
		if(po == null) {
			insertFlag = true;
			po = new IpRecord();
			po.setIp(ipNum);
		}
		
		po.setIsValid(true);
		if(time == null) {
			po.setValidTime(LocalDateTime.now().plusDays(normalValidTimeDay));
		} else {
			po.setValidTime(LocalDateTime.now().plusSeconds(time));
		}
		po.setRecordType(IpReocrdType.allow.getCode());
		
		if(insertFlag) {
			ipRecordMapper.insertSelective(po);
		} else {
			ipRecordMapper.updateByPrimaryKeySelective(po);
		}
	}
	
	@Override
	public void updateIsValid() {
		IpRecordExample example = new IpRecordExample();
		example.createCriteria().andValidTimeLessThan(LocalDateTime.now());
		List<IpRecord> poList = ipRecordMapper.selectByExample(example);
		if(poList == null || poList.isEmpty()) {
			return;
		}
		IpRecord record = new IpRecord();
		record.setIsValid(false);
		ipRecordMapper.updateByExample(record, example);
	}
	
	@Override
	public boolean isDeny(Long ipNum) {
		if(ipNum == null) {
			return true;
		}
		IpRecordExample example = new IpRecordExample();
		example.createCriteria().andIpEqualTo(ipNum).andRecordTypeEqualTo(IpReocrdType.deny.getCode()).andIsDeleteEqualTo(false).andIsValidEqualTo(true).andValidTimeGreaterThan(LocalDateTime.now());
		List<IpRecord> poList = ipRecordMapper.selectByExample(example);
		return (poList != null && !poList.isEmpty());
	}
	
	@Override
	public boolean isAllow(Long ipNum) {
		if(ipNum == null) {
			return true;
		}
		IpRecordExample example = new IpRecordExample();
		example.createCriteria().andIpEqualTo(ipNum).andRecordTypeEqualTo(IpReocrdType.allow.getCode()).andIsDeleteEqualTo(false).andIsValidEqualTo(true).andValidTimeGreaterThan(LocalDateTime.now());
		List<IpRecord> poList = ipRecordMapper.selectByExample(example);
		return (poList != null && !poList.isEmpty());
	}
}
