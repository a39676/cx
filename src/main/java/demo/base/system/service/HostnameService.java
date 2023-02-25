package demo.base.system.service;

import java.util.List;

import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.result.HostnameType;
import jakarta.servlet.http.HttpServletRequest;

public interface HostnameService {

	List<Hostname> findHostnames();

	HostnameType findHostnameType(HttpServletRequest request);

	String findMainHostname();

	String findHostNameFromRequst(HttpServletRequest request);

	boolean isMainHostname(HttpServletRequest request);

}
