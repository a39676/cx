package demo.finance.cryptoCoin.data.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;

@Service
public class CryptoCoinCatalogServiceImpl extends CryptoCoinCommonService implements CryptoCoinCatalogService {

	@Autowired
	private CryptoCoinCatalogMapper mapper;
	@Autowired
	private CryptoCoinLowPriceNoticeService lowPriceNoticeService;

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
	public List<CryptoCoinCatalog> findCatalog(List<String> coinNameList) {
		if (coinNameList == null || coinNameList.isEmpty()) {
			return new ArrayList<>();
		}

		Set<String> paramNameSet = new HashSet<>();
		for (String coinName : coinNameList) {
			if (StringUtils.isNotBlank(coinName)) {
				paramNameSet.add(coinName.toUpperCase());
			}
		}

		if (paramNameSet.isEmpty()) {
			return new ArrayList<>();
		}

		coinNameList.clear();
		coinNameList.addAll(paramNameSet);

		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andCoinNameEnShortIn(coinNameList).andIsDeleteEqualTo(false);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);
		return poList;
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
			voList.add(cryptoCoinCatalogPOToVO(po));
		}

		return voList;
	}

	@Override
	public List<CryptoCoinCatalogVO> getSubscriptionCatalog() {
		List<CryptoCoinCatalogVO> voList = new ArrayList<>();
		Set<CryptoCoinCatalogVO> voSet = new HashSet<>();

		voSet.addAll(getNormalSubscriptionCatalog());
		voSet.addAll(lowPriceNoticeService.getLowPriceSubscriptionCatalogVOList());

		voList.addAll(voSet);

		return voList;
	}

	private List<CryptoCoinCatalogVO> getNormalSubscriptionCatalog() {
		List<CryptoCoinCatalogVO> voList = new ArrayList<>();
		Long size = redisTemplate.opsForList().size(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY);
		if (size < 1) {
			return voList;
		}

		List<Object> redisList = redisTemplate.opsForList().range(CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY,
				0, size);
		List<String> catalogList = new ArrayList<>();
		for (Object value : redisList) {
			catalogList.add(String.valueOf(value));
		}

		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCoinNameEnShortIn(catalogList);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);

		for (CryptoCoinCatalog po : poList) {
			voList.add(cryptoCoinCatalogPOToVO(po));
		}

		return voList;
	}

	@Override
	public void addSubscriptionCatalog(String catalog) {
		addSubscriptionCatalog(catalog, CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY);
	}

	@Override
	public void addSubscriptionCatalog(String catalog, String redisKey) {
		if (StringUtils.isAnyBlank(catalog, redisKey)) {
			return;
		}

		catalog = catalog.toUpperCase();
		CryptoCoinCatalogExample example = new CryptoCoinCatalogExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCoinNameEnShortEqualTo(catalog);
		List<CryptoCoinCatalog> poList = mapper.selectByExample(example);

		if (poList == null || poList.isEmpty()) {
			return;
		}

		redisTemplate.opsForList().leftPush(redisKey, catalog);
	}

	@Override
	public void removeSubscriptionCatalog(String catalog) {
		if (StringUtils.isBlank(catalog)) {
			return;
		}

		catalog = catalog.toUpperCase();
		String redisKey = CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY;

		Long size = redisTemplate.opsForList().size(redisKey);
		if (size < 1) {
			return;
		}

		String tmpValue = null;
		for (int i = 0; i < size; i++) {
			tmpValue = String.valueOf(redisTemplate.opsForList().rightPop(redisKey));

			if (tmpValue.equals(catalog)) {
				return;
			} else {
				redisTemplate.opsForList().leftPush(redisKey, tmpValue);
			}
		}

	}

	@Override
	public void removeAllSubscriptionCatalog() {
		String redisKey = CryptoCoinConstant.CRYPTO_COIN_SUBSCRIPTION_LIST_KEY;
		Long size = redisTemplate.opsForList().size(redisKey);
		if (size < 1) {
			return;
		}

		for (int i = 0; i < size; i++) {
			redisTemplate.opsForList().rightPop(redisKey);
		}
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
