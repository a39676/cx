package demo.base.user.service;

public interface UserRoleService {

	void insertBaseUserAuth(Long userId);

	void insertActiveUserAuth(Long userId);

	void insertSuperAdminAuth(Long userId);

}
