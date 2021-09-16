package com.mycompany.webapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mycompany.webapp.controller.Ch10Controller;

// 객체로 생성해서 관리하도록 설정
@Component
// 모든 컨트롤러에 영향을 미치는 설정
@ControllerAdvice
public class Ch10ExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(Ch10ExceptionHandler.class);
	
	public Ch10ExceptionHandler() {
		// 해당 로그가 찍히는 것은 @Component 애너테이션이 정상적으로 실행되어 애플리케이션 구동시 객체가 생성된 것
		logger.info("실행");
	}
	// 예외 처리자 설정
	@ExceptionHandler
	public String handleNullPointerException(NullPointerException e) {
		logger.info("실행");
		e.printStackTrace();
		return "error/500_null";
	}
	
	@ExceptionHandler
	public String handleClassCastException(ClassCastException e) {
		logger.info("실행");
		e.printStackTrace();
		return "error/500_cast";
	}
	
	@ExceptionHandler
	public String handleClassCastException(Ch10SoldOutException e) {
		logger.info("실행");
		e.printStackTrace();
		return "error/soldout";
	}
	
	@ExceptionHandler
	public String handleCh16NotFoundAccountException(Ch16NotFoundAccountException e, Model model) {
		logger.info("실행");
		e.printStackTrace();
		model.addAttribute("error", e.getMessage());
		return "error/notFoundAccountException";
	}
	
	@ExceptionHandler
	public String handleOtherException(Exception e) {
		logger.info("실행");
		e.printStackTrace();
		return "error/500";
	}
}