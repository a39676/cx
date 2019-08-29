package demo.account_holder.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import demo.account_holder.pojo.po.AccountHolder;

public interface AccountHolderCustomMapper{

	@Results({
		@Result(property = "accountHolderId", column = "account_holder_id"),
		@Result(property = "accountHolderName", column = "account_holder_name"),
		@Result(property = "gender", column = "gender"),
		@Result(property = "birth", column = "birth")
	})
	@Select("select * from account_holder where account_holder_id = #{id}")
	AccountHolder selectHolderByIdWithAnnotation(int id);
	
	@Insert("insert into account_holder("
			+ "    account_holder_name, "
			+ "    gender, "
			+ "    birth "
			+ ") "
			+ "values("
			+ "    #{accountHolderName},"
			+ "    #{gender},"
			+ "    #{birth}"
			+ ")")
	void insertAccountHolderWithAnnotation(AccountHolder holder);
	
    AccountHolder findAccountHolderByID(Long id);
    
    List<AccountHolder> findAccountHolderByName(String holderName);
    
    int addAccountHolder(AccountHolder newHolder);
    
    void deleteAccountHolderByID(String id);
    
    void updateHolder(AccountHolder newHolder);
    
    List<AccountHolder> getCurrentHolders(String userName);
}
