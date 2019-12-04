package demo.finance.credit_bill.mapper.query;

import java.util.List;

public class BillInfoQuery {

	private List<Long> accountIds;

	public List<Long> getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(List<Long> accountIds) {
		this.accountIds = accountIds;
	}

	@Override
	public String toString() {
		return "BillInfoQuery [accountIds=" + accountIds + "]";
	}

}
