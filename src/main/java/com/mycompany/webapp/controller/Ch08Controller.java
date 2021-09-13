package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mycompany.webapp.dto.Ch08InputForm;

@Controller
@RequestMapping("/ch08")
@SessionAttributes({"inputForm"})	// Ch08Controller가 처리하는 내용에 대해서만 세션이 유지된다.
public class Ch08Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch08Controller.class);
	@RequestMapping("/content")
	public String content() {
		return "ch08/content";
	}
	
	@GetMapping(value="/saveData", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String saveData(String name, HttpSession session) {
		logger.info("실행");
		logger.info("name: " + name);
		
		session.setAttribute("name", name);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		String json = jsonObject.toString(); // {"result":"success"}
		return json;
	}
	
	// 방법 1
	/*	@GetMapping(value="/readData", produces="application/json; charset=UTF-8")
		@ResponseBody
		public String readData(HttpSession session) {
			logger.info("실행");
			
			String name = (String) session.getAttribute("name");
			logger.info("name: " + name);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", name);
			String json = jsonObject.toString(); // {"name":"홍길동"}
			return json;
		}*/
	
	// 방법 2
	@GetMapping(value="/readData", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String readData(@SessionAttribute String name) {
		logger.info("실행");
		
		logger.info("name: " + name);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", name);
		String json = jsonObject.toString(); // {"name":"홍길동"}
		return json;
	}
	
	@GetMapping("/login")
	public String loginForm() {
		logger.info("실행");
		return "ch08/loginForm";
	}
	
	@PostMapping("/login")
	public String login(String mid, String mpassword, HttpSession session) {
		logger.info("실행");
		if(mid.equals("spring") && mpassword.equals("12345")) {
			session.setAttribute("sessionMid", mid);
		}
		return "redirect:/ch08/content";
	}
	// 방법 1
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("실행");
		session.removeAttribute("sessionMid");
		return "redirect:/ch08/loginForm";
	}
	
	// 방법 2 -> 로그아웃 시 사용자의 모든 정보를 없앨 때 씀
	/*@GetMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("실행");
		session.invalidate(); // 세션 객체 자체가 없어져버림
		return "redirect:/ch08/loginForm";
	}*/
	
	@PostMapping(value="/loginAjax", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String loginAjax(String mid, String mpassword, HttpSession session) {
		logger.info("실행");
		String result = "";
		
		if(!mid.equals("spring")) {
			result = "wrongMid";
		} else if (!mpassword.equals("12345")) {
			result = "wrongMpassword";
		} else {
			result = "success";
			session.setAttribute("sessionMid", mid);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		String json = jsonObject.toString(); // {"name":"홍길동"}
		return json;
	}
	
	/*@GetMapping(value="/logoutAjax", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String logoutAjax(String mid, String mpassword, HttpSession session) {
		logger.info("실행");
	
	//		session.invalidate();
		session.removeAttribute("sessionMid");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		String json = jsonObject.toString(); // {"name":"홍길동"}
		return json;
	}*/
	
	@GetMapping(value="/logoutAjax")
	public void logoutAjax(HttpSession session, HttpServletResponse response) throws IOException {
		logger.info("실행");
	
		session.invalidate(); // 세션을 없애고 만드는 시간보다 밑의 코드를 실행하고 pw를 close하는 시간이 더 빠르기 때문에 에러 발생
//		session.removeAttribute("sessionMid");
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", "success");
		String json = jsonObject.toString(); // {"name":"홍길동"}

		pw.println(json);
//		pw.flush();
//		pw.close();
	}
	
	@ModelAttribute("inputForm")
	// request를 할때마다 실행되는 메서드 -> 클래스 레벨에 붙은 @SessionAttributes로 인해 한 번만 실행되고 해당 객체를 공유함
	// -> 세션에 "inputForm"이라는 이름이 없을 때만 딱 한 번 실행
	public Ch08InputForm getInputForm() { 
		logger.info("실행");
		Ch08InputForm inputForm = new Ch08InputForm();
		return inputForm;
	}
	
	@GetMapping("/inputStep1")
//	public String inputStep1(Ch08InputForm inputForm) { // 이렇게 쓸 경우 요청마다 새로운 inputForm이 만들어져버린다.
	public String inputStep1(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("실행");
		return "ch08/inputStep1";
	}
	
	@PostMapping("/inputStep2")
	public String inputStep2(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("실행");
		logger.info("data1: " + inputForm.getData1());
		logger.info("data2: " + inputForm.getData2());
		logger.info("data3: " + inputForm.getData3());
		logger.info("data4: " + inputForm.getData4());
		return "ch08/inputStep2";
	}
	
	@PostMapping("/inputDone")
	public String inputDone(@ModelAttribute("inputForm") Ch08InputForm inputForm, SessionStatus sessionStatus) {
		logger.info("실행");
		logger.info("data1: " + inputForm.getData1());
		logger.info("data2: " + inputForm.getData2());
		logger.info("data3: " + inputForm.getData3());
		logger.info("data4: " + inputForm.getData4());
		// 처리 내용~
		// 세션에 저장되어있는 inputForm을 제거
		sessionStatus.setComplete();
//		session.removeAttribute("inputForm") -> 이런 방식으로 지우면 안됨
		return "redirect:/ch08/content";
	}
	
	
}