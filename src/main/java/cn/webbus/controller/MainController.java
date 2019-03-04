
package cn.webbus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.webbus.model.User;
import cn.webbus.service.UserService;

@Controller
@RequestMapping(value = "/main")
public class MainController {
	@Autowired
	private UserService service;

	@RequestMapping(value = "/index")
	public String index() {
		User user = new User();
		user.setPassword("hello");
		user.setUsername("hello");
		service.insertUser(user);
		return "index";
	}

}
