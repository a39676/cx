package demo.account_info.pojo.constant;

public class AccountUrl {
	
	public static final String accountInfoRoot = "/accountInfo";
	public static final String accountInfo = "/accountInfo";
	public static final String accountDetail = "/accountDetail";
	public static final String accountNumberDuplicateCheck = "/accountNumberDuplicateCheck";
	public static final String currentAccountNumberList = "/currentAccountNumberList";
	public static final String accountRegist = "/accountRegist";
	
	public static final String accountStatistics = "/accountStatistics";
	/** 提供账户下拉选择框数据 */
	public static final String accountList = "/accountList";
	public static final String accountInfoWithBankInfoList = "/accountInfoWithBankInfoList";
	/** 账户统计页面 */
	public static final String accountListView = "/accountListView";
	
	public static final String modifyVaildDate = "/modifyVaildDate";
	public static final String modifyCreditsQuota = "/modifyCreditsQuota";
	public static final String modifyTemproraryCreditsQuota = "/modifyTemproraryCreditsQuota";
	public static final String modifyTemproraryCreditsVaildDate = "/modifyTemproraryCreditsVaildDate";

	public static final String accountSelectorV1 = "/accountSelectorV1";
	
	
	/* 2019-06-11 远古遗留的设计造成目前  交易逻辑跟账户逻辑有耦合, 待解决后迁移 */
	public static final String financeclear = "/financeclear";
	
	public static final String insertNewTransationV4 = "/insertNewTransationV4";
}
