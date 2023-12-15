package demo.base.system.controller;


import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import demo.common.controller.CommonController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

@Controller
public class CsrfController extends CommonController {
	
	
	@GetMapping(value = {"/csrf", "/_csrf"})
    public void getCsrfToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        JSONObject json = JSONObject.fromObject(csrf);
        outputJson(response, json);
    }
	
}