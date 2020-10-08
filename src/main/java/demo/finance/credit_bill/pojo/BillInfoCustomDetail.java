package demo.finance.credit_bill.pojo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import demo.finance.credit_bill.pojo.po.BillInfo;

public class BillInfoCustomDetail {
	
	private Long accountId;

	private LocalDate lastBillDate;
	private LocalDate nextBillDate;
	
	private Integer daysToNextBillDate;
	
	private Integer daysToThisRefundDate;
	private Integer daysToNextRefundDate;
	
	private LocalDate thisRefundDate;
	private LocalDate nextRefundDate;
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public LocalDate getLastBillDate() {
		return lastBillDate;
	}

	public void setLastBillDate(LocalDate lastBillDate) {
		this.lastBillDate = lastBillDate;
	}

	public LocalDate getNextBillDate() {
		return nextBillDate;
	}

	public void setNextBillDate(LocalDate nextBillDate) {
		this.nextBillDate = nextBillDate;
	}

	public Integer getDaysToNextBillDate() {
		return daysToNextBillDate;
	}

	public void setDaysToNextBillDate(Integer daysToNextBillDate) {
		this.daysToNextBillDate = daysToNextBillDate;
	}

	public Integer getDaysToThisRefundDate() {
		return daysToThisRefundDate;
	}

	public void setDaysToThisRefundDate(Integer daysToThisRefundDate) {
		this.daysToThisRefundDate = daysToThisRefundDate;
	}

	public Integer getDaysToNextRefundDate() {
		return daysToNextRefundDate;
	}

	public void setDaysToNextRefundDate(Integer daysToNextRefundDate) {
		this.daysToNextRefundDate = daysToNextRefundDate;
	}

	public LocalDate getThisRefundDate() {
		return thisRefundDate;
	}

	public void setThisRefundDate(LocalDate thisRefundDate) {
		this.thisRefundDate = thisRefundDate;
	}

	public LocalDate getNextRefundDate() {
		return nextRefundDate;
	}

	public void setNextRefundDate(LocalDate nextRefundDate) {
		this.nextRefundDate = nextRefundDate;
	}

	@Override
	public String toString() {
		return "BillInfoCustomDetail [accountId=" + accountId + ", lastBillDate=" + lastBillDate + ", nextBillDate="
				+ nextBillDate + ", daysToNextBillDate=" + daysToNextBillDate + ", daysToThisRefundDate="
				+ daysToThisRefundDate + ", daysToNextRefundDate=" + daysToNextRefundDate + ", thisRefundDate="
				+ thisRefundDate + ", nextRefundDate=" + nextRefundDate + "]";
	}

	public BillInfoCustomDetail(BillInfo billInfo) {
		this.setAccountId(billInfo.getAccountId().longValue());

		LocalDate now = LocalDate.now();
		LocalDate thisBillDate;
		LocalDate nextBillDate;
		
		if(now.getDayOfMonth() <= billInfo.getBillDate()) {
			thisBillDate = now.minusMonths(1).withDayOfMonth(billInfo.getBillDate());
		} else {
			thisBillDate = now.withDayOfMonth(billInfo.getBillDate());
		}
		
		nextBillDate = thisBillDate.plusMonths(1);
		
		this.setLastBillDate(thisBillDate);
		this.setNextBillDate(nextBillDate);
		
		Long daysToNextBillDate = ChronoUnit.DAYS.between(now, nextBillDate);
		this.setDaysToNextBillDate(daysToNextBillDate.intValue());
		
		Long daysToThisRefundDate;
		Long daysToNextRefundDate;
		
		if(billInfo.getFreeDays() != null && billInfo.getFreeDays() > 0) {
			daysToThisRefundDate = ChronoUnit.DAYS.between(now, thisBillDate.plusDays(billInfo.getFreeDays()));
			daysToNextRefundDate = ChronoUnit.DAYS.between(now, nextBillDate.plusDays(billInfo.getFreeDays()));
			this.setDaysToThisRefundDate(daysToThisRefundDate.intValue());
			this.setDaysToNextRefundDate(daysToNextRefundDate.intValue());
			
			this.setThisRefundDate(thisBillDate.plusDays(billInfo.getFreeDays()));
			this.setNextRefundDate(nextBillDate.plusDays(billInfo.getFreeDays()));
		}
		
		if(billInfo.getLastRefundDate() != null && (billInfo.getLastRefundDate() > 0 && billInfo.getLastRefundDate() <= 28)) {
			LocalDate thisRefundDate;
			if(billInfo.getLastRefundDate() >= billInfo.getBillDate()) {
				thisRefundDate = thisBillDate.withDayOfMonth(billInfo.getLastRefundDate());
			} else {
				thisRefundDate = thisBillDate.withDayOfMonth(billInfo.getLastRefundDate()).plusMonths(1);
			}
			daysToThisRefundDate = ChronoUnit.DAYS.between(now, thisRefundDate);
			daysToNextRefundDate = ChronoUnit.DAYS.between(now, thisRefundDate.plusMonths(1));
			
			this.setDaysToThisRefundDate(daysToThisRefundDate.intValue());
			this.setDaysToNextRefundDate(daysToNextRefundDate.intValue());
			
			this.setThisRefundDate(thisRefundDate);
			this.setNextRefundDate(thisRefundDate.plusMonths(1));
		}
		
	}

}
