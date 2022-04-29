package demo.finance.cryptoCoin.mining.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.mining.pojo.vo.CryptoCoinMiningMachineVO;

public interface CryptoCoinMiningCalculateService {

	List<CryptoCoinMiningMachineVO> getMachineList();

	ModelAndView home();

}
