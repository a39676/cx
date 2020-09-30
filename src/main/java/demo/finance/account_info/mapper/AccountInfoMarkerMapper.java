package demo.finance.account_info.mapper;

import demo.finance.account_info.pojo.po.AccountInfoMarker;

public interface AccountInfoMarkerMapper {
	int insert(AccountInfoMarker record);

	int insertSelective(AccountInfoMarker record);

	int updateAccountMarker(AccountInfoMarker marker);

	AccountInfoMarker getMarkerByAccountId(Long accountId);

	int insertCustom(AccountInfoMarker marker);
}