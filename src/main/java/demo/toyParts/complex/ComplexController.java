package demo.toyParts.complex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ComplexController {

	@GetMapping(value = "/1jlbdmb")
	@ResponseBody
	public String for_1jlbdmb() {
		return "pong";
	}

}
