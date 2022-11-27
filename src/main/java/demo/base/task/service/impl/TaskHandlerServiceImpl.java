package demo.base.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.article.article.pojo.type.ArticleTaskType;
import demo.article.article.service.impl.ArticleTaskService;
import demo.automationTest.pojo.type.AutomationTestTaskType;
import demo.automationTest.service.impl.AutomationTestTaskServiceImpl;
import demo.base.task.pojo.constant.TaskResultConstant;
import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.OldDataDeleteTaskType;
import demo.base.task.pojo.type.SystemTaskType;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.TaskHandlerService;
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.pojo.type.CryptoCoinTaskType;
import demo.finance.cryptoCoin.data.service.impl.CryptoCoinTaskService;
import demo.joy.common.pojo.type.JoyTaskType;
import demo.joy.common.service.JoyTaskService;
import demo.pmemo.pojo.type.UrgeNoticeTaskType;
import demo.pmemo.service.impl.UrgeNoticeTaskService;
import demo.tool.bookmark.pojo.type.BookmarkTaskType;
import demo.tool.bookmark.service.impl.BookmarkTaskService;
import demo.tool.calendarNotice.mq.producer.TelegramCalendarNoticeMessageAckProducer;
import demo.tool.calendarNotice.pojo.type.CalendarNoticeTaskType;
import demo.tool.calendarNotice.service.impl.CalendarNoticeTaskService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.dto.TelegramBotNoticeMessageDTO;

@Service
public class TaskHandlerServiceImpl extends CommonService implements TaskHandlerService {

	@Autowired
	private TaskOptionService optionService;

	@Autowired
	private TelegramCalendarNoticeMessageAckProducer telegramMessageAckProducer;

	@Autowired
	private SystemTaskServiceImpl systemTaskService;
	@Autowired
	private ArticleTaskService articleTaskService;
	@Autowired
	private AutomationTestTaskServiceImpl automationTestTaskService;
	@Autowired
	private BookmarkTaskService bookmarkTaskService;
	@Autowired
	private CalendarNoticeTaskService calendarNoticeTaskService;
	@Autowired
	private OldDataDeleteServiceImpl oldDataDeleteService;
	@Autowired
	private CryptoCoinTaskService cryptoCoinTaskService;
	@Autowired
	private JoyTaskService joyTaskService;
	@Autowired
	private UrgeNoticeTaskService urgeNoticeTaskService;

	@Override
	public CommonResult startEvent(String message) {
		CommonResult r = new CommonResult();
		SendTaskDTO dto = null;

		try {
			dto = buildObjFromJsonCustomization(message, SendTaskDTO.class);
		} catch (Exception e) {
			r.setMessage("Message format error");
			return r;
		}

		r = verifyTaskDTO(dto);
		if (r.isFail()) {
			return r;
		}

		if (optionService.getBreakFlag()) {
			r.setMessage("Break flag = true");
			return r;
		}

		if (optionService.getRunningTask()) {
			String taskName = optionService.getRunningTaskName();
			r.setMessage("Still running task: " + taskName);
			return r;
		}

		startTask(dto);

		TaskType taskType = TaskType.getType(dto.getTaskFirstCode());
		String secondTaskName = null;

		try {
			if (TaskType.TEST.getCode().equals(dto.getTaskFirstCode())) {
				sendTelegram("Receive test task");

			} else if (TaskType.SYSTEM.getCode().equals(dto.getTaskFirstCode())) {
				SystemTaskType secondTaskType = SystemTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (SystemTaskType.KEEP_DATABASE_CONNECTION_ALIVE.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.keepDatabaseConnectionAlive();
				} else if (SystemTaskType.CLEAN_ATTEMPTS.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.cleanAttempts();
				} else if (SystemTaskType.CLEAN_MAIL_RECORD.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.cleanMailRecord();
				} else if (SystemTaskType.EVALUATION_CACHE_TO_STORE.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.evaluationCacheToStore();
				} else if (SystemTaskType.EVALUATION_CACHE_STATISTICS.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.evaluationCacheStatistics();
				} else if (SystemTaskType.REFILL_ARTICLE_LONG_REVIEW_CREATOR_ID.getCode()
						.equals(dto.getTaskSecondCode())) {
					systemTaskService.refillArticleLongReviewCreatorId();
				} else if (SystemTaskType.CREATE_FAKE_EVALUATION_STORE.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.createFakeEvaluationStore();
				} else if (SystemTaskType.AUTO_PASS.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.autoPass();
				} else if (SystemTaskType.VISIT_COUNT_REDIS_TO_ORM.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.visitCountRedisToOrm();
				} else if (SystemTaskType.DELETE_EXPIRED_DENY_RECORD.getCode().equals(dto.getTaskSecondCode())) {
					systemTaskService.deleteExpiredDenyRecord();
				}

			} else if (TaskType.ARTICLE.getCode().equals(dto.getTaskFirstCode())) {
				ArticleTaskType secondTaskType = ArticleTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (ArticleTaskType.DELETE_ARTICLE_BY_VALID_SETTING.getCode().equals(dto.getTaskSecondCode())) {
					articleTaskService.deleteArticleByValidSetting();

				} else if (ArticleTaskType.UPDATE_ARTICLE_HOT_EXPIRED.getCode().equals(dto.getTaskSecondCode())) {
					articleTaskService.updateArticleHotExpired();

				}

			} else if (TaskType.AUTOMATION_TEST.getCode().equals(dto.getTaskFirstCode())) {
				AutomationTestTaskType secondTaskType = AutomationTestTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (AutomationTestTaskType.DELETE_OLD_DATA.getCode().equals(dto.getTaskSecondCode())) {
					automationTestTaskService.deleteOldData();

				} else if (AutomationTestTaskType.HANDLE_LONG_WAITING_EVENT.getCode().equals(dto.getTaskSecondCode())) {
					automationTestTaskService.handleLongWaitingEvent();

				} else if (AutomationTestTaskType.SEND_TEST_EVENT_TO_RUN.getCode().equals(dto.getTaskSecondCode())) {
					automationTestTaskService.sendTestEventToRun();

				}

			} else if (TaskType.BOOKMARK.getCode().equals(dto.getTaskFirstCode())) {
				BookmarkTaskType secondTaskType = BookmarkTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (BookmarkTaskType.RE_BALANCE_WEIGHT.getCode().equals(dto.getTaskSecondCode())) {
					bookmarkTaskService.reBalanceWeight();
				}

			} else if (TaskType.CALENDAR_NOTICE.getCode().equals(dto.getTaskFirstCode())) {
				CalendarNoticeTaskType secondTaskType = CalendarNoticeTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CalendarNoticeTaskType.FIND_AND_SEND_NOTICE.getCode().equals(dto.getTaskSecondCode())) {
					calendarNoticeTaskService.findAndSendNotice();
				} else if (CalendarNoticeTaskType.FIND_AND_SNED_STRONG_NOTICE.getCode()
						.equals(dto.getTaskSecondCode())) {
					calendarNoticeTaskService.findAndSendStrongNotice();
				} else if (CalendarNoticeTaskType.SEND_TOMORROW_NOTICE_LIST.getCode().equals(dto.getTaskSecondCode())) {
					calendarNoticeTaskService.sendTomorrowNoticeList();
				}

			} else if (TaskType.OLD_DATA_DELETE.getCode().equals(dto.getTaskFirstCode())) {
				OldDataDeleteTaskType secondTaskType = OldDataDeleteTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (OldDataDeleteTaskType.CLEAN_OLD_AUTO_TEST_UPLOAD_IMAGE.getCode().equals(dto.getTaskSecondCode())) {
					oldDataDeleteService.cleanOldAutoTestUploadImage();
				} else if (OldDataDeleteTaskType.CLEAN_EXPIRED_ARTICLE_BURN.getCode().equals(dto.getTaskSecondCode())) {
					oldDataDeleteService.cleanExpiredArticleBurn();
				} else if (OldDataDeleteTaskType.IMAGE_CLEAN.getCode().equals(dto.getTaskSecondCode())) {
					oldDataDeleteService.imageClean();
				} else if (OldDataDeleteTaskType.DELETE_OLD_EXERCISE_FILE.getCode().equals(dto.getTaskSecondCode())) {
					oldDataDeleteService.deleteOldExerciseFile();
				}

			} else if (TaskType.CRYPTO_COIN.getCode().equals(dto.getTaskFirstCode())) {
				CryptoCoinTaskType secondTaskType = CryptoCoinTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CryptoCoinTaskType.CHECK_WEB_SOCKET_STATUS.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinTaskService.checkWebSocketStatus();
				} else if (CryptoCoinTaskType.CLEAN_OLD_HISTORY_DATA.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinTaskService.cleanOldHistoryData();
				}
			} else if (TaskType.JOY.getCode().equals(dto.getTaskFirstCode())) {
				JoyTaskType secondTaskType = JoyTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (JoyTaskType.CACHE_TO_DATABASE.getCode().equals(dto.getTaskSecondCode())) {
					joyTaskService.cacheToDatabase();
				}
			} else if (TaskType.URGE_NOTICE.getCode().equals(dto.getTaskFirstCode())) {
				UrgeNoticeTaskType secondTaskType = UrgeNoticeTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (UrgeNoticeTaskType.SEND_URGE_NOTICE.getCode().equals(dto.getTaskSecondCode())) {
					urgeNoticeTaskService.sendUrgeNotice();
				}
			}

		} catch (Exception e) {
			r.setMessage("Task running failed, task type: " + taskType.getName() + ", second task type name: "
					+ secondTaskName + ". error: " + e.getLocalizedMessage());
			return r;
		}

		endTask(r, dto);

		r.setIsSuccess();
		return r;
	}

	private CommonResult verifyTaskDTO(SendTaskDTO dto) {
		CommonResult r = new CommonResult();
		TaskType taskType = TaskType.getType(dto.getTaskFirstCode());
		String taskTypeName = null;

		if (taskType == null) {
			r.setMessage("Can NOT find task type, type code: " + dto.getTaskFirstCode() + ", task name: "
					+ dto.getTaskFirstName());
			return r;
		}

		if (TaskType.SYSTEM.equals(taskType)) {
			SystemTaskType secondTaskType = SystemTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = SystemTaskType.class.getSimpleName();
			}
		} else if (TaskType.ARTICLE.equals(taskType)) {
			ArticleTaskType secondTaskType = ArticleTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = ArticleTaskType.class.getSimpleName();
			}
		} else if (TaskType.AUTOMATION_TEST.equals(taskType)) {
			AutomationTestTaskType secondTaskType = AutomationTestTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = AutomationTestTaskType.class.getSimpleName();
			}
		} else if (TaskType.BOOKMARK.equals(taskType)) {
			BookmarkTaskType secondTaskType = BookmarkTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = BookmarkTaskType.class.getSimpleName();
			}
		} else if (TaskType.CALENDAR_NOTICE.equals(taskType)) {
			CalendarNoticeTaskType secondTaskType = CalendarNoticeTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = CalendarNoticeTaskType.class.getSimpleName();
			}
		} else if (TaskType.OLD_DATA_DELETE.equals(taskType)) {
			OldDataDeleteTaskType secondTaskType = OldDataDeleteTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = OldDataDeleteTaskType.class.getSimpleName();
			}
		} else if (TaskType.CRYPTO_COIN.equals(taskType)) {
			CryptoCoinTaskType secondTaskType = CryptoCoinTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = CryptoCoinTaskType.class.getSimpleName();
			}
		} else if (TaskType.JOY.equals(taskType)) {
			JoyTaskType secondTaskType = JoyTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = JoyTaskType.class.getSimpleName();
			}
		} else if (TaskType.URGE_NOTICE.equals(taskType)) {
			UrgeNoticeTaskType secondTaskType = UrgeNoticeTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType == null) {
				taskTypeName = UrgeNoticeTaskType.class.getSimpleName();
			}
		}

		if (taskTypeName != null) {
			r.setMessage("Can NOT find " + taskTypeName + " task type, type code: " + dto.getTaskSecondCode()
					+ ", task name: " + dto.getTaskSecondName());
			return r;
		}

		if (optionService.getFaildTaskCountingMap().containsKey(dto.getTaskId())) {
			if (optionService.getFaildTaskCountingMap().get(dto.getTaskId()) >= TaskResultConstant.MAX_FAIL_COUNT) {
				r.setCode(TaskResultConstant.FAIL_BY_MAX_COUNT);
				return r;
			}
		}

		r.setIsSuccess();
		return r;
	}

	private void startTask(SendTaskDTO dto) {
		optionService.setRunningTask(true);
		optionService.setRunningTaskName(dto.getTaskFirstName() + "," + dto.getTaskSecondName());

	}

	private void endTask(CommonResult result, SendTaskDTO dto) {
		optionService.setRunningTask(false);
		optionService.setRunningTaskName(null);

		if (result.isSuccess() || TaskResultConstant.FAIL_BY_MAX_COUNT.equals(result.getCode())) {
			optionService.removeFailTaskCount(dto.getTaskId());
		} else {
			optionService.addFailTaskCount(dto.getTaskId());
		}

		if (result.isFail()) {
			sendTelegram("Task failed, " + dto.getTaskFirstName() + ", " + dto.getTaskSecondName() + ", failed count: "
					+ optionService.getFaildTaskCountingMap().get(dto.getTaskId()));
		}
	}

	private void endTask() {
		optionService.setRunningTask(false);
		optionService.setRunningTaskName(null);
	}

	@Override
	public String getRunningTaskName() {
		return optionService.getRunningTaskName() + "," + optionService.getRunningTask();
	}

	@Override
	public void fixRuningTaskStatus() {
		endTask();
	}

	@Override
	public boolean setBreakFlag(Integer flag) {
		optionService.setBreakFlag("1".equals(String.valueOf(flag)));
		return optionService.getBreakFlag();
	}

	@Override
	public boolean setBreakFlag(boolean flag) {
		optionService.setBreakFlag(flag);
		return optionService.getBreakFlag();
	}

	private void sendTelegram(String msg) {
		TelegramBotNoticeMessageDTO dto = null;
		dto = new TelegramBotNoticeMessageDTO();
		dto.setId(TelegramStaticChatID.MY_ID);
		dto.setBotName(TelegramBotType.BOT_2.getName());
		dto.setMsg(msg);

		telegramMessageAckProducer.send(dto);
	}

}
