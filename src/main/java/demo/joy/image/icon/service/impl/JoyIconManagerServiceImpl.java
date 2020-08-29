package demo.joy.image.icon.service.impl;

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
		
		String saveingFolderPath = null;
		if (isLinux()) {
			saveingFolderPath = IconConstant.ICON_LOCAL_STORE;
		} else if (isWindows()) {
			saveingFolderPath = "d:" + IconConstant.ICON_LOCAL_STORE;
		}
		
		for (Element s : imgs) {
			imgSavingPath = imgBase64Saving(saveingFolderPath, s.attr("src"), dto.getRemark());
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

}
