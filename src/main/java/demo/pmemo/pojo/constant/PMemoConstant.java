package demo.pmemo.pojo.constant;

public class PMemoConstant {

	public static final String P_MEMO_REDIS_KEY = "PMemoRedisKey";
	
	/** 默认 PMemo 有效期(秒), 一天 */
	public static final Long DEFAULT_VALID_SECONDS = 3600L *24L;
	
	/** 暂定最大5m */
	public static final Long MEMO_MAX_SIZE = 1024L * 1024 * 5;
	
	public static final String P_NOTE_SAVING_FOLDER = "/home/u2/pNote";
	
	public static final String URGE_NOTE_SAVING_FOLDER = "/home/u2/urgeNote";
	
	public static final String LAST_UPDATE_TELEGRAM_BOT_URGE_NOTICE_MSG_ID_KEY = "lastUpdateTelegramBotUrgeNoticeMsgId";
}
