package demo.finance.cryptoCoin.mining.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.mining.pojo.dto.CryptoCoinShareCalculateDTO;
import demo.finance.cryptoCoin.mining.pojo.result.CryptoCoinShareCalculateResult;
import demo.finance.cryptoCoin.mining.pojo.vo.CryptoCoinMiningMachineVO;

public interface CryptoCoinSharingCalculateService {

	List<CryptoCoinMiningMachineVO> getMachineList();

	ModelAndView home();

	CryptoCoinShareCalculateResult getCalculateResult(CryptoCoinShareCalculateDTO dto);

	ModelAndView readSharingDetail(Long id);

}
