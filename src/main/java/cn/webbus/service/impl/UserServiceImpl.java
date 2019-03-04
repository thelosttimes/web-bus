package cn.webbus.service.impl;

import cn.webbus.dao.UserDAO;
import cn.webbus.model.User;
import cn.webbus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional(rollbackFor=  Exception.class)
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		int i = userDAO.insertUser(user);
		int x = 2/0;
		return i;
	}
}
