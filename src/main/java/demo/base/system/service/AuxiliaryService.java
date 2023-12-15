package demo.base.system.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuxiliaryService {

	void robotHandle(HttpServletRequest request, HttpServletResponse response);

}
