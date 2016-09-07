package com.eden.dao;

import java.util.List;

import com.eden.domain.PageBean;
import com.eden.domain.User;


public interface UserDao {
	public List<User> getAllUsers();
	public User getUserByUSER_ACCOUNT(String userAccount);
	public PageBean<User> getUserByPage(int currentPage,int pageSize,String searchContent) throws Exception;
	public boolean deleteUserByUSER_ACCOUNT(String userAccount);
	public boolean updateUser(User user) ;
	public boolean addUser(User user);
	public User loginSuccess(String account, String password);
	public boolean UpdateImage(String account, String url);


}
