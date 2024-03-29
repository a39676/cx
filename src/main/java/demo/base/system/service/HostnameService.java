package demo.base.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.result.HostnameType;

public interface HostnameService {

	List<Hostname> findHostnames();

	HostnameType findHostnameType(HttpServletRequest request);

	String findMainHostname();

	String findHostNameFromRequst(HttpServletRequest request);

	boolean isMainHostname(HttpServletRequest request);

	Hostname findHostname(HostnameType type);

}
