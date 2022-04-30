package demo.finance.cryptoCoin.mining.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.mining.mapper.CryptoCoinAllocationAssistantMapper;
import demo.finance.cryptoCoin.mining.mapper.CryptoCoinAllocationAssistantMatchMachineMapper;
import demo.finance.cryptoCoin.mining.mapper.CryptoCoinMiningMachineInfoMapper;
import demo.finance.cryptoCoin.mining.mapper.CryptoCoinMiningMachineMapper;
import demo.finance.cryptoCoin.mining.pojo.dto.CryptoCoinShareCalculateDTO;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinAllocationAssistant;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinAllocationAssistantExample;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinAllocationAssistantMatchMachine;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinAllocationAssistantMatchMachineExample;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachine;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineExample;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineInfo;
import demo.finance.cryptoCoin.mining.pojo.po.CryptoCoinMiningMachineInfoExample;
import demo.finance.cryptoCoin.mining.pojo.result.CryptoCoinShareCalculateResult;
import demo.finance.cryptoCoin.mining.pojo.vo.AllocationAssistantVO;
import demo.finance.cryptoCoin.mining.pojo.vo.CryptoCoinMiningMachineVO;
import demo.finance.cryptoCoin.mining.service.CryptoCoinMiningCalculateService;

@Service
public class CryptoCoinMiningCalculateServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinMiningCalculateService {

	@Autowired
	private CryptoCoinCatalogService coinCatalogService;
	@Autowired
	private CryptoCoinMiningMachineMapper machineMapper;
	@Autowired
	private CryptoCoinMiningMachineInfoMapper machineInfoMapper;
	@Autowired
	private CryptoCoinAllocationAssistantMatchMachineMapper assistantMatchMachineMapper;
	@Autowired
	private CryptoCoinAllocationAssistantMapper assistantMapper;
	
	private static final int SCALE = 4;

	@Override
	public List<CryptoCoinMiningMachineVO> getMachineList() {
		return getMachineVoList(null);
	}
	
	public CryptoCoinShareCalculateResult getCalculateResult(CryptoCoinShareCalculateDTO dto) {
//		TODO
		CryptoCoinShareCalculateResult r = new CryptoCoinShareCalculateResult();
		if(StringUtils.isBlank(dto.getMachineIdStr())) {
			return r;
		}
		
		Long machineId = null;
		LocalDate markDate = null;
		try {
			machineId = Long.parseLong(dto.getMachineIdStr());
			markDate = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getMarkDateStr()).toLocalDate();
		} catch (Exception e) {
			return r;
		}
		
		List<CryptoCoinMiningMachineVO> machineVoList = getMachineVoList(machineId);
		if(machineVoList == null || machineVoList.isEmpty()) {
			return r;
		}
		
		CryptoCoinMiningMachineVO machineVO = machineVoList.get(0);
		
		BigDecimal handlingFee = dto.getGetCoinCounting().multiply(machineVO.getHandlingFeeRate());
		BigDecimal restAfterHaldingFee = dto.getGetCoinCounting().subtract(handlingFee);
		
		BigDecimal coinCountingOfEachPartOfMachine = restAfterHaldingFee.divide(new BigDecimal(machineVO.getPartingCount()), SCALE, RoundingMode.DOWN);
		
//		TODO
		
		return r;
	}
	
	private List<CryptoCoinMiningMachineVO> getMachineVoList(Long machineId) {
		List<CryptoCoinMiningMachineVO> result = new ArrayList<>();
		CryptoCoinMiningMachineExample machineExample = new CryptoCoinMiningMachineExample();
		if(machineId == null) {
			machineExample.createCriteria().andIsDeleteEqualTo(false);
		} else {
			machineExample.createCriteria().andIsDeleteEqualTo(false).andIdEqualTo(machineId);
		}
		List<CryptoCoinMiningMachine> machineList = machineMapper.selectByExample(machineExample);
		if (machineList == null || machineList.isEmpty()) {
			return result;
		}
		Map<Long, CryptoCoinMiningMachine> machineMap = new HashMap<>();
		for (CryptoCoinMiningMachine machine : machineList) {
			machineMap.put(machine.getId(), machine);
		}

		List<Long> machineIdList = machineList.stream().map(po -> po.getId()).collect(Collectors.toList());

		CryptoCoinMiningMachineInfoExample machineInfoExample = new CryptoCoinMiningMachineInfoExample();
		machineInfoExample.createCriteria().andIsDeleteEqualTo(false);
		List<CryptoCoinMiningMachineInfo> machineInfoList = machineInfoMapper.selectByExample(machineInfoExample);
		Map<Long, CryptoCoinMiningMachineInfo> machineInfoMap = new HashMap<>();
		for (CryptoCoinMiningMachineInfo machineInfo : machineInfoList) {
			machineInfoMap.put(machineInfo.getMiningMachineId(), machineInfo);
		}

		CryptoCoinAllocationAssistantMatchMachineExample assistantMatchMachineExample = new CryptoCoinAllocationAssistantMatchMachineExample();
		assistantMatchMachineExample.createCriteria().andIsDeleteEqualTo(false).andMiningMachineIdIn(machineIdList);
		List<CryptoCoinAllocationAssistantMatchMachine> assistantMatchList = assistantMatchMachineMapper
				.selectByExample(assistantMatchMachineExample);
		Set<Long> assistantIdSet = new HashSet<>();
		for (CryptoCoinAllocationAssistantMatchMachine assistantMatch : assistantMatchList) {
			assistantIdSet.add(assistantMatch.getAssistantId());
		}

		List<Long> assistantIdList = new ArrayList<>();
		assistantIdList.addAll(assistantIdSet);
		CryptoCoinAllocationAssistantExample assistantExample = new CryptoCoinAllocationAssistantExample();
		assistantExample.createCriteria().andIsDeleteEqualTo(false).andIdIn(assistantIdList);
		List<CryptoCoinAllocationAssistant> assistantList = assistantMapper.selectByExample(assistantExample);
		Map<Long, CryptoCoinAllocationAssistant> assistantMap = new HashMap<>();
		for (CryptoCoinAllocationAssistant assistant : assistantList) {
			assistantMap.put(assistant.getId(), assistant);
		}

		Map<Long, CryptoCoinMiningMachineVO> cryptoCoinMiningMachineVOMap = buildCryptoCoinMiningMachineVOMap(
				machineMap, machineInfoMap, assistantMatchList, assistantMap);
		result.addAll(cryptoCoinMiningMachineVOMap.values());

		Collections.sort(result);

		return result;
	}

	private AllocationAssistantVO buildAssistantVO(CryptoCoinMiningMachine machine,
			CryptoCoinAllocationAssistant assistant, CryptoCoinAllocationAssistantMatchMachine assistantMatchInfo) {
		AllocationAssistantVO vo = new AllocationAssistantVO();
		vo.setCommissionFeeRate(assistantMatchInfo.getCommissionFeeRate());
		vo.setId(assistant.getId());
		vo.setIdStr(assistant.getId().toString());
		vo.setMachineId(machine.getId());
		vo.setMachineIdStr(machine.getId().toString());
		vo.setName(assistant.getNameCn());
		vo.setPartingCount(assistantMatchInfo.getPartingCount());
		return vo;
	}

	private Map<Long, CryptoCoinMiningMachineVO> buildCryptoCoinMiningMachineVOMap(
			Map<Long, CryptoCoinMiningMachine> machineMap, Map<Long, CryptoCoinMiningMachineInfo> machineInfoMap,
			List<CryptoCoinAllocationAssistantMatchMachine> assistantMatchList,
			Map<Long, CryptoCoinAllocationAssistant> assistantMap) {
		Map<Long, CryptoCoinMiningMachineVO> result = new HashMap<>();
		CryptoCoinMiningMachineVO machineVO = null;
		AllocationAssistantVO assistantVO = null;
		Long machineId = null;
		Long assistantId = null;
		CryptoCoinMiningMachine machine = null;
		CryptoCoinMiningMachineInfo machineInfo = null;
		CryptoCoinAllocationAssistant assistant = null;
		CryptoCoinCatalog coinCatalog = null;
		for (CryptoCoinAllocationAssistantMatchMachine assistantMatchInfo : assistantMatchList) {
			machineId = assistantMatchInfo.getMiningMachineId();
			assistantId = assistantMatchInfo.getAssistantId();
			machine = machineMap.get(machineId);
			machineInfo = machineInfoMap.get(machineId);
			assistant = assistantMap.get(assistantId);

			if (!result.containsKey(machineId)) {
				machineVO = new CryptoCoinMiningMachineVO();
				coinCatalog = coinCatalogService.findCatalog(machine.getCryptoId());
				machineVO.setId(machine.getId());
				machineVO.setIdStr(machine.getId().toString());
				machineVO.setMachineName(machine.getMachineName());
				machineVO.setCoinId(coinCatalog.getId());
				machineVO.setCoinIdStr(coinCatalog.getId().toString());
				machineVO.setCoinName(coinCatalog.getCoinNameEnShort());
				machineVO.setHandlingFeeRate(machineInfo.getHandlingFeeRate());
				machineVO.setPartingCount(machineInfo.getPartingCount());
			} else {
				machineVO = result.get(machineId);
			}

			assistantVO = buildAssistantVO(machine, assistant, assistantMatchInfo);
			machineVO.addAssistant(assistantVO);
			result.put(machineVO.getId(), machineVO);
		}

		return result;
	}

	@Override
	public ModelAndView home() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinMiningCalculate");
		view.addObject("machineList", getMachineList());
		view.addObject("today", LocalDate.now());
		return view;
	}

}
