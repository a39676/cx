package demo.tool.taobao.service.impl;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.geographical.mapper.InternationalDialingCodeMapper;
import demo.geographical.pojo.po.GeographicalArea;
import demo.geographical.pojo.po.InternationalDialingCode;
import demo.geographical.pojo.po.InternationalDialingCodeExample;
import demo.geographical.service.GeographicalService;
import demo.tool.taobao.mapper.TaobaoOfferFromDownstreamBuyerMapper;
import demo.tool.taobao.mapper.TaobaoOfferToSupplierMapper;
import demo.tool.taobao.mapper.TaobaoUpstreamSupplierMapper;
import demo.tool.taobao.pojo.bo.TaobaoAddOfferFromDownstreamBuyerBO;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferFromDownstreamBuyerDTO;
import demo.tool.taobao.pojo.dto.TaobaoAddOfferToSupplierDTO;
import demo.tool.taobao.pojo.po.TaobaoOfferFromDownstreamBuyer;
import demo.tool.taobao.pojo.po.TaobaoOfferToSupplier;
import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplier;
import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplierExample;
import demo.tool.taobao.pojo.result.TaobaoAddOfferFromDownstreamBuyerResult;
import demo.tool.taobao.service.TaobaoOfferFromDownstreamBuyerRecordService;
import net.sf.json.JSONObject;

@Service
public class TaobaoOfferFromDownstreamBuyerRecordServiceImpl extends CommonService
		implements TaobaoOfferFromDownstreamBuyerRecordService {

	@Autowired
	private TaobaoOfferFromDownstreamBuyerMapper offerFromDownstreamBuyerMapper;
	@Autowired
	private TaobaoUpstreamSupplierMapper supplierMapper;
	@Autowired
	private GeographicalService geographicalService;
	@Autowired
	private InternationalDialingCodeMapper internationalDialingCodeMapper;
	@Autowired
	private TaobaoOfferToSupplierMapper offerToSupplierMapper;

	@Override
	public ModelAndView offerRecordView() {
		ModelAndView view = new ModelAndView("toolJSP/taobao/taobaoOfferRecord");
		view.addObject("title", "TaobaoOfferRecord");
		TaobaoUpstreamSupplierExample supplierExample = new TaobaoUpstreamSupplierExample();
		supplierExample.createCriteria().andIsDeleteEqualTo(false);
		List<TaobaoUpstreamSupplier> supplierList = supplierMapper.selectByExample(supplierExample);
		view.addObject("supplierList", supplierList);
		InternationalDialingCodeExample internationalDialingCodeExample = new InternationalDialingCodeExample();
		internationalDialingCodeExample.createCriteria().andIdGreaterThan(0);
		List<InternationalDialingCode> internationalDialingCodeList = internationalDialingCodeMapper
				.selectByExample(internationalDialingCodeExample);
		view.addObject("internationalDialingCodeList", internationalDialingCodeList);
		return view;
	}

	@Override
	public TaobaoAddOfferFromDownstreamBuyerResult addNewOfferFromDownstreamBuyer(
			TaobaoAddOfferFromDownstreamBuyerDTO dto) {
		TaobaoAddOfferFromDownstreamBuyerResult r = new TaobaoAddOfferFromDownstreamBuyerResult();
		JSONObject json = downstreamBuyerOfferStrToJson(dto.getOfferRawText());
		if (json == null || json.keySet().size() < 1) {
			r.setMessage("Offer raw text format error");
			return r;
		}

		TaobaoAddOfferFromDownstreamBuyerBO bo = new TaobaoAddOfferFromDownstreamBuyerBO();
		try {
			bo.setAddressInfo(json.getString("收货地址"));
			bo.setAfterTransitRegionId(dto.getInternationalNum());
			bo.setAmount(new BigDecimal(json.getString("订单总付款")));
			bo.setBuyerWangWangName(json.getString("买家旺旺"));
			bo.setIdOutsource(json.getLong("订单编号"));
			bo.setPackageReceiverName(json.getString("收货姓名"));
			bo.setPhone(json.getString("收货电话"));
			if (json.containsKey("买家备注")) {
				bo.setRemark(json.getString("买家备注"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setMessage("Raw text error");
			return r;
		}

		if (bo.getIdOutsource() == null) {
			r.setMessage("Need order id");
			return r;
		}
		if (bo.getAmount() == null || bo.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			r.setMessage("Order amount error");
			return r;
		}
		if (StringUtils.isBlank(bo.getAddressInfo())) {
			r.setMessage("Order info error");
			return r;
		}
		String addressInfo = bo.getAddressInfo();
//		if (hasAtLeastTwoCommas(info)) {
//			r.setMessage("Order info format error");
//			return r;
//		}
		int spaceIndex = addressInfo.indexOf(" ");
		String provinceStr = addressInfo.substring(0, spaceIndex);
		addressInfo = addressInfo.substring(spaceIndex + 1);
		GeographicalArea province = geographicalService.findGeographical(provinceStr);

		spaceIndex = addressInfo.indexOf(" ");
		String cityStr = addressInfo.substring(0, spaceIndex);
		addressInfo = addressInfo.substring(spaceIndex + 1);
		GeographicalArea city = geographicalService.findGeographical(cityStr);

		TaobaoOfferFromDownstreamBuyer po = new TaobaoOfferFromDownstreamBuyer();
		po.setId(snowFlake.getNextId());
		po.setIdOutsource(bo.getIdOutsource());
		po.setAmount(bo.getAmount());
		po.setAddress(bo.getAddressInfo());
		po.setPhone(bo.getPhone());
		po.setNickname(bo.getBuyerWangWangName());
		po.setPackageReceiverName(bo.getPackageReceiverName());
		po.setRegionId1(province.getId().intValue());
		po.setRegionId2(city.getId().intValue());
		po.setAfterTransitRegionId(bo.getAfterTransitRegionId());
		po.setRemark(bo.getRemark());
		offerFromDownstreamBuyerMapper.insertSelective(po);

		r.setBuyerOrderID(bo.getIdOutsource());
		r.setReceiverFullInfo(bo.getPackageReceiverName() + ", " + bo.getPhone() + ", " + bo.getAddressInfo());
		r.setIsSuccess();
		return r;
	}

	@SuppressWarnings("unused")
	private boolean hasAtLeastTwoCommas(String str) {
		int firstIndex = str.indexOf("，");
		// 如果连第一个都没找到，或者第一个已经是最后一个字符（后面没有空间放第二个），则返回false
		if (firstIndex == -1)
			return false;

		// 从第一个逗号的下一个位置开始查找第二个逗号
		int secondIndex = str.indexOf("，", firstIndex + 1);

		return secondIndex != -1;
	}

	private JSONObject downstreamBuyerOfferStrToJson(String rawText) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode root = mapper.createObjectNode();
			List<ObjectNode> itemList = new ArrayList<>();
			ObjectNode currentItem = null;

			BufferedReader reader = new BufferedReader(new StringReader(rawText));
			String line;

			// 1. 逐行解析文本
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue;

				String[] parts = line.split("[:：]", 2);
				if (parts.length < 2)
					continue;

				String key = parts[0].trim();
				String value = parts[1].trim();

				switch (key) {
				case "商品总数量" -> root.put(key, Integer.parseInt(value));
				case "订单总付款" -> root.put(key, Double.parseDouble(value));
				case "订单编号", "付款时间", "买家旺旺", "收货姓名", "收货电话", "收货地址", "买家备注" -> root.put(key, value);

				case "商品标题" -> {
					currentItem = mapper.createObjectNode();
					currentItem.put(key, value);
					itemList.add(currentItem);
				}
				case "商品数量" -> {
					if (currentItem != null)
						currentItem.put(key, Integer.parseInt(value));
				}
				case "商品ID", "商品规格sku" -> {
					if (currentItem != null)
						currentItem.put(key, value);
				}
				}
			}

			// 2. 优先按商品ID排序，其次按商品规格sku排序（使用 path 防止字段缺失时报错）
			itemList.sort(Comparator.comparing((ObjectNode item) -> item.path("商品ID").asText())
					.thenComparing(item -> item.path("商品规格sku").asText()));

			// 3. 将排序后的 List 转为 ArrayNode 添加回根节点
			ArrayNode sortedItems = mapper.createArrayNode();
			sortedItems.addAll(itemList);
			root.set("商品列表", sortedItems);

			// 4. 格式化输出 JSON
			String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			JSONObject json = JSONObject.fromObject(jsonStr);
			return json;

		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}

	@Override
	public CommonResult addNewOfferToSupplier(TaobaoAddOfferToSupplierDTO dto) {
		CommonResult r = new CommonResult();

		TaobaoOfferToSupplier po = new TaobaoOfferToSupplier();
		po.setId(snowFlake.getNextId());
		po.setDownstreamBuyerOfferId(dto.getDownstreamBuyerOrderId());
		po.setSupplierOfferId(dto.getSupplierOrderId());
		po.setAmount(dto.getAmount());
		po.setRemark(dto.getRemark());

		TaobaoUpstreamSupplier merchant = supplierMapper.selectByPrimaryKey(dto.getMerchantID());
		if (merchant != null) {
			po.setMerchantId(merchant.getId());
		}
		offerToSupplierMapper.insertSelective(po);

		r.setIsSuccess();
		return r;
	}
}
