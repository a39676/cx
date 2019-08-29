package demo.account_info.mapper;

import demo.account_info.pojo.po.AccountInfoMarker;

public interface AccountInfoMarkerCustomMapper extends AccountInfoMarkerMapper{

	int updateAccountMarker(AccountInfoMarker marker);
	
	AccountInfoMarker getMarkerByAccountId(Long accountId);
	
	int insertCustom(AccountInfoMarker marker);

}
