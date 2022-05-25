package demo.base.system.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import demo.base.system.service.GlobalOptionService;
import demo.common.service.CommonService;

@Service
public class GlobalOptionServiceImpl extends CommonService implements GlobalOptionService {

	@Override
	public boolean checkFolderExists(String path) {
		File f = new File(path);
		if(!f.exists() || !f.isDirectory()) {
			return f.mkdirs();
		} else {
			return true;
		}
	}

}
