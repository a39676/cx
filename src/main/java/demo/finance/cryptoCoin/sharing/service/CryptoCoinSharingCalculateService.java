package demo.finance.cryptoCoin.sharing.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.sharing.pojo.dto.CryptoCoinShareCalculateDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.CryptoCoinSharingCalculateDetailSearchDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.DeleteSharingDetailDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.ReadCombineSharingDetailDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.UpdateAllocationAssistantDTO;
import demo.finance.cryptoCoin.sharing.pojo.result.CryptoCoinShareCalculateResult;
import demo.finance.cryptoCoin.sharing.pojo.vo.CryptoCoinMiningMachineVO;

public interface CryptoCoinSharingCalculateService {

	List<CryptoCoinMiningMachineVO> getMachineList();

	ModelAndView home();

	CryptoCoinShareCalculateResult getCalculateResult(CryptoCoinShareCalculateDTO dto);

	ModelAndView readSharingDetail(String detailPk);

	ModelAndView sharingCalculateDetailListSearch(CryptoCoinSharingCalculateDetailSearchDTO dto);

	ModelAndView sharingCalculateDetailListSearchView();

	CommonResult deleteSharingDetail(DeleteSharingDetailDTO dto);

	CommonResult updateAssistant(UpdateAllocationAssistantDTO dto);

	ModelAndView readCombineSharingDetail(ReadCombineSharingDetailDTO dto);

}
