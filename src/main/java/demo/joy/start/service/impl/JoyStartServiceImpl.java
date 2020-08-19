package demo.joy.start.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.service.JoyCommonService;
import demo.joy.start.pojo.constant.JoyStartView;
import demo.joy.start.service.JoyStartService;

@Service
public class JoyStartServiceImpl extends JoyCommonService implements JoyStartService {

	@Override
	public ModelAndView getStartView() {
		ModelAndView view = new ModelAndView(JoyStartView.START);
		
		return view;
	}
}
