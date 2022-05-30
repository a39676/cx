package demo.joy.garden.pojo.result;

import org.springframework.web.servlet.ModelAndView;

import demo.joy.common.pojo.result.JoyCommonResult;

public class JoyGardenCreateNewFieldLandResult extends JoyCommonResult {

	private ModelAndView view;

	public ModelAndView getView() {
		return view;
	}

	public void setView(ModelAndView view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return "JoyGardenCreateNewFieldLandResult [view=" + view + "]";
	}

}
