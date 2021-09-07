package com.mycompany.webapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch11City;
import com.mycompany.webapp.dto.Ch11Member;
import com.mycompany.webapp.dto.Ch11Skill;

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
	public String form1(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		member.setMnation("한국");
		return "ch11/form1";
	}
	
	@PostMapping("/form1")
	public String handleForm1(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		logger.info("mid: " + member.getMid());
		logger.info("mname: " + member.getMname());
		logger.info("mpassword: " + member.getMpassword());
		logger.info("mnation: " + member.getMnation());
		return "redirect:/ch11/content";
	}
	
	@GetMapping("/form2")
	public String form2(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("실행");
		
		//드롭다운리스트의 항목을 추가할 목적
		List<String> typeList = new ArrayList<>();
		typeList.add("일반회원");
		typeList.add("기업회원");
		typeList.add("헤드헌터회원");
		model.addAttribute("typeList", typeList);	
		
		//기본 선택 항목을 설정
		member.setMtype("기업회원");
		
		//드롭다운리스트의 항목을 추가할 목적
		List<String> jobList = new ArrayList<>();
		jobList.add("학생");
		jobList.add("개발자");
		jobList.add("디자이너");
		model.addAttribute("jobList", jobList);
		
		//기본 선택 항목을 설정
		member.setMjob("개발자");
		
		//드롭다운리스트의 항목을 추가할 목적
		List<Ch11City> cityList = new ArrayList<>();
		cityList.add(new Ch11City(1, "서울"));
		cityList.add(new Ch11City(2, "부산"));
		cityList.add(new Ch11City(3, "제주"));
		model.addAttribute("cityList", cityList);
		
		//기본 선택 항목을 설정
		member.setMcity(3);
		
		return "ch11/form2";
	}
	
	@PostMapping("/form2")
	public String handleForm2(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		logger.info("mtype: " + member.getMtype());
		logger.info("mjob: " + member.getMjob());
		logger.info("mcity: " + member.getMcity());
		return "redirect:/ch11/content";
	}
	
	@GetMapping("/form3")
	public String form3(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("실행");
		
		List<String> languageList = new ArrayList<>();
		languageList.add("C");
		languageList.add("Python");
		languageList.add("Java");
		languageList.add("JavaScript");
		model.addAttribute("languageList", languageList);
		
		member.setMlanguage(new String[] {"Python", "JavaScript"});
		
		List<Ch11Skill> skillList = new ArrayList<>();
		skillList.add(new Ch11Skill(1, "SpringFramework"));
		skillList.add(new Ch11Skill(2, "SpringBoot"));
		skillList.add(new Ch11Skill(3, "Vue"));
		model.addAttribute("skillList", skillList);
		
		member.setMskill(new int[] {1, 3});
		
		return "ch11/form3";
	}
	
	@PostMapping("/form3")
	public String handleForm3(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		
		if(member.getMlanguage() != null) {
			for(String lang : member.getMlanguage()) {
				logger.info("lang: " + lang);
			}
		}
		
		if(member.getMskill() != null) {
			System.out.println("mskill: " + Arrays.toString(member.getMskill()));
		}
		
		return "redirect:/ch11/content";
	}
	
	@GetMapping("/form4")
	public String form4(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("실행");
		
		//드롭다운리스트의 항목을 추가할 목적
		List<String> jobList = new ArrayList<>();
		jobList.add("학생");
		jobList.add("개발자");
		jobList.add("디자이너");
		model.addAttribute("jobList", jobList);
		
		//기본 선택 항목을 설정
		member.setMjob("개발자");
		
		//드롭다운리스트의 항목을 추가할 목적
		List<Ch11City> cityList = new ArrayList<>();
		cityList.add(new Ch11City(1, "서울"));
		cityList.add(new Ch11City(2, "부산"));
		cityList.add(new Ch11City(3, "제주"));
		model.addAttribute("cityList", cityList);
		
		//기본 선택 항목을 설정
		member.setMcity(3);
		
		return "ch11/form4";
	}
	
	@PostMapping("/form4")
	public String handleForm4(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		
		logger.info("mjob: " + member.getMjob());
		logger.info("mcity: " + member.getMcity());
		
		return "redirect:/ch11/content";
	}
	
	@GetMapping("/form5")
	public String form5(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		return "ch11/form5";
	}
}







