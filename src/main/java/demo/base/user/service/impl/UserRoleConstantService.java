package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.RolesMapper;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.RolesExample;
import demo.base.user.pojo.type.SystemRolesType;
import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class UserRoleConstantService extends CommonService {

	@Autowired
	private RolesMapper roleMapper;

	private List<Roles> baseUserRoles = new ArrayList<>();
	private List<String> baseUserRoleStrList = new ArrayList<>();

	private List<Roles> activeUserRoles = new ArrayList<>();
	private List<String> activeUserRoleStrList = new ArrayList<>();

	private List<Roles> studentUserRoles = new ArrayList<>();
	private List<String> stucentUserRoleStrList = new ArrayList<>();

	private List<Roles> posterRoles = new ArrayList<>();
	private List<String> posterRoleStrList = new ArrayList<>();

	private List<Roles> adminRoles = new ArrayList<>();
	private List<String> adminRoleStrList = new ArrayList<>();

	private List<Roles> superAdminRoles = new ArrayList<>();
	private List<String> superAdminRoleStrList = new ArrayList<>();

	public void refresh() {
		baseUserRoleStrList.add(SystemRolesType.ROLE_USER.getName());
		RolesExample example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleIn(baseUserRoleStrList);
		baseUserRoles.addAll(roleMapper.selectByExample(example));

		activeUserRoleStrList.addAll(baseUserRoleStrList);
		activeUserRoleStrList.add(SystemRolesType.ROLE_USER_ACTIVE.getName());
		example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleIn(activeUserRoleStrList);
		activeUserRoles.addAll(roleMapper.selectByExample(example));

		stucentUserRoleStrList.addAll(baseUserRoleStrList);
		stucentUserRoleStrList.add(SystemRolesType.ROLE_STUDENT.getName());
		example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleIn(stucentUserRoleStrList);
		studentUserRoles.addAll(roleMapper.selectByExample(example));

		posterRoleStrList.addAll(activeUserRoleStrList);
		posterRoleStrList.add(SystemRolesType.ROLE_POSTER.getName());
		example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleIn(posterRoleStrList);
		posterRoles.addAll(roleMapper.selectByExample(example));

		adminRoleStrList.addAll(activeUserRoleStrList);
		adminRoleStrList.add(SystemRolesType.ROLE_ADMIN.getName());
		example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleIn(adminRoleStrList);
		adminRoles.addAll(roleMapper.selectByExample(example));

		superAdminRoleStrList.addAll(adminRoleStrList);
		superAdminRoleStrList.add(SystemRolesType.ROLE_SUPER_ADMIN.getName());
		example = new RolesExample();
		example.createCriteria().andIsDeleteEqualTo(false).andRoleIn(superAdminRoleStrList);
		superAdminRoles.addAll(roleMapper.selectByExample(example));
	}

	public List<Roles> getBaseUserRoles() {
		return baseUserRoles;
	}

	public void setBaseUserRoles(List<Roles> baseUserRoles) {
		this.baseUserRoles = baseUserRoles;
	}

	public List<String> getBaseUserRoleStrList() {
		return baseUserRoleStrList;
	}

	public void setBaseUserRoleStrList(List<String> baseUserRoleStrList) {
		this.baseUserRoleStrList = baseUserRoleStrList;
	}

	public List<Roles> getActiveUserRoles() {
		return activeUserRoles;
	}

	public void setActiveUserRoles(List<Roles> activeUserRoles) {
		this.activeUserRoles = activeUserRoles;
	}

	public List<String> getActiveUserRoleStrList() {
		return activeUserRoleStrList;
	}

	public void setActiveUserRoleStrList(List<String> activeUserRoleStrList) {
		this.activeUserRoleStrList = activeUserRoleStrList;
	}

	public List<Roles> getStudentUserRoles() {
		return studentUserRoles;
	}

	public void setStudentUserRoles(List<Roles> studentUserRoles) {
		this.studentUserRoles = studentUserRoles;
	}

	public List<String> getStucentUserRoleStrList() {
		return stucentUserRoleStrList;
	}

	public void setStucentUserRoleStrList(List<String> stucentUserRoleStrList) {
		this.stucentUserRoleStrList = stucentUserRoleStrList;
	}

	public List<Roles> getPosterRoles() {
		return posterRoles;
	}

	public void setPosterRoles(List<Roles> posterRoles) {
		this.posterRoles = posterRoles;
	}

	public List<String> getPosterRoleStrList() {
		return posterRoleStrList;
	}

	public void setPosterRoleStrList(List<String> posterRoleStrList) {
		this.posterRoleStrList = posterRoleStrList;
	}

	public List<Roles> getAdminRoles() {
		return adminRoles;
	}

	public void setAdminRoles(List<Roles> adminRoles) {
		this.adminRoles = adminRoles;
	}

	public List<String> getAdminRoleStrList() {
		return adminRoleStrList;
	}

	public void setAdminRoleStrList(List<String> adminRoleStrList) {
		this.adminRoleStrList = adminRoleStrList;
	}

	public List<Roles> getSuperAdminRoles() {
		return superAdminRoles;
	}

	public void setSuperAdminRoles(List<Roles> superAdminRoles) {
		this.superAdminRoles = superAdminRoles;
	}

	public List<String> getSuperAdminRoleStrList() {
		return superAdminRoleStrList;
	}

	public void setSuperAdminRoleStrList(List<String> superAdminRoleStrList) {
		this.superAdminRoleStrList = superAdminRoleStrList;
	}

}
