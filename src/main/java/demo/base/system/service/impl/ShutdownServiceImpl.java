package demo.base.system.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import demo.base.system.service.ShutdownService;
import demo.common.service.CommonService;

@Service
public class ShutdownServiceImpl extends CommonService implements ShutdownService, ApplicationContextAware{

	private ApplicationContext context;
	
	@Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }
	
	@Override
	public String shutdownContext() {
		
		if(!baseUtilCustom.hasSuperAdminRole()) {
			return "";
		}
		
		((ConfigurableApplicationContext) context).close();
		// if context close success, will NOT return anything 
		return "close error";
    }
}
