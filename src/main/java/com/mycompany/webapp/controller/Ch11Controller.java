package com.mycompany.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ch11")
public class Ch11Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch11Controller.class);
	
	@RequestMapping("/content")
	public String content(String data) {
		logger.info("실행");
		return "ch11/content";
	}
	
	@GetMapping("/form1")
	public String form1() {
		logger.info("실행");
		return "ch11/form1";
	}
}
