package demo.joy.image.npc.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.joy.common.pojo.result.JoyCommonResult;
import demo.joy.image.npc.mapper.JoyImageNpcMapper;
import demo.joy.image.npc.pojo.constant.ImageNpcConstant;
import demo.joy.image.npc.pojo.dto.BatchUploadNpcImageDTO;
import demo.joy.image.npc.pojo.po.JoyImageNpc;
import demo.joy.image.npc.service.JoyImageNpcManagerService;

@Service
public class JoyImageNpcManagerServiceImpl extends JoyImageNpcCommonService implements JoyImageNpcManagerService {

	@Autowired
	private JoyImageNpcMapper imageNpcMapper;

	@Override
	public JoyCommonResult batchUploadImageNpc(BatchUploadNpcImageDTO dto) {
		JoyCommonResult r = new JoyCommonResult();

		if (StringUtils.isAnyBlank(dto.getContent(), dto.getRemark())) {
			r.addMessage("null param");
			return r;
		}
		
		if(dto.getRemark().length() > ImageNpcConstant.MAX_REMARK_LENGTH) {
			r.addMessage("remark too long");
			return r;
		}

		Element doc = Jsoup.parse(dto.getContent());
		Elements imgs = doc.select("img[src]");
		String imgSavingPath = null;
		
		String saveingFolderPath = null;
		if (isLinux()) {
			saveingFolderPath = ImageNpcConstant.NPC_LOCAL_STORE;
		} else if (isWindows()) {
			saveingFolderPath = "d:" + ImageNpcConstant.NPC_LOCAL_STORE;
		}
		
		for (Element s : imgs) {
			imgSavingPath = imgBase64Saving(saveingFolderPath, s.attr("src"), dto.getRemark());
			if(imgSavingPath != null) {
				JoyImageNpc po = new JoyImageNpc();
				po.setId(snowFlake.getNextId());
				po.setImgPath(imgSavingPath);
				po.setRemark(dto.getRemark());

				if (imageNpcMapper.insertSelective(po) > 0) {
					loadToRedis(po);
				}
			}
		}

		return r;
	}

}
