package demo.toyParts.educate.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.tool.calendarNotice.mq.producer.TelegramCalendarNoticeMessageAckProducer;
import demo.toyParts.educate.mapper.StudentExerciesHistoryMapper;
import demo.toyParts.educate.pojo.po.StudentExerciesHistory;
import demo.toyParts.educate.pojo.po.StudentExerciesHistoryExample;
import demo.toyParts.educate.service.EducateCommonService;
import demo.toyParts.educate.service.EducateTaskService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.dto.TelegramMessageDTO;

@Service
public class EducateTaskServiceImpl extends EducateCommonService implements EducateTaskService {

	@Autowired
	private TelegramCalendarNoticeMessageAckProducer telegramMessageAckProducer;
	
	@Autowired
	private StudentExerciesHistoryMapper exerciesHistoryMapper;

	@Override
	public void deleteOldExerciesFile() {
		LocalDateTime limitLivingTime = LocalDateTime.now().minusDays(optionService.getOldExerciesFileLivingDay());
		StudentExerciesHistoryExample example = new StudentExerciesHistoryExample();
		example.createCriteria().andFilePathIsNotNull().andCompeletionTimeLessThan(limitLivingTime);
		List<StudentExerciesHistory> poListByCompeletionTime = exerciesHistoryMapper.selectByExample(example);
		
		HashSet<StudentExerciesHistory> summaryPoSet = new HashSet<>();
		summaryPoSet.addAll(poListByCompeletionTime);
		
		example = new StudentExerciesHistoryExample();
		example.createCriteria().andFilePathIsNotNull().andCreateTimeLessThan(limitLivingTime).andCompeletionTimeIsNull();
		List<StudentExerciesHistory> poListByCreateTimeAndNotCompelet = exerciesHistoryMapper.selectByExample(example);
		summaryPoSet.addAll(poListByCreateTimeAndNotCompelet);
		
		File tmpFile = null;
		for(StudentExerciesHistory po : summaryPoSet) {
			tmpFile = new File(po.getFilePath());
			if(tmpFile.exists() && tmpFile.isFile()) {
				try {
					if(!tmpFile.delete()) {
						sendTelegram("Delete old exercies file error, ID: " + po.getExerciesId());
					} else {
						po.setFilePath(null);
						exerciesHistoryMapper.updateByPrimaryKeySelective(po);
					}
				} catch (Exception e) {
					sendTelegram("Delete old exercies file error, ID: " + po.getExerciesId());
				}
			}
		}
	}
	
	private void sendTelegram(String msg) {
		TelegramMessageDTO dto = null;
		dto = new TelegramMessageDTO();
		dto.setId(TelegramStaticChatID.MY_ID);
		dto.setBotName(TelegramBotType.BOT_1.getName());
		dto.setMsg(msg);
		
		telegramMessageAckProducer.send(dto);
	}
}
