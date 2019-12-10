package demo.toyParts.woqu.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.pojo.type.GenderType;
import demo.baseCommon.service.CommonService;
import demo.toyParts.woqu.mapper.WusersMapper;
import demo.toyParts.woqu.pojo.po.Wusers;
import demo.toyParts.woqu.service.WoquService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class WoquServiceImpl extends CommonService implements WoquService {

	@Autowired
	private WusersMapper wusersMapper;
	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public void initInsert(String filePath) {
		File f = new File(filePath);
		if(!f.exists()) {
			return;
		}

		String fileStr = ioUtil.getStringFromFile(filePath);
		List<String> lines = Arrays.asList(fileStr.split("\\n"));

		String tmpLine = null;
		String[] tmpStrAry = null;
		Wusers tmpu = null;

		for (int i = 0; i < lines.size(); i++) {
			tmpLine = lines.get(i);
			if (tmpLine.startsWith("#")) {
				continue;
			}
			tmpStrAry = tmpLine.split("\t");
			tmpu = buildWuserByStrLine(tmpStrAry);
			System.out.println(tmpStrAry[0]);
			wusersMapper.insertSelectiveIgnore(tmpu);
		}
		
	}

	private Wusers buildWuserByStrLine(String[] line) {
		Wusers u = new Wusers();
		Integer tmpLevel = 0;
		if (StringUtils.isNotBlank(line[1])) {
			u.setNickName(line[1]);
		}
		if (StringUtils.isNotBlank(line[2]) && !line[2].equals(line[3])) {
			u.setCnName(line[2]);
		}
		if (numberUtil.matchMobile(line[3])) {
			u.setMobile(Long.parseLong(line[3]));
		}
		if(numberUtil.matchInteger(line[4].replaceAll("V", ""))) {
			tmpLevel = Integer.parseInt(line[4].replaceAll("V", ""));
		}
		u.setLevel(tmpLevel);
		if (numberUtil.matchInteger(line[5])) {
			u.setScore(Integer.parseInt(line[5]));
		}

		if (StringUtils.isNotBlank(line[6])) {
			if (line[6].equals(GenderType.female.getName())) {
				u.setGender(GenderType.female.getCode());
			} else if (line[6].equals(GenderType.male.getName())) {
				u.setGender(GenderType.male.getCode());
			} else {
				u.setGender(GenderType.unknow.getCode());
			}
		}

		if (StringUtils.isNotBlank(line[8])) {
			u.setJob(line[8]);
		}
		if (StringUtils.isNotBlank(line[9]) && dateHandler.isDateValid(line[9])) {
			u.setBirth(dateHandler.stringToDateUnkonwFormat(line[9]));
		}
		if (StringUtils.isNotBlank(line[10])) {
			u.setBelongStore(line[10]);
		}
		if (StringUtils.isNotBlank(line[11])) {
			u.setBelongStaff(line[11]);
		}
		if (StringUtils.isNotBlank(line[12])) {
			u.setSource(line[12]);
		}
		if (StringUtils.isNotBlank(line[14])) {
			u.setIntroduceMobile(Long.parseLong(line[14]));
		}
		if (StringUtils.isNotBlank(line[15]) && dateHandler.isDateValid(line[15])) {
			u.setCreateDate(dateHandler.stringToDateUnkonwFormat(line[15]));
		}
		return u;
	}

}
