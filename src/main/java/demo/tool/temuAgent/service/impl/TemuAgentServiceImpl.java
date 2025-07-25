package demo.tool.temuAgent.service.impl;

import java.math.BigDecimal;
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
import demo.tool.temuAgent.mapper.TemuAgentProductFlowHistoryMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductFlowStatisticsMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductModelMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductSellHistoryMapper;
import demo.tool.temuAgent.mapper.TemuAgentProductSellStatisticsMapper;
import demo.tool.temuAgent.pojo.dto.TemuAgentCeateProductDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentProductModelDetailSearchDTO;
import demo.tool.temuAgent.pojo.dto.TemuAgentProductSearchDTO;
import demo.tool.temuAgent.pojo.po.TemuAgentProduct;
import demo.tool.temuAgent.pojo.po.TemuAgentProductExample;
import demo.tool.temuAgent.pojo.po.TemuAgentProductExample.Criteria;
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
	private TemuAgentProductSellStatisticsMapper productSellStatisticsMapper;
	@Autowired
	private TemuAgentProductSellHistoryMapper productSellHistoryMapper;

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
		if (dto.getUnitPrice() == null) {
			r.setMessage("Unit price error");
			return r;
		}
		LocalDateTime releaseDate = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getReleaseDataStr());
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
		po.setUnitPrice(dto.getUnitPrice());
		productMapper.insertSelective(po);
		r.setIsSuccess();
		r.setMessage(String.valueOf(po.getId()));
		return r;
	}

	@Override
	public ModelAndView searchProductList(TemuAgentProductSearchDTO dto) {
		ModelAndView v = new ModelAndView("toolJSP/temuAgent/temuProductList");
		List<TemuAgentProduct> list = queryProductList(dto);
		v.addObject("productList", list);
		return v;
	}

	public ModelAndView searchProductModelList(TemuAgentProductSearchDTO dto) {
		ModelAndView v = new ModelAndView(); // TODO
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

	public ModelAndView searchProductModelDetail(TemuAgentProductModelDetailSearchDTO dto) {
		ModelAndView v = new ModelAndView(); // TODO
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
		v.addObject("productMap", productMap);

		List<TemuAgentProductModelStatisticsVO> productFlowStatisticsVoList = queryProductFlowStatisticsVoList(
				productModelIdList, productMap, productModelMap);
		v.addObject("productFlowList", productFlowStatisticsVoList);

//		TODO
		return v;
	}

	private List<TemuAgentProduct> queryProductList(TemuAgentProductSearchDTO dto) {
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
			Map<Long, TemuAgentProduct> productMap, Map<Long, TemuAgentProductModel> productModelMap) {
		List<TemuAgentProductFlowStatistics> flowStatisticsPoList = queryProductFlowStatistics(modelIdList);
		List<TemuAgentProductModelStatisticsVO> voList = new ArrayList<>();
		for (int i = 0; i < flowStatisticsPoList.size(); i++) {
			TemuAgentProductFlowStatistics flowStatisticsPO = flowStatisticsPoList.get(i);
			TemuAgentProductModelStatisticsVO vo = new TemuAgentProductModelStatisticsVO();
			voList.add(vo);
			BeanUtils.copyProperties(flowStatisticsPO, vo);
			TemuAgentProductModel productModel = productModelMap.get(flowStatisticsPO.getModelId());
			if (productModel == null) {
				continue;
			}
			TemuAgentProduct product = productMap.get(productModel.getProductId());
			if (product == null) {
				continue;
			}
			vo.setProductName(product.getNameCn());
			Integer buiedCounting = vo.getStockingCounting() + vo.getInternationalStockingCounting()
					+ vo.getSelledCounting();
			vo.setTotalStockingCost(product.getUnitPrice().multiply(new BigDecimal(buiedCounting)));
		}
		return voList;
	}

	private List<TemuAgentProductFlowStatistics> queryProductFlowStatistics(List<Long> modelIdList) {
		TemuAgentProductFlowStatisticsExample example = new TemuAgentProductFlowStatisticsExample();
		if (modelIdList != null && modelIdList.size() > 0) {
			example.createCriteria().andModelIdIn(modelIdList);
		}
		return productFlowStatisticsMapper.selectByExample(example);
	}
}
