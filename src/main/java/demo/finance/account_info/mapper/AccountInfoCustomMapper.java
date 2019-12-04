package demo.finance.account_info.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.finance.account_info.pojo.bo.AccountInfoWithBankInfo;
import demo.finance.account_info.pojo.dto.controllerDTO.FindAccountInfoByConditionDTO;
import demo.finance.account_info.pojo.dto.mapperDTO.GetAccountInfoWithConditionParam;
import demo.finance.account_info.pojo.po.AccountInfo;

public interface AccountInfoCustomMapper {
    
    AccountInfo getAcountInfoById(long id);

    void accountRegistration(AccountInfo accountInfo);
    
    ArrayList<String> accountNumberDuplicateCheck(String accountNumberInput);

    String getMainAccountNum(int id);
    
    int accountAmountModify(AccountInfo accountInfo);
    
    AccountInfo getAccountInfoByAccountNumber(String accountNumber);
    
    int modifyAccountInfoVaildDate(@Param("vaildDate")Date vaildDate, @Param("accountNumber")String accountNumber);
    
    int modifyAccountInfo(AccountInfo account);
    
    List<AccountInfoWithBankInfo> getAccountInfoWithBankInfo(GetAccountInfoWithConditionParam mapperParam);
    
    List<AccountInfo> getAllAffiliatedAccountByAffiliationId(@Param("accountAffiliation")Long accountAffiliation);
    
    int checkAccountNumberBelongUser(HashMap<String, String> conditionMap);
    
    List<AccountInfo> findByCondition(FindAccountInfoByConditionDTO dto);
}
