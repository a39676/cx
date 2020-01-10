package demo.base.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.base.system.pojo.po.Hostname;
import demo.base.system.pojo.result.HostnameType;

public interface HostnameService {

	List<Hostname> findHonstnames();

	HostnameType findHostname(HttpServletRequest request);

	String findEasy();

	String findSeek();

	String find3310();

}
