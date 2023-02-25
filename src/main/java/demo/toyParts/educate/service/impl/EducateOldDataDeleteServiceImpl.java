package demo.toyParts.educate.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.tool.telegram.service.TelegramService;
import demo.toyParts.educate.mapper.StudentExerciseHistoryMapper;
import demo.toyParts.educate.pojo.po.StudentExerciseHistory;
import demo.toyParts.educate.pojo.po.StudentExerciseHistoryExample;
import demo.toyParts.educate.service.EducateCommonService;
import demo.toyParts.educate.service.EducateOldDataDeleteService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

@Service
public class EducateOldDataDeleteServiceImpl extends EducateCommonService implements EducateOldDataDeleteService {

	@Autowired
	private TelegramService telegramService;
	@Autowired
	private StudentExerciseHistoryMapper exerciseHistoryMapper;

	@Override
	public void deleteOldExerciseFile() {
		LocalDateTime limitLivingTime = LocalDateTime.now().minusDays(optionService.getOldExerciseFileLivingDay());
		StudentExerciseHistoryExample example = new StudentExerciseHistoryExample();
		example.createCriteria().andFilePathIsNotNull().andCompeletionTimeLessThan(limitLivingTime);
		List<StudentExerciseHistory> poListByCompeletionTime = exerciseHistoryMapper.selectByExample(example);
		
		HashSet<StudentExerciseHistory> summaryPoSet = new HashSet<>();
		summaryPoSet.addAll(poListByCompeletionTime);
		
		example = new StudentExerciseHistoryExample();
		example.createCriteria().andFilePathIsNotNull().andCreateTimeLessThan(limitLivingTime).andCompeletionTimeIsNull();
		List<StudentExerciseHistory> poListByCreateTimeAndNotCompelet = exerciseHistoryMapper.selectByExample(example);
		summaryPoSet.addAll(poListByCreateTimeAndNotCompelet);
		
		File tmpFile = null;
		for(StudentExerciseHistory po : summaryPoSet) {
			tmpFile = new File(po.getFilePath());
			if(tmpFile.exists() && tmpFile.isFile()) {
				try {
					if(!tmpFile.delete()) {
						sendTelegram("Delete old exercise file error, ID: " + po.getExerciseId());
					} else {
						po.setFilePath(null);
						exerciseHistoryMapper.updateByPrimaryKeySelective(po);
					}
				} catch (Exception e) {
					sendTelegram("Delete old exercise file error, ID: " + po.getExerciseId());
				}
			}
		}
	}
	
	private void sendTelegram(String msg) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE, msg, TelegramStaticChatID.MY_ID);
	}
}
