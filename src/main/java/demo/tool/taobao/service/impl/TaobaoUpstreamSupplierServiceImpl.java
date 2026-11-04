package demo.tool.taobao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.taobao.mapper.TaobaoUpstreamSupplierMapper;
import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplier;
import demo.tool.taobao.pojo.po.TaobaoUpstreamSupplierExample;
import demo.tool.taobao.service.TaobaoUpstreamSupplierService;

@Service
public class TaobaoUpstreamSupplierServiceImpl extends CommonService implements TaobaoUpstreamSupplierService {

	@Autowired
	private TaobaoUpstreamSupplierMapper mapper;

	@Override
	public CommonResult supplierHeatReduct() {
		TaobaoUpstreamSupplierExample example = new TaobaoUpstreamSupplierExample();
		example.createCriteria().andConnectIdIsNotNull();
		List<TaobaoUpstreamSupplier> supplierList = mapper.selectByExample(example);
		int minHeat = Integer.MAX_VALUE;
		TaobaoUpstreamSupplier supplier = null;
		for (int i = 0; i < supplierList.size(); i++) {
			supplier = supplierList.get(i);
			if (supplier.getHeat() == null) {
				supplier.setHeat(0);
				mapper.updateByPrimaryKeySelective(supplier);
			} else if (supplier.getHeat() > 0 && minHeat > supplier.getHeat()) {
				minHeat = supplier.getHeat();
			}
		}

		for (int i = 0; i < supplierList.size(); i++) {
			supplier = supplierList.get(i);
			if (supplier.getHeat() == 0) {
				continue;
			} else if (supplier.getHeat() - minHeat > 0) {
				supplier.setHeat(supplier.getHeat() - minHeat);
			} else {
				supplier.setHeat(0);
			}
			mapper.updateByPrimaryKeySelective(supplier);
		}
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}
}
