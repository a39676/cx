package demo.toyParts.woqu.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.GenderType;
import demo.baseCommon.service.CommonService;
import demo.toyParts.woqu.mapper.PtusersOriginalMapper;
import demo.toyParts.woqu.pojo.po.PtusersOriginal;
import demo.toyParts.woqu.pojo.po.PtusersOriginalExample;
import demo.toyParts.woqu.pojo.po.PtusersOriginalExample.Criteria;
import demo.toyParts.woqu.service.PtusersOriginalService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class PtusersOriginalServiceImpl extends CommonService implements PtusersOriginalService {

	@Autowired
	private PtusersOriginalMapper ptuserOriginalMapper;
	@Autowired
	private FileUtilCustom ioUtil;

	@Override
	public void createPtusers() {
		String mainFolderPath = "D:\\auxiliary\\tmp\\铂涛通讯录";
		File mainFolder = new File(mainFolderPath);
		File[] fileList = mainFolder.listFiles();
		String fileStr = null;
		for(File subFile : fileList) {
			fileStr = ioUtil.getStringFromFile(subFile.getAbsolutePath());
			List<PtusersOriginal> ptuserList = createPtusersFromFile(fileStr);
			ptuserList.stream().forEach(o -> ptuserOriginalMapper.insertSelective(o));
		}
	}
	
	public void updateMobile() {
		PtusersOriginalExample example = new PtusersOriginalExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdGreaterThan(0L);
		List<PtusersOriginal> ptUserList = ptuserOriginalMapper.selectByExample(example);
		ptUserList.stream().forEach(o -> {
			if(StringUtils.isNotBlank(o.getMobile()) && o.getMobile().matches("\\d{3}-\\d{4}-\\d{4}")) {
				o.setMobile(o.getMobile().replaceAll("-", ""));
				System.out.println(o.getMobile());
				ptuserOriginalMapper.updateByPrimaryKey(o);
			}
		});
	}
	
	@Override
	public void createVcf() {
		List<PtusersOriginal> ptUserList = ptuserOriginalMapper.selectAllGroupByMobile();
		String outputVcfPath = "D:\\auxiliary\\tmp\\铂涛通讯录\\ptVcf.vcf";
		ptUserList.stream().forEach(o -> {
			if(StringUtils.isNotBlank(o.getMobile())) {
				String singleResult = ""
						+ "BEGIN:VCARD\r\n" 
						+ "VERSION:2.1\r\n"
						+ "FN:" + o.getCnName() + "-" + o.getJobtitle() + "\r\n"
						+ "TEL;CELL:" + o.getMobile() + "\r\n"
						+ "END:VCARD" + "\r\n"
						;
				ioUtil.byteToFileAppendAtEnd(singleResult.getBytes(StandardCharsets.UTF_8), outputVcfPath);
			}
		});
	}
	
	private List<PtusersOriginal> createPtusersFromFile(String fileStr) {
		JSONObject json = xmlStrToJson(fileStr);
		JSONArray rows = getTargetRows(json);

		JSONObject row = null;
		JSONArray elements = null;
		PtusersOriginal tmpPO = null;
		List<PtusersOriginal> userList = new ArrayList<PtusersOriginal>();
		for(int i = 0; i < rows.size(); i++) {
			row = rows.getJSONObject(i);
			elements = row.getJSONArray("col");
			tmpPO = buildPtuser(elements);
			userList.add(tmpPO);
			System.out.println(tmpPO);
		}
		
		return userList;
	}

	private JSONObject xmlStrToJson(String xmlStr) {
		org.json.JSONObject orgJson = XML.toJSONObject(xmlStr);
		return JSONObject.fromObject(orgJson.toString());
	}

	private JSONArray getTargetRows(JSONObject json) {
		JSONObject table = json.getJSONObject("table");
		JSONArray rows = table.getJSONArray("row");
		return rows;
	}

	private PtusersOriginal buildPtuser(JSONArray elements) {
		PtusersOriginal po = new PtusersOriginal();
		JSONObject elementJ = null;
		for (int i = 0; i < elements.size(); i++) {
			elementJ = elements.getJSONObject(i);
			if(elementJ.containsKey("column")) {
				if ("lastname".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setCnName(elementJ.getString("content"));
				} else if ("subcompanyid1".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setSubcompany(elementJ.getString("content"));
				} else if ("departmentid".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setDepartment(elementJ.getString("content"));
				} else if ("mobile".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setMobile(elementJ.getString("content"));
				} else if ("telephone".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setTelephone(elementJ.getString("content"));
				} else if ("managerid".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setManagerName(elementJ.getString("content"));
				} else if ("jobtitle".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setJobtitle(elementJ.getString("content"));
				} else if ("accounttype".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					po.setAccounttype(elementJ.getString("content"));
				} else if ("sex".equals(elementJ.getString("column")) && elementJ.containsKey("content")) {
					String gender = elementJ.getString("content");
					if(GenderType.male.getName().equals(gender)) {
						po.setGender(GenderType.male.getCode());
					} else if (GenderType.female.getName().equals(gender)) {
						po.setGender(GenderType.female.getCode());
					} else {
						po.setGender(GenderType.unknow.getCode());
					}
				}
			}
		}
		return po;
	}
}
