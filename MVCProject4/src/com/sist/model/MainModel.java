package com.sist.model;

import javax.servlet.http.HttpServletRequest;

import com.sist.temp.Controller;

@Controller
public class MainModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request) {
	
	//request.setAttribute("main_jsp", "home.jsp");
		
		return "main.jsp"; // 스트링으로 리턴!!!!!!!!
	}

}
