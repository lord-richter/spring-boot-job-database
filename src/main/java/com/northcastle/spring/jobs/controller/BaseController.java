package com.northcastle.spring.jobs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BaseController {

	public BaseController() {
	}

	@GetMapping
	public String getWelcome(){
		return "index";
	}

}
