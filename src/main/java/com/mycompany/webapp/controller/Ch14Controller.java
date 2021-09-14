package com.mycompany.webapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch14Board;
import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.service.Ch14BoardService;
import com.mycompany.webapp.service.Ch14MemberService;
import com.mycompany.webapp.service.Ch14MemberService.JoinResult;
import com.mycompany.webapp.service.Ch14MemberService.LoginResult;

@Controller
@RequestMapping("/ch14")
public class Ch14Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch14Controller.class);
	
	@Resource
	private DataSource dataSource; // 인터페이스를 구현한 객체 자동 주입
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");

		return "ch14/content";
	}
	
	@GetMapping("/testConnectToDB")
	public String testConnectToDB() throws SQLException {
		// 커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		logger.info("연결 성공");
		
		// 커넥션 풀로 연결 객체를 반납하기 (연결을 끊는 것이 아니라 커넥션 풀로 반납)
		conn.close();
		
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testInsert")
	public String testInsert() throws SQLException {
		// 커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			// 작업 처리
			String sql = "INSERT INTO board VALUES(SEQ_BNO.NEXTVAL, ?, ?, SYSDATE, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "오늘은 월요일");
			pstmt.setString(2, "스트레스가 이빠이 올라갔어요.");
			pstmt.setString(3, "user");
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 커넥션 풀로 연결 객체를 반납하기 (연결을 끊는 것이 아니라 커넥션 풀로 반납)
		conn.close();
		
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testSelect")
	public String testSelect() throws SQLException {
		// 커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			// 작업 처리
			String sql = "SELECT bno, btitle, bcontent, bdate, mid FROM board";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int bno = rs.getInt("bno");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Date bdate = rs.getDate("bdate");
				String mid = rs.getString("mid");
				logger.info(bno + "\t" + btitle + "\t" + bcontent + "\t" + bdate + "\t" + mid);
			}
			rs.close();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 커넥션 풀로 연결 객체를 반납하기 (연결을 끊는 것이 아니라 커넥션 풀로 반납)
		conn.close();
		
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testUpdate")
	public String testUpdate() throws SQLException {
		// 커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			// 작업 처리
			String sql = "UPDATE board SET btitle=?, bcontent=? WHERE bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "배고파요");
			pstmt.setString(2, "점심 먹으러 언제 가요?");
			pstmt.setInt(3, 1);
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 커넥션 풀로 연결 객체를 반납하기 (연결을 끊는 것이 아니라 커넥션 풀로 반납)
		conn.close();
		
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testDelete")
	public String testDelete() throws SQLException {
		// 커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			// 작업 처리
			String sql = "DELETE FROM board WHERE bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.executeUpdate();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 커넥션 풀로 연결 객체를 반납하기 (연결을 끊는 것이 아니라 커넥션 풀로 반납)
		conn.close();
		
		return "redirect:/ch14/content";
	}
	@Resource
	private Ch14MemberService memberService;
	
	@GetMapping("/join")
	public String joinForm() {
		return "ch14/joinForm";
	}
	
	@PostMapping("/join")
	public String join(Ch14Member member, Model model) {
		member.setMenabled(1);
		member.setMrole("ROLE_USER");
		JoinResult result = memberService.join(member);
		
		if (result == JoinResult.SUCCESS) {
			return "redirect:/ch14/content";
		} else if (result == JoinResult.DUPLICATED) { 
			model.addAttribute("error", "중복된 아이디가 있습니다.");
			return "ch14/joinForm";
		} else { 
			model.addAttribute("error", "회원 가입이 실패되었습니다. 다시 시도해 주세요.");
			return "ch14/joinForm";
		}
	}
	
	

	@GetMapping("/login")
	public String loginForm(Ch14Member member, Model model) {
		return "ch14/loginForm";
	}

	
	@PostMapping("/login")
	public String login(Ch14Member member, Model model) {
		LoginResult result = memberService.login(member);
		if(result == LoginResult.SUCCESS) {
			return "redirect:/ch14/content";
		} else if(result ==LoginResult.FAIL_MID) {
			model.addAttribute("error", "아아디가 존재하지 않습니다.");
			return "ch14/loginForm";
		}else if(result ==LoginResult.FAIL_MPASSWORD) {
			model.addAttribute("error", "패스워드가 틀립니다.");
			return "ch14/loginForm";
		}else {
			model.addAttribute("error", "알수 없는 이유로 로그인이 되지 않았습니다. 다시 시도해 주세요.");
			return "ch14/loginForm";
		}
	}
	
	@Resource
	private Ch14BoardService boardService;
	
	@GetMapping("/boardList")
	public String boardList(Model model) {
		List<Ch14Board> boards = boardService.getBoards();
		model.addAttribute("boards", boards);
		return"ch14/boardList";
	}
}