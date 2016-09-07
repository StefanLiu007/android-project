package com.eden.service.impl;

import java.util.List;

import com.eden.dao.UserDao;
import com.eden.dao.impl.UserDaoImplJDBC;
import com.eden.domain.PageBean;
import com.eden.domain.User;
import com.eden.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao dao;
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
		dao = new UserDaoImplJDBC();
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return dao.getAllUsers();
	}

	@Override
	public User getUserByUSER_ACCOUNT(String userAccount) {
		// TODO Auto-generated method stub
		return dao.getUserByUSER_ACCOUNT(userAccount);
	}

	@Override
	public PageBean<User> getUserByPage(int currentPage, int pageSize, String searchContent) throws Exception{
		// TODO Auto-generated method stub
		return dao.getUserByPage(currentPage, pageSize, searchContent);
	}

	@Override
	public boolean deleteUserByUSER_ACCOUNT(String userAccount) {
		// TODO Auto-generated method stub
		return dao.deleteUserByUSER_ACCOUNT(userAccount);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return dao.updateUser(user);
	}

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		return dao.addUser(user);
	}

	@Override
	public User loginSuccess(String account,String password) {
		
		return dao.loginSuccess(account,password);
	}

}
