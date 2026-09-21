package demo.geographical.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.geographical.mapper.InternationalDialingCodeMapper;
import demo.geographical.pojo.po.InternationalDialingCode;
import demo.geographical.pojo.po.InternationalDialingCodeExample;

@Scope("singleton")
@Service
public class InternationalDialingCodeOptionService extends CommonService {

	@Autowired
	private InternationalDialingCodeMapper internationalDialingCodeMapper;

	private List<InternationalDialingCode> codeList;

	public List<InternationalDialingCode> getCodeList(boolean refresh) {
		if (refresh || codeList == null || codeList.size() < 1) {
			InternationalDialingCodeExample example = new InternationalDialingCodeExample();
			example.createCriteria().andIdGreaterThan(0);
			codeList = internationalDialingCodeMapper.selectByExample(example);
		}
		return codeList;
	}

	public InternationalDialingCode getByName(String name) {
		codeList = getCodeList(false);
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (int i = 0; i < codeList.size(); i++) {
			if (codeList.get(i).getAreaName().equals(name)) {
				return codeList.get(i);
			}
		}
		return null;
	}

	public InternationalDialingCode getByCode(Integer code) {
		codeList = getCodeList(false);
		if (code == null || code < 0) {
			return null;
		}
		for (int i = 0; i < codeList.size(); i++) {
			if (codeList.get(i).getCode().equals(code)) {
				return codeList.get(i);
			}
		}
		return null;
	}
}
