package demo.base.task.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
import demo.finance.cryptoCoin.data.pojo.type.CryptoCoinCatalogTaskType;
import demo.finance.cryptoCoin.data.pojo.type.CryptoCoinDataTaskType;
import demo.finance.cryptoCoin.data.pojo.type.CryptoCoinTaskType;
import demo.finance.cryptoCoin.data.service.impl.CryptoCoinCatalogTaskService;
import demo.finance.cryptoCoin.data.service.impl.CryptoCoinDataTaskService;
import demo.finance.cryptoCoin.data.service.impl.CryptoCoinTaskService;
import demo.finance.cryptoCoin.notice.pojo.type.CryptoCoinNoticeTaskType;
import demo.finance.cryptoCoin.notice.service.impl.CryptoCoinNoticeTaskService;
import demo.finance.currencyExchangeRate.data.pojo.type.CurrencyExchangeRateDataTaskType;
import demo.finance.currencyExchangeRate.data.service.impl.CurrencyExchangeRateDataTaskService;
import demo.finance.currencyExchangeRate.notice.pojo.type.CurrencyExchangeRateNoticeTaskType;
import demo.finance.currencyExchangeRate.notice.service.impl.CurrencyExchangeRateNoticeTaskService;
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
	@Autowired
	private CryptoCoinCatalogTaskService cryptoCoinCatalogTaskService;
	@Autowired
	private CryptoCoinDataTaskService cryptoCoinDataTaskService;
	@Autowired
	private CryptoCoinNoticeTaskService cryptoCoinNoticeTaskService;
	@Autowired
	private CurrencyExchangeRateDataTaskService currencyExchangeRateDataTaskService;
	@Autowired
	private CurrencyExchangeRateNoticeTaskService currencyExchangeRateNoticeTaskService;

	@Override
	public CommonResult startEvent(String message) {
		CommonResult r = new CommonResult();
		SendTaskDTO dto = null;
		
		StringBuffer sb = new StringBuffer();

		try {
			dto = buildObjFromJsonCustomization(message, SendTaskDTO.class);
			
			sb.append("Receive " + dto.getTaskFirstName() + ", " + dto.getTaskSecondName());
		} catch (Exception e) {
			sb.append("Receive task error, message: " + message);
			sendTelegram(sb.toString());
			r.setMessage("Message format error");
			return r;
		}

		r = verifyTaskDTO(dto);
		if (r.isFail()) {
			sb.append("Receive task, verify failed, message: " + r.getMessage());
			sendTelegram(sb.toString());
			return r;
		}

		if (optionService.getBreakFlag()) {
			sb.append("Task flag = true");
			sendTelegram(sb.toString());
			r.setMessage("Break flag = true");
			return r;
		}

		if (optionService.getRunningTask()) {
			String taskName = optionService.getRunningTaskName();
			r.setMessage("Still running task: " + taskName);
			return r;
		}

		LocalDateTime startTime = LocalDateTime.now();
		sb.append("Before start task. datetime: " + startTime);
		startTask(dto);

		TaskType taskType = TaskType.getType(dto.getTaskFirstCode());
		String secondTaskName = null;

		try {
			switch (taskType) {

			case TEST: {
				sendTelegram("Receive test task");
				break;
			}
			case SYSTEM: {
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
				break;
			}
			case ARTICLE: {
				ArticleTaskType secondTaskType = ArticleTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (ArticleTaskType.DELETE_ARTICLE_BY_VALID_SETTING.getCode().equals(dto.getTaskSecondCode())) {
					articleTaskService.deleteArticleByValidSetting();

				} else if (ArticleTaskType.UPDATE_ARTICLE_HOT_EXPIRED.getCode().equals(dto.getTaskSecondCode())) {
					articleTaskService.updateArticleHotExpired();

				}
				break;
			}
			case AUTOMATION_TEST: {
				AutomationTestTaskType secondTaskType = AutomationTestTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (AutomationTestTaskType.DELETE_OLD_DATA.getCode().equals(dto.getTaskSecondCode())) {
					automationTestTaskService.deleteOldData();

				} else if (AutomationTestTaskType.HANDLE_LONG_WAITING_EVENT.getCode().equals(dto.getTaskSecondCode())) {
					automationTestTaskService.handleLongWaitingEvent();

				} else if (AutomationTestTaskType.SEND_TEST_EVENT_TO_RUN.getCode().equals(dto.getTaskSecondCode())) {
					automationTestTaskService.sendTestEventToRun();

				}
				break;
			}
			case BOOKMARK: {
				BookmarkTaskType secondTaskType = BookmarkTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (BookmarkTaskType.RE_BALANCE_WEIGHT.getCode().equals(dto.getTaskSecondCode())) {
					bookmarkTaskService.reBalanceWeight();
				}
				break;
			}
			case CALENDAR_NOTICE: {
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
				break;
			}
			case OLD_DATA_DELETE: {
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
				break;
			}
			case CRYPTO_COIN: {
				CryptoCoinTaskType secondTaskType = CryptoCoinTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CryptoCoinTaskType.CHECK_WEB_SOCKET_STATUS.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinTaskService.checkWebSocketStatus();
				} else if (CryptoCoinTaskType.CLEAN_OLD_HISTORY_DATA.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinTaskService.cleanOldHistoryData();
				}
				break;
			}
			case JOY: {
				JoyTaskType secondTaskType = JoyTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (JoyTaskType.CACHE_TO_DATABASE.getCode().equals(dto.getTaskSecondCode())) {
					joyTaskService.cacheToDatabase();
				}
				break;
			}
			case URGE_NOTICE: {
				UrgeNoticeTaskType secondTaskType = UrgeNoticeTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (UrgeNoticeTaskType.SEND_URGE_NOTICE.getCode().equals(dto.getTaskSecondCode())) {
					urgeNoticeTaskService.sendUrgeNotice();
				}
				break;
			}
			case CRYPTO_COIN_CATALOG: {
				CryptoCoinCatalogTaskType secondTaskType = CryptoCoinCatalogTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CryptoCoinCatalogTaskType.ADD_SUBSCRIPTION_CATALOG.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinCatalogTaskService.addSubscriptionCatalog();
				}
				break;
			}
			case CRYPTO_COIN_DATA: {
				CryptoCoinDataTaskType secondTaskType = CryptoCoinDataTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CryptoCoinDataTaskType.DELETE_EXPIRED_CACHE_DATA.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinDataTaskService.deleteExpiredCacheData();
				} else if (CryptoCoinDataTaskType.RESET_DAILY_DATA_WAITING_QUERY_SET.getCode()
						.equals(dto.getTaskSecondCode())) {
					cryptoCoinDataTaskService.resetDailyDataWaitingQuerySet();
				} else if (CryptoCoinDataTaskType.SEND_CRYPTO_COIN_DAILY_DATA_QUERY_MSG.getCode()
						.equals(dto.getTaskSecondCode())) {
					cryptoCoinDataTaskService.sendCryptoCoinDailyDataQueryMsg();
				} else if (CryptoCoinDataTaskType.SUMMARY_HISTORY_DATA.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinDataTaskService.summaryHistoryData();
				} else if (CryptoCoinDataTaskType.SUMMARY_MINUTE_DATA.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinDataTaskService.summaryMinuteData();
				}
				break;
			}
			case CRYPTO_COIN_NOTICE: {
				CryptoCoinNoticeTaskType secondTaskType = CryptoCoinNoticeTaskType.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CryptoCoinNoticeTaskType.CRYPTO_COIN_PRICE_NOTICE_HANDLER.getCode()
						.equals(dto.getTaskSecondCode())) {
					cryptoCoinNoticeTaskService.cryptoCoinPriceNoticeHandler();
				} else if (CryptoCoinNoticeTaskType.DELETE_OLD_NOTICE.getCode().equals(dto.getTaskSecondCode())) {
					cryptoCoinDataTaskService.deleteExpiredCacheData();
				}
				break;
			}
			case CURRENCY_EXCHANGE_RATE_DATA: {
				CurrencyExchangeRateDataTaskType secondTaskType = CurrencyExchangeRateDataTaskType
						.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CurrencyExchangeRateDataTaskType.SEND_DATA_QUERY.getCode().equals(dto.getTaskSecondCode())) {
					currencyExchangeRateDataTaskService.sendDataQuery();
				}
				break;
			}
			case CURRENCY_EXCHANGE_RATE_NOTICE: {
				CurrencyExchangeRateNoticeTaskType secondTaskType = CurrencyExchangeRateNoticeTaskType
						.getType(dto.getTaskSecondCode());
				secondTaskName = secondTaskType.getName();

				if (CurrencyExchangeRateNoticeTaskType.DELETE_OLD_NOTICE.getCode().equals(dto.getTaskSecondCode())) {
					currencyExchangeRateNoticeTaskService.deleteOldNotice();
				}
				break;
			}
			default:{
				sb.append("Can NOT match task type: " + taskType.getName());
			}
			}
		} catch (Exception e) {
			sb.append("Task running exception, " + dto.getTaskFirstName() + ", " + dto.getTaskSecondName());
			sendTelegram(sb.toString());
			r.setMessage("Task running failed, task type: " + taskType.getName() + ", second task type name: "
					+ secondTaskName + ". error: " + e.getLocalizedMessage());
			return r;
		}
		
		endTask(r, dto);
		long minutes = ChronoUnit.MINUTES.between(startTime, LocalDateTime.now());
		sb.append("End task, " + dto.getTaskFirstName() + ", " + dto.getTaskSecondName() + " use minutes: " + minutes);
		sendTelegram(sb.toString());

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

		switch (taskType) {

		case SYSTEM: {
			SystemTaskType secondTaskType = SystemTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = SystemTaskType.class.getSimpleName();
			}
			break;
		}
		case ARTICLE: {
			ArticleTaskType secondTaskType = ArticleTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = ArticleTaskType.class.getSimpleName();
			}
			break;
		}
		case AUTOMATION_TEST: {
			AutomationTestTaskType secondTaskType = AutomationTestTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = AutomationTestTaskType.class.getSimpleName();
			}
			break;
		}
		case BOOKMARK: {
			BookmarkTaskType secondTaskType = BookmarkTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = BookmarkTaskType.class.getSimpleName();
			}
			break;
		}
		case CALENDAR_NOTICE: {
			CalendarNoticeTaskType secondTaskType = CalendarNoticeTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = CalendarNoticeTaskType.class.getSimpleName();
			}
			break;
		}
		case OLD_DATA_DELETE: {
			OldDataDeleteTaskType secondTaskType = OldDataDeleteTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = OldDataDeleteTaskType.class.getSimpleName();
			}
			break;
		}
		case CRYPTO_COIN: {
			CryptoCoinTaskType secondTaskType = CryptoCoinTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = CryptoCoinTaskType.class.getSimpleName();
			}
			break;
		}
		case JOY: {
			JoyTaskType secondTaskType = JoyTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = JoyTaskType.class.getSimpleName();
			}
			break;
		}
		case URGE_NOTICE: {
			UrgeNoticeTaskType secondTaskType = UrgeNoticeTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = UrgeNoticeTaskType.class.getSimpleName();
			}
			break;
		}
		case CRYPTO_COIN_CATALOG: {
			CryptoCoinCatalogTaskType secondTaskType = CryptoCoinCatalogTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = UrgeNoticeTaskType.class.getSimpleName();
			}
			break;
		}
		case CRYPTO_COIN_DATA: {
			CryptoCoinDataTaskType secondTaskType = CryptoCoinDataTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = UrgeNoticeTaskType.class.getSimpleName();
			}
			break;
		}
		case CRYPTO_COIN_NOTICE: {
			CryptoCoinNoticeTaskType secondTaskType = CryptoCoinNoticeTaskType.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = UrgeNoticeTaskType.class.getSimpleName();
			}
			break;
		}
		case CURRENCY_EXCHANGE_RATE_DATA: {
			CurrencyExchangeRateDataTaskType secondTaskType = CurrencyExchangeRateDataTaskType
					.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = UrgeNoticeTaskType.class.getSimpleName();
			}
			break;
		}
		case CURRENCY_EXCHANGE_RATE_NOTICE: {
			CurrencyExchangeRateNoticeTaskType secondTaskType = CurrencyExchangeRateNoticeTaskType
					.getType(dto.getTaskSecondCode());
			if (secondTaskType != null) {
				taskTypeName = UrgeNoticeTaskType.class.getSimpleName();
			}
			break;
		}
		case TEST:
			break;
		default:
			break;
		}

		if (taskType == null || taskTypeName == null) {
			r.setMessage("Can NOT find task type, type code: " + dto.getTaskSecondCode() + ", task name: "
					+ dto.getTaskSecondName());
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
