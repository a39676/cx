package demo.finance.cryptoCoin.data.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBtcAndLowIndexGapDTO;

public interface CryptoCoinComplexToolMapper {

	List<CryptoCoinBtcAndLowIndexGapDTO> selectGaps(@Param(value = "startTime") LocalDateTime startTime);
}
