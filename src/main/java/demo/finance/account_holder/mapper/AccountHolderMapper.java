package demo.finance.account_holder.mapper;

import demo.finance.account_holder.pojo.po.AccountHolder;
import demo.finance.account_holder.pojo.po.AccountHolderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AccountHolderMapper {
    long countByExample(AccountHolderExample example);

    int deleteByExample(AccountHolderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AccountHolder record);

    int insertSelective(AccountHolder record);

    List<AccountHolder> selectByExampleWithRowbounds(AccountHolderExample example, RowBounds rowBounds);

    List<AccountHolder> selectByExample(AccountHolderExample example);

    AccountHolder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AccountHolder record, @Param("example") AccountHolderExample example);

    int updateByExample(@Param("record") AccountHolder record, @Param("example") AccountHolderExample example);

    int updateByPrimaryKeySelective(AccountHolder record);

    int updateByPrimaryKey(AccountHolder record);
}