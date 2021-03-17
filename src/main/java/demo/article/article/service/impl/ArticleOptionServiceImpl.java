package demo.article.article.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.article.article.mapper.ArticleOptionMapper;
import demo.article.article.pojo.po.ArticleOption;
import demo.article.article.pojo.po.ArticleOptionExample;
import demo.article.article.service.ArticleOptionService;
import demo.base.system.pojo.bo.SystemConstant;
import demo.common.service.CommonService;

@Service
public class ArticleOptionServiceImpl extends CommonService implements ArticleOptionService {

	@Autowired
	private ArticleOptionMapper optionMapper;
	
	@Override
	public void loadAllOption() {
		ArticleOptionExample example = new ArticleOptionExample();
		example.createCriteria().andIsdeleteEqualTo(false);
		List<ArticleOption> poList = optionMapper.selectByExample(example);
		List<SystemConstant> boList = new ArrayList<>();
		SystemConstant bo = null;
		for(ArticleOption po : poList) {
			bo = transToConstant(po);
			boList.add(bo);
		}
		constantService.setValsByName(boList);
	}
	
	private SystemConstant transToConstant(ArticleOption option) {
		SystemConstant bo = new SystemConstant();
		bo.setConstantName(option.getConstantname());
		bo.setConstantValue(option.getConstantvalue());
		return bo;
	}
}
