package demo.finance.cryptoCoin.data.service;

public interface CryptoCoinLocalDataRestoreService {

	void restoreWeekData();

	void restoreMonthData();

	Long cleanDuplicateDailyData();

	Long cleanDuplicateDailyDataInTheSameDay();

}
