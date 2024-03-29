package demo.tool.educate.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.tool.educate.mapper.StudentExerciseHistoryMapper;
import demo.tool.educate.pojo.po.StudentExerciseHistory;
import demo.tool.educate.pojo.po.StudentExerciseHistoryExample;
import demo.tool.educate.service.EducateCommonService;
import demo.tool.educate.service.EducateOldDataDeleteService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

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
