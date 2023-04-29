package demo.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public abstract class CommonController {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * FIXME 2020-08-28 should delete
	 * 
	 * @param response
	 * @param json
	 */
	protected void outputJson(HttpServletResponse response, JSONObject json) {
		try {
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
