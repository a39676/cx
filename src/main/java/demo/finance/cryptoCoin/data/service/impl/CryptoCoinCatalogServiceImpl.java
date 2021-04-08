package demo.finance.cryptoCoin.data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinCatalogMapper;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinConstant;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinCatalogVO;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;

@Service
public class CryptoCoinCatalogServiceImpl extends CryptoCoinCommonService implements CryptoCoinCatalogService {

	@Autowired
	private CryptoCoinCatalogMapper mapper;

	@Override
	public CryptoCoinCatalog findCatalog(String coinName) {
		if (StringUtils.isBlank(coinName)) {
			return null;
		}
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andCoinNameEnShortEqualTo(coinName.toUpperCase()).andIsDeleteEqualTo(false);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);
		if (poList.isEmpty()) {
			return null;
		}
		return poList.get(0);
	}

	@Override
	public CryptoCoinCatalog findCatalog(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CryptoCoinCatalog> getAllCatalog() {
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		return mapper.selectByExample(example);
	}

	@Override
	public List<CryptoCoinCatalogVO> getAllCatalogVO() {
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsDeleteEqualTo(false);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);
		List<CryptoCoinCatalogVO> voList = new ArrayList<>();

		for (CryptoCoinCatalog po : poList) {
			voList.add(poToVO(po));
		}

		return voList;
	}

	@Override
	public List<CryptoCoinCatalogVO> getSubscriptionCatalog() {
		List<CryptoCoinCatalogVO> voList = new ArrayList<>();
		String catalogNameListStr = constantService.getValByName(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY);
		if (StringUtils.isBlank(catalogNameListStr)) {
			return voList;
		}

		List<String> catalogList = Arrays.asList(catalogNameListStr.toUpperCase().split(","));

		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCoinNameEnShortIn(catalogList);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);

		for (CryptoCoinCatalog po : poList) {
			voList.add(poToVO(po));
		}

		return voList;
	}

	@Override
	public void addSubscriptionCatalog(String catalog) {
		if (StringUtils.isBlank(catalog)) {
			return;
		}

		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCoinNameEnShortEqualTo(catalog);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);

		if (poList == null || poList.isEmpty()) {
			return;
		}

		String recordsStr = constantService.getValByName(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY);

		if (StringUtils.isBlank(recordsStr)) {
			recordsStr = poList.get(0).getCoinNameEnShort();
		} else {
			if (recordsStr.contains(catalog)) {
				return;
			} else {
				recordsStr = recordsStr + "," + poList.get(0).getCoinNameEnShort();
			}
		}

		constantService.setValByName(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY, recordsStr);
	}

	@Override
	public void removeSubscriptionCatalog(String catalog) {
		if (StringUtils.isBlank(catalog)) {
			return;
		}

		String recordsStr = constantService.getValByName(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY);

		if (StringUtils.isBlank(recordsStr)) {
			return;
		} else {
			catalog = catalog.toUpperCase();
			if(Arrays.asList(recordsStr.split(",")).contains(catalog)) {
				if (recordsStr.contains(catalog + ",")) {
					recordsStr = recordsStr.replaceAll(catalog + ",", "");
				} else if (recordsStr.contains("," + catalog)) {
					recordsStr = recordsStr.replaceAll("," + catalog, "");
				}
				constantService.setValByName(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY, recordsStr);
			};
		}

	}

	@Override
	public void removeAllSubscriptionCatalog() {
		constantService.deleteValByName(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY);
	}

	private CryptoCoinCatalogVO poToVO(CryptoCoinCatalog po) {
		CryptoCoinCatalogVO vo = new CryptoCoinCatalogVO();
		vo.setPk(encryptId(po.getId()));
		vo.setEnShortname(po.getCoinNameEnShort());
		return vo;
	}

	@Override
	public CommonResult addCatalog(String enShortName) {
		CommonResult r = new CommonResult();
		CryptoCoinCatalog po = new CryptoCoinCatalog();
		po.setId(snowFlake.getNextId());
		po.setCoinNameEnShort(enShortName);
		try {
			int count = mapper.insertSelective(po);
			if (count > 0) {
				r.setIsSuccess();
			}
		} catch (Exception e) {
		}
		return r;
	}

	@Override
	public CryptoCoinCatalog findLastCatalog() {
		return mapper.findLastCatalog();
	}
}
