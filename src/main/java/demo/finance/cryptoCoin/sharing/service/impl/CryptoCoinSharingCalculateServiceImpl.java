package demo.finance.cryptoCoin.sharing.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.google.gson.Gson;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.sharing.mapper.CryptoCoinAllocationAssistantMapper;
import demo.finance.cryptoCoin.sharing.mapper.CryptoCoinAllocationAssistantMatchMachineMapper;
import demo.finance.cryptoCoin.sharing.mapper.CryptoCoinMiningMachineInfoMapper;
import demo.finance.cryptoCoin.sharing.mapper.CryptoCoinMiningMachineMapper;
import demo.finance.cryptoCoin.sharing.mapper.CryptoCoinShareMapper;
import demo.finance.cryptoCoin.sharing.pojo.dto.CryptoCoinShareCalculateDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.CryptoCoinSharingCalculateDetailSearchDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.DeleteSharingDetailDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.ReadCombineSharingDetailDTO;
import demo.finance.cryptoCoin.sharing.pojo.dto.UpdateAllocationAssistantDTO;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistant;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistantExample;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistantMatchMachine;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinAllocationAssistantMatchMachineExample;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachine;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachineExample;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachineInfo;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinMiningMachineInfoExample;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinShare;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinShareExample;
import demo.finance.cryptoCoin.sharing.pojo.po.CryptoCoinShareExample.Criteria;
import demo.finance.cryptoCoin.sharing.pojo.result.CryptoCoinShareCalculateResult;
import demo.finance.cryptoCoin.sharing.pojo.result.CryptoCoinShareCalculateSubResult;
import demo.finance.cryptoCoin.sharing.pojo.vo.AllocationAssistantVO;
import demo.finance.cryptoCoin.sharing.pojo.vo.CryptoCoinMachineInfoVO;
import demo.finance.cryptoCoin.sharing.pojo.vo.CryptoCoinMiningMachineVO;
import demo.finance.cryptoCoin.sharing.pojo.vo.CryptoCoinShareVO;
import demo.finance.cryptoCoin.sharing.service.CryptoCoinSharingCalculateService;
import net.sf.json.JSONObject;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CryptoCoinSharingCalculateServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinSharingCalculateService {

	@Autowired
	private FileUtilCustom ioUtil;
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
	@Autowired
	private CryptoCoinShareMapper shareResultMapper;

	@Override
	public List<CryptoCoinMiningMachineVO> getMachineList() {
		return getMachineVoList(null);
	}

	@Override
	public CryptoCoinShareCalculateResult getCalculateResult(CryptoCoinShareCalculateDTO dto) {
		CryptoCoinShareCalculateResult result = new CryptoCoinShareCalculateResult();
		if (StringUtils.isBlank(dto.getMachinePK()) || dto.getGetCoinCounting() == null
				|| dto.getGetCoinCounting().compareTo(BigDecimal.ZERO) < 1) {
			return result;
		}

		Long machineId = systemOptionService.decryptPrivateKey(dto.getMachinePK());
		if (machineId == null) {
			return result;
		}
		LocalDate markDate = null;
		try {
			markDate = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getMarkDateStr()).toLocalDate();
		} catch (Exception e) {
			return result;
		}

		List<CryptoCoinMiningMachineVO> machineVoList = getMachineVoList(machineId);
		if (machineVoList == null || machineVoList.isEmpty()) {
			return result;
		}

		result.setMarkDateStr(
				localDateTimeHandler.dateToStr(markDate.atStartOfDay(), DateTimeUtilCommon.dateFormatNoSymbol));

		CryptoCoinMiningMachineVO machineVO = machineVoList.get(0);
		result.setPartingCount(machineVO.getPartingCount());
		result.setTotalOutput(dto.getGetCoinCounting());

		BigDecimal restAfterHanldingFee = dto.getGetCoinCounting()
				.multiply(BigDecimal.ONE.subtract(machineVO.getHandlingFeeRate()))
				.setScale(optionService.getScaleOfSharingCalculate(), RoundingMode.DOWN);
		result.setRestAfterHanldingFee(restAfterHanldingFee);

		BigDecimal handlingFee = dto.getGetCoinCounting().subtract(restAfterHanldingFee);
		result.setHanldingFee(handlingFee);

		BigDecimal coinCountingOfEachPartOfMachine = restAfterHanldingFee.divide(
				new BigDecimal(machineVO.getPartingCount()), optionService.getScaleOfSharingCalculate(),
				RoundingMode.DOWN);
		result.setCoinCountingOfEachPartOfMachine(coinCountingOfEachPartOfMachine);
		result.setHanldingFeeRate(machineVO.getHandlingFeeRate());

		BigDecimal assistantTotalCoinCounting = null;
		BigDecimal commissionFeeOfOneParting = null;
		BigDecimal commissionFee = null;
		BigDecimal receiveFromParting = null;
		BigDecimal commissionFeeTotal = BigDecimal.ZERO;
		BigDecimal totalSharingCounting = BigDecimal.ZERO;

		CryptoCoinShareCalculateSubResult subResult = null;
		for (AllocationAssistantVO assistant : machineVO.getAssistantList()) {
			subResult = new CryptoCoinShareCalculateSubResult();

			receiveFromParting = coinCountingOfEachPartOfMachine.multiply(assistant.getPartingCount())
					.setScale(optionService.getScaleOfSharingCalculate(), RoundingMode.DOWN);

			commissionFeeOfOneParting = coinCountingOfEachPartOfMachine.multiply(assistant.getCommissionFeeRate())
					.setScale(optionService.getScaleOfSharingCalculate(), RoundingMode.DOWN);

			commissionFee = commissionFeeOfOneParting.multiply(assistant.getPartingCount())
					.setScale(optionService.getScaleOfSharingCalculate(), RoundingMode.DOWN);

			commissionFeeTotal = commissionFeeTotal.add(commissionFee);

			assistantTotalCoinCounting = receiveFromParting.add(commissionFee);

			totalSharingCounting = totalSharingCounting.add(assistantTotalCoinCounting);

			subResult.setAssistantPK(assistant.getPk());
			subResult.setAssistantName(assistant.getName());
			subResult.setPartingCount(assistant.getPartingCount());
			subResult.setTotalCoinCounting(assistantTotalCoinCounting);
			subResult.setCommissionFee(commissionFee);
			subResult.setCommissionFeeRate(assistant.getCommissionFeeRate());
			subResult.setReceiveFromParting(receiveFromParting);
			subResult.setCommissionFeeOfOneParting(commissionFeeOfOneParting);
			result.addCaculateResult(subResult);
		}

		result.setRestAfterCommissionFee(handlingFee.subtract(commissionFeeTotal));
		result.setNetIncome(dto.getGetCoinCounting().subtract(totalSharingCounting));

		Long sharingResultId = snowFlake.getNextId();
		CryptoCoinShare shareResultPO = new CryptoCoinShare();
		shareResultPO.setId(sharingResultId);

		String storePrefixPath = optionService.getSharingCalculateResultSavingPath();
		String fileName = sharingResultId + "L" + ".txt";
		String timeFolder = LocalDate.now().toString();
		File mainFolder = new File(storePrefixPath + "/" + timeFolder);
		String finalFilePath = storePrefixPath + "/" + timeFolder + "/" + fileName;
		shareResultPO.setFilePath(finalFilePath);
		shareResultPO.setMiningMachineId(machineId);
		shareResultPO.setOutputTime(markDate.atStartOfDay());

		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				result.setMessage("计算结果保存异常, 请联系管理员");
				return result;
			}
		}

		result.setIsSuccess();

		StringBuffer sb = new StringBuffer();

		JSONObject resultJson = JSONObject.fromObject(result);

		sb.append(resultJson.toString());

		ioUtil.byteToFile(sb.toString().getBytes(StandardCharsets.UTF_8), finalFilePath);

		shareResultMapper.insertSelective(shareResultPO);

		return result;
	}

	private List<CryptoCoinMiningMachineVO> getMachineVoList(Long machineId) {
		List<CryptoCoinMiningMachineVO> result = new ArrayList<>();
		CryptoCoinMiningMachineExample machineExample = new CryptoCoinMiningMachineExample();
		if (machineId == null) {
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
		machineInfoExample.createCriteria().andIsDeleteEqualTo(false).andMiningMachineIdIn(machineIdList);
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

		return result;
	}

	private AllocationAssistantVO buildAssistantVO(CryptoCoinMiningMachine machine,
			CryptoCoinAllocationAssistant assistant, CryptoCoinAllocationAssistantMatchMachine assistantMatchInfo) {
		AllocationAssistantVO vo = new AllocationAssistantVO();
		vo.setCommissionFeeRate(assistantMatchInfo.getCommissionFeeRate());
		vo.setPk(systemOptionService.encryptId(assistant.getId()));
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
				machineVO.setMachinePK(systemOptionService.encryptId(machineId));
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
			result.put(machineId, machineVO);
		}

		return result;
	}

	@Override
	public ModelAndView home() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinSharingCalculate");
		view.addObject("machineList", getMachineList());
		view.addObject("today", LocalDate.now());
		return view;
	}

	@Override
	public ModelAndView readCombineSharingDetail(ReadCombineSharingDetailDTO dto) {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinSharingCalculateDetail");

		if (dto.getDetailPkList() == null || dto.getDetailPkList().isEmpty()) {
			return view;
		}
		
		if(dto.getDetailPkList().size() == 1) {
			return readSharingDetail(dto.getDetailPkList().get(0));
		}

		List<String> decodePkList = new ArrayList<>();
		for(String pk : dto.getDetailPkList()) {
			decodePkList.add(URLDecoder.decode(pk, StandardCharsets.UTF_8));
		}
		List<Long> idList = systemOptionService.decryptPrivateKey(decodePkList);
		if (idList.isEmpty()) {
			return view;
		}

		CryptoCoinShare po = null;
		List<CryptoCoinShare> poList = new ArrayList<>();
		for (Long id : idList) {
			po = shareResultMapper.selectByPrimaryKey(id);
			if (po == null || (po.getIsDelete() && !baseUtilCustom.hasAdminRole())) {
				continue;
			}
			poList.add(po);
		}
		
		if(poList.isEmpty()) {
			return view;
		}

		CryptoCoinShare firstpo = poList.get(0);
		String content = ioUtil.getStringFromFile(firstpo.getFilePath());
		CryptoCoinShareCalculateResult resultDetail = new Gson().fromJson(content, CryptoCoinShareCalculateResult.class);
		for (int i = 1; i < poList.size(); i++) {
			content = ioUtil.getStringFromFile(poList.get(i).getFilePath());
			CryptoCoinShareCalculateResult tmpDetail = new Gson().fromJson(content, CryptoCoinShareCalculateResult.class);
			resultDetail = combineCryptoCoinShareCalculateResult(resultDetail, tmpDetail);
		}

		view.addObject("detail", resultDetail);

		return view;
	}

	public CryptoCoinShareCalculateResult combineCryptoCoinShareCalculateResult(CryptoCoinShareCalculateResult result,
			CryptoCoinShareCalculateResult source) {
		result.setMarkDateStr(source.getMarkDateStr());
		result.setTotalOutput(result.getTotalOutput().add(source.getTotalOutput()));
		result.setPartingCount(source.getPartingCount());
		result.setCoinCountingOfEachPartOfMachine(result.getCoinCountingOfEachPartOfMachine().add(source.getCoinCountingOfEachPartOfMachine()));
		result.setCoinCountingOfEachPartOfMachine(source.getCoinCountingOfEachPartOfMachine());
		result.setHanldingFeeRate(source.getHanldingFeeRate());
		result.setHanldingFee(result.getHanldingFee().add(source.getHanldingFee()));
		result.setRestAfterHanldingFee(result.getRestAfterHanldingFee().add(source.getRestAfterHanldingFee()));
		result.setRestAfterCommissionFee(result.getRestAfterCommissionFee().add(source.getRestAfterCommissionFee()));
		result.setNetIncome(result.getNetIncome().add(source.getNetIncome()));
		
		HashMap<String, CryptoCoinShareCalculateSubResult> sourceSubResultMap = new HashMap<>();
		for(CryptoCoinShareCalculateSubResult subResult : source.getCaculateResultList()) {
			sourceSubResultMap.put(subResult.getAssistantPK(), subResult);
		}
		
		for(CryptoCoinShareCalculateSubResult subResult : result.getCaculateResultList()) {
			CryptoCoinShareCalculateSubResult sourceSubResult = sourceSubResultMap.get(subResult.getAssistantPK());
			if(sourceSubResult == null) {
				continue;
			}
			subResult = combineCryptoCoinShareCalculateSubResult(subResult, sourceSubResult);
		}
		
		return result;
	}

	private CryptoCoinShareCalculateSubResult combineCryptoCoinShareCalculateSubResult(
			CryptoCoinShareCalculateSubResult result, CryptoCoinShareCalculateSubResult source) {
		result.setReceiveFromParting(result.getReceiveFromParting().add(source.getReceiveFromParting()));
		result.setCommissionFee(result.getCommissionFee().add(source.getCommissionFee()));
		result.setTotalCoinCounting(result.getTotalCoinCounting().add(source.getTotalCoinCounting()));
		result.setCommissionFeeOfOneParting(result.getCommissionFeeOfOneParting().add(source.getCommissionFeeOfOneParting()));
		return result;
	}

	public ModelAndView readSharingDetail(String detailPk) {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinSharingCalculateDetail");

		if (StringUtils.isBlank(detailPk)) {
			return view;
		}

		Long id = systemOptionService.decryptPrivateKey(detailPk);
		if (id == null) {
			return view;
		}

		CryptoCoinShare po = shareResultMapper.selectByPrimaryKey(id);
		if (po == null || (po.getIsDelete() && !baseUtilCustom.hasAdminRole())) {
			return view;
		}

		String content = ioUtil.getStringFromFile(po.getFilePath());

		try {
			CryptoCoinShareCalculateResult detail = new Gson().fromJson(content, CryptoCoinShareCalculateResult.class);
			view.addObject("detail", detail);
		} catch (Exception e) {
		}

		return view;
	}

	@Override
	public CommonResult deleteSharingDetail(DeleteSharingDetailDTO dto) {
		CommonResult r = new CommonResult();

		if (StringUtils.isBlank(dto.getPk())) {
			return r;
		}

		dto.setPk(URLDecoder.decode(dto.getPk(), StandardCharsets.UTF_8));
		Long id = systemOptionService.decryptPrivateKey(dto.getPk());
		if (id == null) {
			return r;
		}

		CryptoCoinShare po = shareResultMapper.selectByPrimaryKey(id);
		if (po == null) {
			return r;
		}

		po.setIsDelete(true);
		shareResultMapper.updateByPrimaryKeySelective(po);

		r.setIsSuccess();
		return r;
	}

	@Override
	public ModelAndView sharingCalculateDetailListSearch(CryptoCoinSharingCalculateDetailSearchDTO dto) {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinSharingCalculateDetailSearchResult");

		if (StringUtils.isBlank(dto.getPk())) {
			return view;
		}

		Long machineId = systemOptionService.decryptPrivateKey(dto.getPk());
		if (machineId == null) {
			return view;
		}

		CryptoCoinShareExample example = new CryptoCoinShareExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(false).andMiningMachineIdEqualTo(machineId);

		if (StringUtils.isNotBlank(dto.getStartDateStr())) {
			try {
				LocalDateTime startTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateStr());
				criteria.andOutputTimeGreaterThanOrEqualTo(startTime);
			} catch (Exception e) {
			}
		}

		if (StringUtils.isNotBlank(dto.getEndDateStr())) {
			try {
				LocalDateTime endTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getEndDateStr());
				criteria.andOutputTimeLessThanOrEqualTo(endTime);
			} catch (Exception e) {
			}
		}
		example.setOrderByClause(" output_time ");

		List<CryptoCoinShare> detailList = shareResultMapper.selectByExample(example);

		List<CryptoCoinShareVO> voList = new ArrayList<>();
		for (CryptoCoinShare po : detailList) {
			voList.add(buildCryptoCoinShareVO(po));
		}

		view.addObject("detailList", voList);

		return view;
	}

	private CryptoCoinShareVO buildCryptoCoinShareVO(CryptoCoinShare po) {
		CryptoCoinShareVO vo = new CryptoCoinShareVO();
		vo.setOutputTime(po.getOutputTime());
		vo.setOutputTimeStr(localDateTimeHandler.dateToStr(po.getOutputTime(), DateTimeUtilCommon.normalDateFormat));
		try {
			vo.setPk(URLEncoder.encode(systemOptionService.encryptId(po.getId()), StandardCharsets.UTF_8.toString()));
		} catch (UnsupportedEncodingException e) {
		}
		return vo;
	}

	@Override
	public ModelAndView sharingCalculateDetailListSearchView() {
		ModelAndView view = new ModelAndView("finance/cryptoCoin/CryptoCoinSharingCalculateDetailSearch");

		CryptoCoinMiningMachineExample machineExample = new CryptoCoinMiningMachineExample();
		machineExample.createCriteria().andIsDeleteEqualTo(false);

		List<CryptoCoinMiningMachine> machineList = machineMapper.selectByExample(machineExample);
		if (machineList == null || machineList.isEmpty()) {
			return view;
		}
		List<Long> machineIdList = machineList.stream().map(po -> po.getId()).collect(Collectors.toList());

		CryptoCoinMiningMachineInfoExample machineInfoExample = new CryptoCoinMiningMachineInfoExample();
		machineInfoExample.createCriteria().andIsDeleteEqualTo(false).andMiningMachineIdIn(machineIdList);
		;
		List<CryptoCoinMiningMachineInfo> machineInfoList = machineInfoMapper.selectByExample(machineInfoExample);
		Map<Long, CryptoCoinMiningMachineInfo> machineInfoMap = new HashMap<>();
		for (CryptoCoinMiningMachineInfo machineInfo : machineInfoList) {
			machineInfoMap.put(machineInfo.getMiningMachineId(), machineInfo);
		}

		List<CryptoCoinMachineInfoVO> voList = new ArrayList<>();
		for (CryptoCoinMiningMachine machine : machineList) {
			voList.add(buildCryptoCoinMachineInfoVO(machine, machineInfoMap.get(machine.getId())));
		}

		view.addObject("startTime", LocalDate.now().minusMonths(2));
		view.addObject("today", LocalDate.now());
		view.addObject("machineList", voList);
		return view;
	}

	private CryptoCoinMachineInfoVO buildCryptoCoinMachineInfoVO(CryptoCoinMiningMachine machine,
			CryptoCoinMiningMachineInfo info) {
		CryptoCoinMachineInfoVO vo = new CryptoCoinMachineInfoVO();

		vo.setPk(systemOptionService.encryptId(machine.getId()));
		vo.setMachineName(machine.getMachineName());

		CryptoCoinCatalog coinCatalog = coinCatalogService.findCatalog(machine.getCryptoId());
		if (coinCatalog != null) {
			vo.setCoinName(coinCatalog.getCoinNameEnShort());
		}

		if (info != null) {
			vo.setHandlingFeeRate(info.getHandlingFeeRate());
			vo.setPartingCount(info.getPartingCount());
		}
		return vo;
	}

	@Override
	public CommonResult updateAssistant(UpdateAllocationAssistantDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isAnyBlank(dto.getAssistantPK(), dto.getMachinePK())) {
			return r;
		}

		Long machineId = systemOptionService.decryptPrivateKey(dto.getMachinePK());
		Long assistantId = systemOptionService.decryptPrivateKey(dto.getAssistantPK());

		if (machineId == null || assistantId == null) {
			return r;
		}

		CryptoCoinAllocationAssistantMatchMachineExample example = new CryptoCoinAllocationAssistantMatchMachineExample();
		example.createCriteria().andIsDeleteEqualTo(false).andAssistantIdEqualTo(assistantId)
				.andMiningMachineIdEqualTo(machineId);
		List<CryptoCoinAllocationAssistantMatchMachine> matchList = assistantMatchMachineMapper
				.selectByExample(example);
		if (matchList == null || matchList.isEmpty()) {
			return r;
		}

		CryptoCoinAllocationAssistantMatchMachine po = matchList.get(0);
		po.setUdpateTime(LocalDateTime.now());
		po.setCommissionFeeRate(dto.getCommissionFeeRateInPercent().divide(new BigDecimal(100)));
		po.setPartingCount(dto.getPartingCount());

		assistantMatchMachineMapper.updateByPrimaryKeySelective(po);
		r.setIsSuccess();
		return r;
	}
}
