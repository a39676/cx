package demo.finance.account_holder.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseViewConstant;
import demo.base.user.controller.UsersController;
import demo.common.controller.CommonController;
import demo.config.costom_component.BaseUtilCustom;
import demo.finance.account_holder.AccountHolderViewConstants;
import demo.finance.account_holder.pojo.constant.HolderUrlConstant;
import demo.finance.account_holder.pojo.po.AccountHolder;
import demo.finance.account_holder.service.AccountHolderService;

@Controller
@RequestMapping(value = HolderUrlConstant.ROOT)
public class AccountHolderController extends CommonController {
	
	@Autowired
	private UsersController usersController;
	
	@Autowired
	private AccountHolderService accountHolderService;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	/**
	 * holderList 功能准备废弃, 2018-04-11
	 * @param id
	 * @param holderName
	 * @return
	 */
	@PostMapping(value = HolderUrlConstant.HOLDER_LIST, produces = "text/html;charset=UTF-8")
	@ResponseBody public ModelAndView holderList(
		@RequestParam(value = "holderId", defaultValue = "0" ) Long id,
		@RequestParam(value = "holderName") String holderName
		) {
		ModelAndView view = new ModelAndView();
		try{
			List<AccountHolder> holderList = new ArrayList<AccountHolder>();
			
			if (id > 0){
				holderList.add(accountHolderService.getHolder(id));
			} else {
				holderList = accountHolderService.findHolder(holderName);
			}
			
			view.addObject("holderList", holderList);
			view.setViewName(AccountHolderViewConstants.holderList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return view;
	}
	

	public List<AccountHolder> getCurrentHolders() {
		
		String userName = baseUtilCustom.getCurrentUserName();
		
		if(StringUtils.isEmpty(userName)) {
			return null;
		}
		
		return accountHolderService.getCurrentHolders(userName);
	}
	
	public AccountHolder getDefaultHolder() {
		return getCurrentHolders().get(0);
	}
	
	
	@PostMapping(value = HolderUrlConstant.HOLDER_REGISTER)
	public ModelAndView holderRegister(
		HttpServletRequest request, 
		HttpServletResponse response
		) {
		ModelAndView view = new ModelAndView();
		
		if (!baseUtilCustom.isLoginUser()) {
			view.setViewName(BaseViewConstant.view403);
			return view;
		}
		
		Long currentUserId = usersController.getCurrentUserId();
		Long holderId = accountHolderService.holderRegister(currentUserId, request);
		
		if(holderId > 0) {
			AccountHolder holder = accountHolderService.getHolder(holderId);
			view.addObject("holder", holder);
			view.setViewName(AccountHolderViewConstants.holderDetail);
		} else {
			view.setViewName(BaseViewConstant.viewError);
		}

		return view;
	}
	

}
