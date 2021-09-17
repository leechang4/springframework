package com.mycompany.webapp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


/*
SimpleUrlAuthenticationSuccessHandler
   로그인 성공후에 무조건 홈으로 이동
SavedRequestAwareAuthenticationSuccessHandler
   로그인 성공후에 사용자가 접근하고자 했던 페이지로 이동
*/

// 로그인을 하면 바로 디폴트값으로 간다
// public class Ch17AuthenticationSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler{ //내가 성공하면 실행
public class Ch17AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	private static final Logger logger = LoggerFactory.getLogger(Ch17AuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("실행");
		super.onAuthenticationSuccess(request, response, authentication);
	}	
	
}
