package demo.finance.credit_bill.pojo;

import demo.config.costom_component.EncryptUtil;

public class CreditBillVerifier {
	
	public boolean checkRemark(CreditBills creditBill) {

		if (creditBill.getMarker() == null || creditBill.getMarker().length() <= 0) {
			return false;
		}

		return creditBill.getMarker()
				.equals(EncryptUtil.Sha1(EncryptUtil.ToMd5String(creditBill.getInfos())));
	}

}
