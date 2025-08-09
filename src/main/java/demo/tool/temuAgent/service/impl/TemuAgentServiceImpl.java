package demo.tool.temuAgent.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.temuAgent.mapper.TemuAgentProductBuyAndSellHistoryMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductBuyAndSellStatisticsMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductFlowHistoryMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductFlowStatisticsMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductModelMapper;
import demo.tool.temuAgent.pojo.dto.TemuAgentCeateProductDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentCreateOrUpdateProductModelDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentProductModelAddFlowDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentProductModelDetailSearchDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentSearchProductDTO;
import demo.tool.temuAgent.pojo.po.TemuAgentProduct;
import demo.tool.temuAgent.pojo.po.TemuAgentProductBuyAndSellHistory;
import demo.tool.temuAgent.pojo.po.TemuAgentProductBuyAndSellStatistics;
import demo.tool.temuAgent.pojo.po.TemuAgentProductBuyAndSellStatisticsExample;
import demo.tool.temuAgent.pojo.po.TemuAgentProductExample;
import demo.tool.temuAgent.pojo.po.TemuAgentProductExample.Criteria;
import demo.tool.temuAgent.pojo.po.TemuAgentProductFlowHistory;
import demo.tool.temuAgent.pojo.po.TemuAgentProductFlowStatistics;
import demo.tool.temuAgent.pojo.po.TemuAgentProductFlowStatisticsExample;
import demo.tool.temuAgent.pojo.po.TemuAgentProductModel;
import demo.tool.temuAgent.pojo.po.TemuAgentProductModelExample;
import demo.tool.temuAgent.pojo.type.TemuAgentProductFlowType;
import demo.tool.temuAgent.pojo.type.TemuAgentProductModelUnitType;
import demo.tool.temuAgent.pojo.vo.TemuAgentProductModelStatisticsVO;
import demo.tool.temuAgent.service.TemuAgentService;
import io.micrometer.core.instrument.util.StringUtils;

@Service
public class TemuAgentServiceImpl extends CommonService implements TemuAgentService {

	@Autowired
	private TemuAgentProductMapper productMapper;
	@Autowired
	private TemuAgentProductModelMapper productModelMapper;
	@Autowired
	private TemuAgentProductFlowStatisticsMapper productFlowStatisticsMapper;
	@Autowired
	private TemuAgentProductFlowHistoryMapper productFlowHistoryMapper;
	@Autowired
	private TemuAgentProductBuyAndSellStatisticsMapper productBuyAndSellStatisticsMapper;
	@Autowired
	private TemuAgentProductBuyAndSellHistoryMapper productBuyAndSellHistoryMapper;

	@Override
	public ModelAndView home() {
		ModelAndView v = new ModelAndView("toolJSP/temuAgent/temuAgent");
		v.addObject("productFlowTypeList", TemuAgentProductFlowType.values());
		v.addObject("productModelUnitTypeList", TemuAgentProductModelUnitType.values());
		return v;
	}

	@Override
	public CommonResult createProduct(TemuAgentCeateProductDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(dto.getProductName())) {
			r.setMessage("Product name error");
			return r;
		}
		LocalDateTime releaseDate = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getReleaseDateStr());
		if (releaseDate == null) {
			r.setMessage("Release date error");
			return r;
		}
		TemuAgentProductExample example = new TemuAgentProductExample();
		example.createCriteria().andNameCnEqualTo(dto.getProductName());
		List<TemuAgentProduct> list = productMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			r.setMessage("Duplicate name");
			return r;
		}

		TemuAgentProduct po = new TemuAgentProduct();
		po.setId(snowFlake.getNextId());
		po.setReleaseTime(releaseDate);
		po.setNameCn(dto.getProductName());
		productMapper.insertSelective(po);
		r.setIsSuccess();
		r.setMessage(String.valueOf(po.getId()));
		return r;
	}

	@Override
	public ModelAndView searchProductList(TemuAgentSearchProductDTO dto) {
		ModelAndView v = new ModelAndView("toolJSP/temuAgent/temuProductList");
		List<TemuAgentProduct> list = queryProductList(dto);
		v.addObject("productList", list);
		return v;
	}

	public ModelAndView searchProductModelList(TemuAgentSearchProductDTO dto) {
		ModelAndView v = new ModelAndView("toolJSP/temuAgent/temuProductModelList");
		List<TemuAgentProduct> list = queryProductList(dto);
		if (list == null || list.size() < 1) {
			return v;
		}
		Map<Long, TemuAgentProduct> productMap = new HashMap<>();
		List<Long> productIdList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			TemuAgentProduct po = list.get(i);
			productMap.put(po.getId(), po);
			productIdList.add(po.getId());
		}

		TemuAgentProductModelExample productModelExample = new TemuAgentProductModelExample();
		productModelExample.createCriteria().andProductIdIn(productIdList);
		List<TemuAgentProductModel> productModelList = productModelMapper.selectByExample(productModelExample);
		v.addObject("productModelList", productModelList);
		return v;
	}

	@Override
	public ModelAndView searchProductModelDetail(TemuAgentProductModelDetailSearchDTO dto) {
		ModelAndView v = new ModelAndView("toolJSP/temuAgent/temuProductModelDetailList");
		TemuAgentProductModelExample productModelExample = new TemuAgentProductModelExample();
		demo.tool.temuAgent.pojo.po.TemuAgentProductModelExample.Criteria criteria = productModelExample
				.createCriteria();
		if (dto.getProductId() != null) {
			criteria.andProductIdEqualTo(dto.getProductId());
		}
		if (dto.getProductModelId() != null) {
			criteria.andIdEqualTo(dto.getProductModelId());
		}
		List<TemuAgentProductModel> productModelList = productModelMapper.selectByExample(productModelExample);
		if (productModelList == null || productModelList.size() < 1) {
			return v;
		}
		Map<Long, TemuAgentProductModel> productModelMap = new HashMap<>();
		List<Long> productModelIdList = new ArrayList<>();
		for (int i = 0; i < productModelList.size(); i++) {
			TemuAgentProductModel po = productModelList.get(i);
			productModelMap.put(po.getId(), po);
			productModelIdList.add(po.getId());
		}

		List<TemuAgentProduct> productList = queryProductList(dto);
		Map<Long, TemuAgentProduct> productMap = new HashMap<>();
		for (int i = 0; i < productList.size(); i++) {
			TemuAgentProduct po = productList.get(i);
			productMap.put(po.getId(), po);
		}

		List<TemuAgentProductModelStatisticsVO> productModelStatisticsVoList = queryProductFlowStatisticsVoList(
				productModelIdList, productMap, productModelMap, dto);
		productModelStatisticsVoList = queryProductBuyAndSellStatisticsVoList(productModelIdList,
				productModelStatisticsVoList);
		for (int i = 0; i < productModelStatisticsVoList.size(); i++) {
			TemuAgentProductModelStatisticsVO vo = productModelStatisticsVoList.get(i);
			TemuAgentProductModel model = productModelMap.get(vo.getModelId());
			if (model == null) {
				continue;
			}
		}

		TemuAgentProductModelStatisticsVO voForSummary = new TemuAgentProductModelStatisticsVO();
		voForSummary.setProductName("Total");
		voForSummary.setTotalStockingCost(BigDecimal.ZERO);
		voForSummary.setTotalPackingFeeCost(BigDecimal.ZERO);
		voForSummary.setTotalSelledAmount(BigDecimal.ZERO);
		voForSummary.setTotalCost(BigDecimal.ZERO);
		for (int i = 0; i < productModelStatisticsVoList.size(); i++) {
			TemuAgentProductModelStatisticsVO tmpVO = productModelStatisticsVoList.get(i);
			if (tmpVO.getTotalPackingFeeCost() != null) {
				voForSummary.setTotalPackingFeeCost(
						voForSummary.getTotalPackingFeeCost().add(tmpVO.getTotalPackingFeeCost()));
			}
			if (tmpVO.getTotalCost() != null) {
				voForSummary.setTotalCost(voForSummary.getTotalCost().add(tmpVO.getTotalCost()));
			}
			if (tmpVO.getTotalSelledAmount() != null) {
				voForSummary
						.setTotalSelledAmount(voForSummary.getTotalSelledAmount().add(tmpVO.getTotalSelledAmount()));
			}
		}

		productModelStatisticsVoList.add(voForSummary);
		v.addObject("productModelStatisticsList", productModelStatisticsVoList);
		return v;
	}

	private List<TemuAgentProduct> queryProductList(TemuAgentSearchProductDTO dto) {
		TemuAgentProductExample example = new TemuAgentProductExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(dto.getProductName())) {
			criteria.andNameCnLike("%" + dto.getProductName() + "%");
		}
		if (dto.getProductId() != null) {
			criteria.andIdEqualTo(dto.getProductId());
		}
		return productMapper.selectByExample(example);
	}

	private List<TemuAgentProductModelStatisticsVO> queryProductFlowStatisticsVoList(List<Long> modelIdList,
			Map<Long, TemuAgentProduct> productMap, Map<Long, TemuAgentProductModel> productModelMap,
			TemuAgentProductModelDetailSearchDTO dto) {

		TemuAgentProductFlowStatisticsExample example = new TemuAgentProductFlowStatisticsExample();
		demo.tool.temuAgent.pojo.po.TemuAgentProductFlowStatisticsExample.Criteria criteria = example.createCriteria();
		if (modelIdList != null && modelIdList.size() > 0) {
			criteria.andModelIdIn(modelIdList);
		}
		if (dto.getStockingGreaterThanZero()) {
			criteria.andStockingCountingGreaterThan(0);
		}
		if (dto.getInternationalStockingGreaterThanZero()) {
			criteria.andInternationalStockingCountingGreaterThan(0);
		}
		List<TemuAgentProductFlowStatistics> flowStatisticsPoList = productFlowStatisticsMapper
				.selectByExample(example);

		Map<Long, TemuAgentProductFlowStatistics> flowStatisticsPoMap = new HashMap<>();
		for (int i = 0; i < flowStatisticsPoList.size(); i++) {
			TemuAgentProductFlowStatistics dataPO = flowStatisticsPoList.get(i);
			flowStatisticsPoMap.put(dataPO.getModelId(), dataPO);
		}
		// TemuAgentProductModelDetailSearchDTO getStockingGreaterThanZero ||
		// getInternationalStockingGreaterThanZero 需要将无存货数据条目剔出
		for (int i = 0; i < modelIdList.size(); i++) {
			Long id = modelIdList.get(i);
			if (!flowStatisticsPoMap.keySet().contains(id)) {
				modelIdList.remove(i);
				i--;
			}
		}

		List<TemuAgentProductModelStatisticsVO> voList = new ArrayList<>();
		for (int i = 0; i < modelIdList.size(); i++) {
			TemuAgentProductModelStatisticsVO vo = new TemuAgentProductModelStatisticsVO();
			voList.add(vo);
			Long modelId = modelIdList.get(i);
			TemuAgentProductModel productModel = productModelMap.get(modelId);
			if (productModel == null) {
				continue;
			}
			BeanUtils.copyProperties(productModel, vo);
			vo.setModelId(productModel.getId());
			TemuAgentProductModelUnitType unitType = TemuAgentProductModelUnitType.getType(vo.getUnitTypeCode());
			if (unitType == null) {
				vo.setUnitTypeName("Unknow unit type");
			} else {
				vo.setUnitTypeName(unitType.getName());
			}
			vo.setCreateTimeStr(localDateTimeHandler.dateToStr(vo.getCreateTime()));

			TemuAgentProduct product = productMap.get(productModel.getProductId());
			if (product == null) {
				continue;
			}
			vo.setProductName(product.getNameCn());
			vo.setProductId(product.getId());

			TemuAgentProductFlowStatistics flowStatisticsPO = flowStatisticsPoMap.get(modelId);
			if (flowStatisticsPO == null) {
				vo.setStockingCounting(0);
				vo.setInternationalStockingCounting(0);
				vo.setSelledCounting(0);
				continue;
			}
			BeanUtils.copyProperties(flowStatisticsPO, vo);

			Integer packagedCounting = vo.getStockingCounting() + vo.getInternationalStockingCounting()
					+ vo.getSelledCounting() + vo.getRepackageCounting();
			BigDecimal totalPackageFee = productModel.getPackingFee().multiply(new BigDecimal(packagedCounting));
			vo.setTotalPackingFeeCost(totalPackageFee);
		}
		return voList;
	}

	private List<TemuAgentProductModelStatisticsVO> queryProductBuyAndSellStatisticsVoList(List<Long> modelIdList,
			List<TemuAgentProductModelStatisticsVO> voList) {
		List<TemuAgentProductBuyAndSellStatistics> buyAndSellStatisticsList = queryProductSellStatistics(modelIdList);
		Map<Long, TemuAgentProductBuyAndSellStatistics> buyAndSellStatisticsMap = new HashMap<>();
		for (int i = 0; i < buyAndSellStatisticsList.size(); i++) {
			buyAndSellStatisticsMap.put(buyAndSellStatisticsList.get(i).getModelId(), buyAndSellStatisticsList.get(i));
		}

		for (int i = 0; i < voList.size(); i++) {
			TemuAgentProductModelStatisticsVO vo = voList.get(i);
			TemuAgentProductBuyAndSellStatistics statistics = buyAndSellStatisticsMap.get(vo.getModelId());
			if (statistics == null) {
				vo.setAvgBuyPrice(BigDecimal.ZERO);
				vo.setHighestBuyPrice(BigDecimal.ZERO);
				vo.setLowestBuyPrice(BigDecimal.ZERO);
				vo.setLowestBuyPrice(BigDecimal.ZERO);
				vo.setLastBuyPrice(BigDecimal.ZERO);
				vo.setAvgSellPrice(BigDecimal.ZERO);
				vo.setHighestSellPrice(BigDecimal.ZERO);
				vo.setLowestSellPrice(BigDecimal.ZERO);
				vo.setLowestSellPrice(BigDecimal.ZERO);
				vo.setTotalSelledAmount(BigDecimal.ZERO);
				continue;
			}
			vo.setAvgBuyPrice(statistics.getAvgBuyPrice());
			vo.setHighestBuyPrice(statistics.getHighestBuyPrice());
			vo.setLowestBuyPrice(statistics.getLowestBuyPrice());
			vo.setLastBuyPrice(statistics.getLastBuyPrice());
			vo.setBoughtCounting(statistics.getBuyCounting());
			BigDecimal buyCounting = new BigDecimal(statistics.getBuyCounting());
			BigDecimal totalPackageFee = buyCounting.multiply(vo.getPackingFee());
			vo.setTotalCost(buyCounting.multiply(statistics.getAvgBuyPrice()).add(totalPackageFee));
			vo.setAvgSellPrice(statistics.getAvgSellPrice());
			vo.setHighestSellPrice(statistics.getHighestSellPrice());
			vo.setLowestSellPrice(statistics.getLowestSellPrice());
			vo.setLastSellPrice(statistics.getLastSellPrice());
			vo.setSelledCounting(statistics.getSellCounting());
			vo.setTotalSelledAmount(
					statistics.getAvgSellPrice().multiply(new BigDecimal(statistics.getSellCounting())));
		}

		return voList;
	}

	private List<TemuAgentProductBuyAndSellStatistics> queryProductSellStatistics(List<Long> modelIdList) {
		TemuAgentProductBuyAndSellStatisticsExample example = new TemuAgentProductBuyAndSellStatisticsExample();
		if (modelIdList != null && modelIdList.size() > 0) {
			example.createCriteria().andModelIdIn(modelIdList);
		}
		return productBuyAndSellStatisticsMapper.selectByExample(example);
	}

	@Override
	public CommonResult createProductModel(TemuAgentCreateOrUpdateProductModelDTO dto) {
		CommonResult r = new CommonResult();
		TemuAgentProductModel po = new TemuAgentProductModel();
		po.setId(snowFlake.getNextId());
		try {
			po.setProductId(dto.getProductId());
			po.setUnitTypeCode(dto.getUnitTypeCode());
			po.setUnitCounting(dto.getUnitCounting());
			po.setSpu(dto.getSpu());
			po.setSku(dto.getSku());
			po.setSkc(dto.getSkc());
			po.setDeclearedPrice(dto.getDeclearedPrice());
			po.setPackingFee(dto.getPackingFee());
			productModelMapper.insertSelective(po);

			TemuAgentProductFlowStatistics statistics = new TemuAgentProductFlowStatistics();
			statistics.setModelId(po.getId());
			productFlowStatisticsMapper.insertSelective(statistics);
			r.setIsSuccess();
			r.setMessage(String.valueOf(po.getId()));
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
		}
		return r;
	}

	@Override
	public CommonResult updateProductModel(TemuAgentCreateOrUpdateProductModelDTO dto) {
		CommonResult r = new CommonResult();
		TemuAgentProductModel po = productModelMapper.selectByPrimaryKey(dto.getProductModelId());
		try {
			po.setProductId(dto.getProductId());
			po.setUnitTypeCode(dto.getUnitTypeCode());
			po.setUnitCounting(dto.getUnitCounting());
			po.setSpu(dto.getSpu());
			po.setSku(dto.getSku());
			po.setSkc(dto.getSkc());
			po.setDeclearedPrice(dto.getDeclearedPrice());
			po.setPackingFee(dto.getPackingFee());
			productModelMapper.updateByPrimaryKey(po);
			r.setIsSuccess();
			r.setMessage(String.valueOf(po.getId()));
		} catch (Exception e) {
			r.setMessage(e.getLocalizedMessage());
		}
		return r;
	}

	@Override
	public CommonResult productModelAddStocking(TemuAgentProductModelAddFlowDTO dto) {
		CommonResult r = productModelFlowDtoVarify(dto);
		if (r.isFail()) {
			return r;
		}
		if (dto.getPrice() == null || dto.getPrice().doubleValue() < 0) {
			r.setMessage("Price error");
			return r;
		}
		TemuAgentProductFlowStatistics flowStatistics = productFlowStatisticsMapper
				.selectByPrimaryKey(dto.getModelId());
		if (flowStatistics == null) {
			r.setMessage("Can NOT find statistics data");
			return r;
		}

		insertProductModelFlowHistory(dto);
		// TODO
		// add storehouse data ?

		LocalDateTime now = LocalDateTime.now();
		flowStatistics.setStockingCounting(flowStatistics.getStockingCounting() + dto.getCounting());
		flowStatistics.setStockingUpdateTime(now);
		flowStatistics.setUpdateTime(now);
		productFlowStatisticsMapper.updateByPrimaryKeySelective(flowStatistics);

		productModelAddBuyOrSelledData(dto);

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult productModelAddInternationalStocking(TemuAgentProductModelAddFlowDTO dto) {
		CommonResult r = productModelFlowDtoVarify(dto);
		if (r.isFail()) {
			return r;
		}
		insertProductModelFlowHistory(dto);

		TemuAgentProductFlowStatistics flowStatistics = productFlowStatisticsMapper
				.selectByPrimaryKey(dto.getModelId());
		if (flowStatistics == null) {
			r.setMessage("Can NOT find statistics data");
			return r;
		}
		LocalDateTime now = LocalDateTime.now();
		flowStatistics.setStockingCounting(flowStatistics.getStockingCounting() - dto.getCounting());
		flowStatistics.setStockingUpdateTime(now);
		flowStatistics.setInternationalStockingCounting(
				flowStatistics.getInternationalStockingCounting() + dto.getCounting());
		flowStatistics.setInternationalStockingUpdateTime(now);
		flowStatistics.setUpdateTime(now);
		productFlowStatisticsMapper.updateByPrimaryKeySelective(flowStatistics);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult productModelAddSelled(TemuAgentProductModelAddFlowDTO dto) {
		CommonResult r = productModelFlowDtoVarify(dto);
		if (r.isFail()) {
			return r;
		}
		if (dto.getPrice() == null || dto.getPrice().doubleValue() < 0) {
			r.setMessage("Price error");
			return r;
		}

		TemuAgentProductFlowStatistics flowStatistics = productFlowStatisticsMapper
				.selectByPrimaryKey(dto.getModelId());
		if (flowStatistics == null) {
			r.setMessage("Can NOT find statistics data");
			return r;
		}

		insertProductModelFlowHistory(dto);

		LocalDateTime now = LocalDateTime.now();
		flowStatistics.setInternationalStockingCounting(
				flowStatistics.getInternationalStockingCounting() - dto.getCounting());
		flowStatistics.setInternationalStockingUpdateTime(now);
		flowStatistics.setSelledCounting(flowStatistics.getSelledCounting() + dto.getCounting());
		flowStatistics.setSelledUpdateTime(now);
		flowStatistics.setUpdateTime(now);
		productFlowStatisticsMapper.updateByPrimaryKeySelective(flowStatistics);

		r = productModelAddBuyOrSelledData(dto);
		if (r.isFail()) {
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	private CommonResult productModelAddBuyOrSelledData(TemuAgentProductModelAddFlowDTO dto) {
		CommonResult r = new CommonResult();
		TemuAgentProductBuyAndSellStatistics po = productBuyAndSellStatisticsMapper
				.selectByPrimaryKey(dto.getModelId());
		boolean newDataFlag = po == null;
		TemuAgentProductFlowType flowType = TemuAgentProductFlowType.getType(dto.getFlowTypeCode());
		boolean buyFlag = TemuAgentProductFlowType.STOCKING.equals(flowType)
				|| TemuAgentProductFlowType.INTERNATIONAL_STOCKING.equals(flowType);
		LocalDateTime now = LocalDateTime.now();
		if (newDataFlag) {
			po = new TemuAgentProductBuyAndSellStatistics();
			po.setModelId(dto.getModelId());
			if (buyFlag) {
				po.setBuyCounting(dto.getCounting());
				po.setAvgBuyPrice(dto.getPrice());
				po.setHighestBuyPrice(dto.getPrice());
				po.setLowestBuyPrice(dto.getPrice());
				po.setLastBuyPrice(dto.getPrice());
				po.setSellCounting(0);
				po.setAvgSellPrice(BigDecimal.ZERO);
				po.setHighestSellPrice(BigDecimal.ZERO);
				po.setLowestSellPrice(BigDecimal.ZERO);
				po.setLastSellPrice(BigDecimal.ZERO);
			} else {
				po.setSellCounting(Math.abs(dto.getCounting()));
				po.setAvgSellPrice(dto.getPrice());
				po.setHighestSellPrice(dto.getPrice());
				po.setLowestSellPrice(dto.getPrice());
				po.setLastSellPrice(dto.getPrice());
				po.setBuyCounting(0);
				po.setAvgBuyPrice(BigDecimal.ZERO);
				po.setHighestBuyPrice(BigDecimal.ZERO);
				po.setLowestBuyPrice(BigDecimal.ZERO);
				po.setLastBuyPrice(BigDecimal.ZERO);
			}
			po.setUpdateTime(now);
			po.setCreateTime(now);
			productBuyAndSellStatisticsMapper.insertSelective(po);
		} else {
			Integer counting = Math.abs(dto.getCounting());
			if (buyFlag) {
				BigDecimal oldBoughtAmount = po.getAvgBuyPrice().multiply(new BigDecimal(po.getBuyCounting()));
				BigDecimal newBoughtAmount = dto.getPrice().multiply(new BigDecimal(counting));
				BigDecimal newBoughtAvgPrice = oldBoughtAmount.add(newBoughtAmount)
						.divide(new BigDecimal(po.getBuyCounting() + counting), 2, RoundingMode.HALF_UP);
				po.setAvgBuyPrice(newBoughtAvgPrice);
				po.setBuyCounting(po.getBuyCounting() + counting);
				po.setUpdateTime(now);
				po.setLastBuyPrice(dto.getPrice());
				if (dto.getPrice().compareTo(po.getHighestBuyPrice()) > 0) {
					po.setHighestBuyPrice(dto.getPrice());
				}
				if (dto.getPrice().compareTo(po.getLowestBuyPrice()) < 0) {
					po.setLowestBuyPrice(dto.getPrice());
				}
			} else {
				BigDecimal oldSelledAmount = po.getAvgSellPrice().multiply(new BigDecimal(po.getSellCounting()));
				BigDecimal newSelledAmount = dto.getPrice().multiply(new BigDecimal(counting));
				BigDecimal newSelledAvgPrice = oldSelledAmount.add(newSelledAmount)
						.divide(new BigDecimal(po.getSellCounting() + counting), 2, RoundingMode.HALF_UP);
				po.setAvgSellPrice(newSelledAvgPrice);
				po.setSellCounting(po.getSellCounting() + counting);
				po.setUpdateTime(now);
				po.setLastSellPrice(dto.getPrice());
				if (dto.getPrice().compareTo(po.getHighestSellPrice()) > 0) {
					po.setHighestSellPrice(dto.getPrice());
				}
				if (dto.getPrice().compareTo(po.getLowestSellPrice()) < 0) {
					po.setLowestSellPrice(dto.getPrice());
				}
			}

			productBuyAndSellStatisticsMapper.updateByPrimaryKey(po);
		}

		TemuAgentProductBuyAndSellHistory history = new TemuAgentProductBuyAndSellHistory();
		history.setId(snowFlake.getNextId());
		history.setModelId(dto.getModelId());
		history.setCounting(dto.getCounting());
		history.setPrice(dto.getPrice());
		history.setIsReturn(false);
		productBuyAndSellHistoryMapper.insertSelective(history);

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult productModelAddRepackage(TemuAgentProductModelAddFlowDTO dto) {
		CommonResult r = productModelFlowDtoVarify(dto);
		if (r.isFail()) {
			return r;
		}
		TemuAgentProductFlowStatistics flowStatistics = productFlowStatisticsMapper
				.selectByPrimaryKey(dto.getModelId());
		if (flowStatistics == null) {
			r.setMessage("Can NOT find statistics data");
			return r;
		}

		insertProductModelFlowHistory(dto);

		LocalDateTime now = LocalDateTime.now();
		flowStatistics.setStockingCounting(flowStatistics.getStockingCounting() - dto.getCounting());
		flowStatistics.setStockingUpdateTime(now);
		flowStatistics.setRepackageCounting(flowStatistics.getRepackageCounting() + dto.getCounting());
		flowStatistics.setRepackageUdpateTime(now);
		flowStatistics.setUpdateTime(now);
		productFlowStatisticsMapper.updateByPrimaryKeySelective(flowStatistics);
		r.setIsSuccess();
		return r;
	}

	private CommonResult productModelFlowDtoVarify(TemuAgentProductModelAddFlowDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getModelId() == null) {
			r.setMessage("Model ID error");
			return r;
		}
		if (dto.getFlowTypeCode() == null) {
			r.setMessage("Flow type code error");
			return r;
		}
		TemuAgentProductFlowType flowType = TemuAgentProductFlowType.getType(dto.getFlowTypeCode());
		if (flowType == null) {
			r.setMessage("Flow type code error");
			return r;
		}
		if (dto.getCounting() == null || dto.getCounting() < 1) {
			r.setMessage("Counting error");
			return r;
		}
		r.setIsSuccess();
		return r;
	}

	private void insertProductModelFlowHistory(TemuAgentProductModelAddFlowDTO dto) {
		TemuAgentProductFlowHistory flowHistory = new TemuAgentProductFlowHistory();
		flowHistory.setId(snowFlake.getNextId());
		flowHistory.setModelId(dto.getModelId());
		flowHistory.setFlowTypeCode(dto.getFlowTypeCode());
		flowHistory.setFlowCounting(dto.getCounting());
		productFlowHistoryMapper.insertSelective(flowHistory);
	}
}
