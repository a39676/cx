package demo.joy.image.icon.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.icon.mapper.JoyImageIconMapper;
import demo.joy.image.icon.pojo.constant.IconConstant;
import demo.joy.image.icon.pojo.dto.BatchUploadIconDTO;
import demo.joy.image.icon.pojo.po.JoyImageIcon;
import demo.joy.image.icon.service.JoyIconManagerService;

@Service
public class JoyIconManagerServiceImpl extends JoyIconCommonService implements JoyIconManagerService {

	@Autowired
	private JoyImageIconMapper iconMapper;

	@Override
	public JoyCommonResult batchUploadIcon(BatchUploadIconDTO dto) {
		JoyCommonResult r = new JoyCommonResult();

		if (StringUtils.isAnyBlank(dto.getContent(), dto.getRemark())) {
			r.addMessage("null param");
			return r;
		}
		
		if(dto.getRemark().length() > IconConstant.MAX_REMARK_LENGTH) {
			r.addMessage("remark too long");
			return r;
		}

		Element doc = Jsoup.parse(dto.getContent());
		Elements imgs = doc.select("img[src]");
		String imgSavingPath = null;
		
		for (Element s : imgs) {
			imgSavingPath = imgBase64Saving(optionService.getIconImageStorePathPrefix(), s.attr("src"));
			if(imgSavingPath != null) {
				JoyImageIcon po = new JoyImageIcon();
				po.setId(snowFlake.getNextId());
				po.setImgPath(imgSavingPath);
				po.setRemark(dto.getRemark());

				if (iconMapper.insertSelective(po) > 0) {
					loadToRedis(po);
				}
			}
		}

		return r;
	}

	@Override
	public JoyCommonResult delete(Long id) {
		JoyCommonResult r = new JoyCommonResult();
		if(id == null) {
			return r;
		}
		
		JoyImageIcon po = iconMapper.selectByPrimaryKey(id);
		if(po == null || StringUtils.isBlank(po.getImgPath())) {
			r.failWithMessage("data error");
			return r;
		}
		
		File f = new File(po.getImgPath());
		if(!f.delete()) {
			r.addMessage("file delete error");
			return r;
		}
		int delCount = iconMapper.deleteByPrimaryKey(id);
		
		if(delCount != 1) {
			r.addMessage("file deleted, but database error");
			return r;
		}

		r.setIsSuccess();
		return r;
	}
}
