package demo.tool.wodian.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.wodian.mapper.WodianClientInfoMapper;
import demo.tool.wodian.mapper.WodianContractInfoMapper;
import demo.tool.wodian.mapper.WodianMerchantsInfoMapper;
import demo.tool.wodian.mapper.WodianSalesmanInfoMapper;
import demo.tool.wodian.pojo.dto.CreateWodianContractDTO;
import demo.tool.wodian.pojo.dto.WodianContractSerachConditionDTO;
import demo.tool.wodian.pojo.dto.WodianSummaryContractDataBySalesmanDTO;
import demo.tool.wodian.pojo.po.WodianClientInfo;
import demo.tool.wodian.pojo.po.WodianClientInfoExample;
import demo.tool.wodian.pojo.po.WodianContractInfo;
import demo.tool.wodian.pojo.po.WodianContractInfoExample;
import demo.tool.wodian.pojo.po.WodianContractInfoExample.Criteria;
import demo.tool.wodian.pojo.po.WodianMerchantsInfo;
import demo.tool.wodian.pojo.po.WodianMerchantsInfoExample;
import demo.tool.wodian.pojo.po.WodianSalesmanInfo;
import demo.tool.wodian.pojo.po.WodianSalesmanInfoExample;
import demo.tool.wodian.pojo.result.WodianContractListResult;
import demo.tool.wodian.pojo.vo.WodianContractInfoVO;
import demo.tool.wodian.service.WodianService;

@Service
public class WodianServiceImpl extends CommonService implements WodianService {

	private static final Integer DEAFULT_CONTRACT_VERSION = 2;

	@Autowired
	private WodianContractInfoMapper contractInfoMapper;
	@Autowired
	private WodianClientInfoMapper clientInfoMapper;
	@Autowired
	private WodianMerchantsInfoMapper merchantsInfoMapper;
	@Autowired
	private WodianSalesmanInfoMapper salesmanInfoMapper;

	@Override
	public ModelAndView summaryView() {
		ModelAndView v = new ModelAndView("toolJSP/wodian/wodian");
		v.addObject("salesmanList", getAllSalesmanList());
		v.addObject("merchantsList", getAllMerchantsList());
		return v;
	}

	@Override
	public ModelAndView getContractListViewByCondition(WodianContractSerachConditionDTO dto) {
		ModelAndView v = new ModelAndView("toolJSP/wodian/wodianContractTable");
		WodianContractListResult result = getContractListByCondition(dto);
		v.addObject("contractVoList", result.getContractList());
		v.addObject("msg", result.getMessage());

		List<WodianSummaryContractDataBySalesmanDTO> summaryList = summaryContractDataBySalesman(result);
		v.addObject("summaryList", summaryList);
		return v;
	}

	private WodianContractListResult getContractListByCondition(WodianContractSerachConditionDTO dto) {
		BigDecimal defaultVersion = new BigDecimal(DEAFULT_CONTRACT_VERSION);
		WodianContractListResult r = new WodianContractListResult();
		List<WodianContractInfoVO> voList = new ArrayList<>();

		WodianContractInfoExample example = new WodianContractInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andVersionEqualTo(defaultVersion);
		LocalDateTime startTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateStr());
		LocalDateTime endTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getEndDateStr());
		if (startTime != null) {
			criteria.andContractCreateTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
			criteria.andContractCreateTimeLessThanOrEqualTo(endTime);
		}
		if (dto.getSalesmanId() != null) {
			criteria.andSalesmanIdEqualTo(dto.getSalesmanId());
		}
		if (dto.getMerchantsId() != null) {
			criteria.andMerchantsIdEqualTo(dto.getMerchantsId());
		}
		List<WodianContractInfo> poList = contractInfoMapper.selectByExample(example);
		Map<Long, WodianMerchantsInfo> allMerchantsMap = getAllMerchantsMap();
		Map<Long, WodianSalesmanInfo> allSalesmanMap = getAllSalesmanMap();

		List<Long> clientIdList = new ArrayList<>();
		for (int i = 0; i < poList.size(); i++) {
			clientIdList.add(poList.get(i).getClientId());
		}
		Map<Long, WodianClientInfo> clientInfoMap = getClientInfoMap(clientIdList);

		for (int i = 0; i < poList.size(); i++) {
			WodianContractInfo po = poList.get(i);
			WodianContractInfoVO vo = new WodianContractInfoVO();
			vo.setId(po.getId());

			vo.setClientId(po.getClientId());
			WodianClientInfo clientInfo = clientInfoMap.get(po.getClientId());
			if (clientInfo != null) {
				vo.setClientName(clientInfo.getName());
			}

			vo.setSalesmanId(po.getSalesmanId());
			WodianSalesmanInfo salesman = allSalesmanMap.get(po.getSalesmanId());
			if (salesman != null) {
				vo.setSalesmanName(salesman.getName());
			}

			vo.setMerchantId(po.getMerchantsId());
			WodianMerchantsInfo merchants = allMerchantsMap.get(po.getMerchantsId());
			if (merchants != null) {
				vo.setMerchantsName(merchants.getName());
			}

			vo.setContractAmount(po.getContractAmount());
			vo.setContractCreateTime(po.getContractCreateTime());
			vo.setContractCreateTimeStr(localDateTimeHandler.dateToStr(po.getContractCreateTime()));
			vo.setGoldCoinForClient(po.getGoldcoinForClient());
			vo.setGoleCoinForMerchants(po.getGoldcoinForMerchants());
			vo.setIntegralForClient(po.getIntegralForClient());
			vo.setIntegralForMerchants(po.getIntegralForMerchants());
			vo.setPartCounts(po.getPartCounts());
			vo.setRemark(po.getRemark());
			voList.add(vo);
		}

		r.setContractList(voList);
		r.setIsSuccess();

		return r;
	}

	private List<WodianSummaryContractDataBySalesmanDTO> summaryContractDataBySalesman(
			WodianContractListResult result) {
		List<WodianSummaryContractDataBySalesmanDTO> list = new ArrayList<>();
		if (result.isFail()) {
			return list;
		}

		Set<Long> salesmanIdSet = new HashSet<>();
		for (int i = 0; i < result.getContractList().size(); i++) {
			WodianContractInfoVO contract = result.getContractList().get(i);
			salesmanIdSet.add(contract.getSalesmanId());
		}
		Map<Long, WodianSummaryContractDataBySalesmanDTO> summaryContractDataBySalesmanMap = new HashMap<>();
		for (int i = 0; i < result.getContractList().size(); i++) {
			WodianContractInfoVO contract = result.getContractList().get(i);
			Long salesmanId = contract.getSalesmanId();
			if (summaryContractDataBySalesmanMap.containsKey(salesmanId)) {
				WodianSummaryContractDataBySalesmanDTO summaryDTO = summaryContractDataBySalesmanMap.get(salesmanId);
				summaryDTO.setSummaryAmount(summaryDTO.getSummaryAmount().add(contract.getContractAmount()));
			} else {
				WodianSummaryContractDataBySalesmanDTO summaryDTO = new WodianSummaryContractDataBySalesmanDTO();
				summaryDTO.setSalesmanId(salesmanId);
				summaryDTO.setSalesmanName(contract.getSalesmanName());
				summaryDTO.setSummaryAmount(contract.getContractAmount());
				summaryContractDataBySalesmanMap.put(salesmanId, summaryDTO);
			}
		}
		list.addAll(summaryContractDataBySalesmanMap.values());
		return list;
	}

	/* 2025-07-05 目前人数较少, 粗暴使用 */
	private Map<Long, WodianMerchantsInfo> getAllMerchantsMap() {
		List<WodianMerchantsInfo> poList = getAllMerchantsList();

		Map<Long, WodianMerchantsInfo> result = new HashMap<>();
		for (int i = 0; i < poList.size(); i++) {
			WodianMerchantsInfo po = poList.get(i);
			result.put(po.getId(), po);
		}
		return result;
	}

	private List<WodianMerchantsInfo> getAllMerchantsList() {
		WodianMerchantsInfoExample example = new WodianMerchantsInfoExample();
		example.createCriteria().andIdGreaterThan(0L);
		List<WodianMerchantsInfo> poList = merchantsInfoMapper.selectByExample(example);
		return poList;
	}

	/* 2025-07-05 目前人数较少, 粗暴使用 */
	private Map<Long, WodianSalesmanInfo> getAllSalesmanMap() {
		List<WodianSalesmanInfo> poList = getAllSalesmanList();

		Map<Long, WodianSalesmanInfo> result = new HashMap<>();
		for (int i = 0; i < poList.size(); i++) {
			WodianSalesmanInfo po = poList.get(i);
			result.put(po.getId(), po);
		}
		return result;
	}

	private List<WodianSalesmanInfo> getAllSalesmanList() {
		WodianSalesmanInfoExample example = new WodianSalesmanInfoExample();
		example.createCriteria().andIdGreaterThan(0L);
		List<WodianSalesmanInfo> poList = salesmanInfoMapper.selectByExample(example);
		return poList;
	}

	private Map<Long, WodianClientInfo> getClientInfoMap(List<Long> clientIdList) {
		WodianClientInfoExample example = new WodianClientInfoExample();
		example.createCriteria().andIdIn(clientIdList);
		List<WodianClientInfo> poList = clientInfoMapper.selectByExample(example);
		if (poList == null || poList.isEmpty()) {
			return new HashMap<Long, WodianClientInfo>();
		}

		Map<Long, WodianClientInfo> result = new HashMap<>();
		for (int i = 0; i < poList.size(); i++) {
			WodianClientInfo po = poList.get(i);
			result.put(po.getId(), po);
		}
		return result;
	}

	@Override
	public CommonResult createContract(CreateWodianContractDTO dto) {
		BigDecimal defaultVersion = new BigDecimal(DEAFULT_CONTRACT_VERSION);
		CommonResult r = new CommonResult();
		WodianClientInfoExample clientExample = new WodianClientInfoExample();
		demo.tool.wodian.pojo.po.WodianClientInfoExample.Criteria criteriaOfName = clientExample.createCriteria();
		criteriaOfName.andNameLike("%" + dto.getClientName() + "%");
		demo.tool.wodian.pojo.po.WodianClientInfoExample.Criteria criteriaOfPhoneNumber = clientExample
				.createCriteria();
		criteriaOfPhoneNumber.andPhoneEqualTo(dto.getClientPhoneNumber());
		clientExample.or(criteriaOfPhoneNumber);
		List<WodianClientInfo> clientInfoList = clientInfoMapper.selectByExample(clientExample);
		Long clientId = null;
		if (clientInfoList == null || clientInfoList.isEmpty()) {
			WodianClientInfo newClientPO = new WodianClientInfo();
			newClientPO.setId(snowFlake.getNextId());
			newClientPO.setName(dto.getClientName());
			newClientPO.setPhone(dto.getClientPhoneNumber());
			clientInfoMapper.insertSelective(newClientPO);
			clientId = newClientPO.getId();
		} else {
			WodianClientInfo po = clientInfoList.get(0);
			clientId = po.getId();
		}
		WodianContractInfo newContractPO = new WodianContractInfo();
		newContractPO.setId(snowFlake.getNextId());
		newContractPO.setClientId(clientId);
		newContractPO.setMerchantsId(dto.getMerchantsId());
		newContractPO.setSalesmanId(dto.getSalesmanId());
		newContractPO.setContractAmount(dto.getContractAmount());
		newContractPO.setContractCreateTime(
				localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getContractCreateTimeStr()));
		newContractPO.setGoldcoinForClient(dto.getGoldCoinForClient());
		newContractPO.setGoldcoinForMerchants(dto.getGoleCoinForMerchants());
		newContractPO.setIntegralForClient(dto.getIntegralForClient());
		newContractPO.setIntegralForMerchants(dto.getIntegralForMerchants());
		newContractPO.setPartCounts(dto.getPartCounts());
		newContractPO.setRemark(dto.getRemark());
		newContractPO.setVersion(defaultVersion);
		contractInfoMapper.insertSelective(newContractPO);

		r.setIsSuccess();
		return r;
	}

	@Override
	public ModelAndView getClientInfo(Long clientId) {
		WodianClientInfo po = clientInfoMapper.selectByPrimaryKey(clientId);
		ModelAndView view = new ModelAndView("toolJSP/wodian/wodianClientInfo");
		if (po == null) {
			return view;
		}
		view.addObject("name", po.getName());
		view.addObject("phone", po.getPhone());
		return view;
	}
}
