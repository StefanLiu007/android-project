package com.eden.service;

import java.util.List;

import com.eden.domain.Content;
import com.eden.domain.PageBean;
import com.eden.domain.User;


public interface UserService {
	public List<User> getAllUsers();
	public User getUserByUSER_ACCOUNT(String userAccount);
	public PageBean<User> getUserByPage(int currentPage,int pageSize,String searchContent) throws Exception;
	public boolean deleteUserByUSER_ACCOUNT(String userAccount);
	public boolean updateUser(User user) ;
	public boolean addUser(User user);
	public User loginSuccess(String string, String string2);
	
	

}
