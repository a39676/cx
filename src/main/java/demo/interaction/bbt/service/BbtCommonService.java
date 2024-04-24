package demo.interaction.bbt.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.common.service.CommonService;
import demo.config.customComponent.BbtDynamicKey;

public abstract class BbtCommonService extends CommonService {

	@Autowired
	protected BbtDynamicKey bbtDynamicKey;
	
}
