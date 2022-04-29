package demo.finance.cryptoCoin.mining.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.mining.mapper.CryptoCoinMiningMachineInfoMapper;
import demo.finance.cryptoCoin.mining.mapper.CryptoCoinMiningMachineMapper;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachine;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineExample;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineInfo;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineInfoExample;
import demo.finance.cryptoCoin.mining.pojo.vo.CryptoCoinMiningMachineVO;
import demo.finance.cryptoCoin.mining.service.CryptoCoinMiningCalculateService;

@Service
public class CryptoCoinMiningCalculateServiceImpl extends CryptoCoinCommonService implements CryptoCoinMiningCalculateService {

	@Autowired
	private CryptoCoinCatalogService coinCatalogService;
	@Autowired
	private CryptoCoinMiningMachineMapper machineMapper;
	@Autowired
	private CryptoCoinMiningMachineInfoMapper machineInfoMapper;
	
	@Override
	public List<CryptoCoinMiningMachineVO> getMachineList() {
		List<CryptoCoinMiningMachineVO> result = new ArrayList<>();
		CryptoCoinMiningMachineExample machineExample = new CryptoCoinMiningMachineExample();
		machineExample.createCriteria().andIsDeleteEqualTo(false);
		List<CryptoCoinMiningMachine> machineList = machineMapper.selectByExample(machineExample);
		
		if(machineList == null || machineList.isEmpty()) {
			return result;
		}
		
		CryptoCoinMiningMachineInfoExample machineInfoExample = new CryptoCoinMiningMachineInfoExample();
		machineInfoExample.createCriteria().andIsDeleteEqualTo(false);
		List<CryptoCoinMiningMachineInfo> machineInfoList = machineInfoMapper.selectByExample(machineInfoExample);
		Map<Long, CryptoCoinMiningMachineInfo> machineInfoMap = new HashMap<>();
		for(CryptoCoinMiningMachineInfo machineInfo : machineInfoList) {
			machineInfoMap.put(machineInfo.getMiningMachineId(), machineInfo);
		}
		
		CryptoCoinCatalog catalog = null;
		
		for(CryptoCoinMiningMachine machine : machineList) {
			catalog = coinCatalogService.findCatalog(machine.getCryptoId());
			result.add(buildVO(machine, machineInfoMap.get(machine.getId()), catalog));
		}
		
		return result;
	}
	
	private CryptoCoinMiningMachineVO buildVO(CryptoCoinMiningMachine machine, CryptoCoinMiningMachineInfo info, CryptoCoinCatalog coinCatalog) {
		CryptoCoinMiningMachineVO vo = new CryptoCoinMiningMachineVO();
		vo.setId(machine.getId().toString());
		vo.setMachineName(machine.getMachineName());
		vo.setCoinId(coinCatalog.getId().toString());
		vo.setCoinName(coinCatalog.getCoinNameEnShort());
		vo.setHandlingFeeRate(info.getHandlingFeeRate());
		vo.setPartingCount(info.getPartingCount());
		return vo;
	}

	@Override
	public ModelAndView home() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinMiningCalculate");
		view.addObject("machineList", getMachineList());
		return view;
	}
	
}
