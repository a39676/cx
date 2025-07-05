package demo.tool.wodian.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//		TODO 未指定页面
		ModelAndView v = new ModelAndView("");
		return v;
	}
	
	@Override
	public WodianContractListResult getContractListByCondition(WodianContractSerachConditionDTO dto) {
		WodianContractListResult r = new WodianContractListResult();
		List<WodianContractInfoVO> voList = new ArrayList<>();

		WodianContractInfoExample example = new WodianContractInfoExample();
		Criteria criteria = example.createCriteria();
		LocalDateTime startTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getStartDateStr());
		LocalDateTime endTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getEndDateStr());
		if (startTime != null) {
			criteria.andContractCreateTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
			criteria.andContractCreateTimeLessThanOrEqualTo(endTime);
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

	/* 2025-07-05 目前人数较少, 粗暴使用 */
	private Map<Long, WodianMerchantsInfo> getAllMerchantsMap() {
		WodianMerchantsInfoExample example = new WodianMerchantsInfoExample();
		example.createCriteria().andIdGreaterThan(0L);
		List<WodianMerchantsInfo> poList = merchantsInfoMapper.selectByExample(example);

		Map<Long, WodianMerchantsInfo> result = new HashMap<>();
		for (int i = 0; i < poList.size(); i++) {
			WodianMerchantsInfo po = poList.get(i);
			result.put(po.getId(), po);
		}
		return result;
	}

	/* 2025-07-05 目前人数较少, 粗暴使用 */
	private Map<Long, WodianSalesmanInfo> getAllSalesmanMap() {
		WodianSalesmanInfoExample example = new WodianSalesmanInfoExample();
		example.createCriteria().andIdGreaterThan(0L);
		List<WodianSalesmanInfo> poList = salesmanInfoMapper.selectByExample(example);

		Map<Long, WodianSalesmanInfo> result = new HashMap<>();
		for (int i = 0; i < poList.size(); i++) {
			WodianSalesmanInfo po = poList.get(i);
			result.put(po.getId(), po);
		}
		return result;
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
		contractInfoMapper.insertSelective(newContractPO);

		r.setIsSuccess();
		return r;
	}
}
