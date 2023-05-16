package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

import jakarta.servlet.http.*;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("checkId/{id}")
	@ResponseBody
	public Map<String, Object> checkId(@PathVariable("id") String id) {
		return service.checkId(id);
	}
	
	@GetMapping("checkNickname/{nickname}")
	@ResponseBody
	public Map<String, Object> checkNickname(@PathVariable("nickname") String nickname,
			Authentication authentication) {
		return service.checkNickname(nickname, authentication);
	}
	
	@GetMapping("checkEmail/{email}")
	@ResponseBody
	public Map<String, Object> checkEamil(@PathVariable("email") String email,
			Authentication authentication) {
		return service.checkEmail(email, authentication);
	}
	
	@GetMapping("signup")
	@PreAuthorize("isAnonymous()")  //메소드 레벨에서 접근 제한
	public void sighupForm() {
		
	}
	
	@GetMapping("login")
	public void loginForm() {
		
	}
	
	@PostMapping("signup")
	public String signupProcess(Member member, RedirectAttributes rttr) {
		
		try {
			service.signup(member);
			rttr.addFlashAttribute("message", "회원 가입이 정상적으로 되었습니다.");
			return "redirect:/list";
		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원 가입 실패");
			return "redirect:/member/signup";
		}
	}
	
	@GetMapping("list")
	@PreAuthorize("hasAuthority('admin')")
	public void list(Model model) {
		List<Member> list = service.getMemberList();
		model.addAttribute("memberList", list);
	}
	// 경로 : /member/info?id=aaaa
	@GetMapping("info")
	@PreAuthorize("(isAuthenticated() and (authentication.name eq #id)) or hasAuthority('admin')")
	public void info(String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
	}
	
	@PostMapping("remove")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #member.id)")
	public String remove(Member member, 
			RedirectAttributes rttr,
			HttpServletRequest request) throws Exception {
	
		boolean ok = service.remove(member);
		
		if(ok) {
			rttr.addFlashAttribute("message", "회원 탈퇴하였습니다.");
			
			//로그아웃
			request.logout();
			
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("message", "회원 탈퇴 중 문제가 발생되었습니다.");
			return "redirect:/member/info?id=" + member.getId();
		}
	}
	
	//1. 수정버튼 눌렀을 때 수정 폼으로 이동
	@GetMapping("modify")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #id)")
	public void modifyForm(String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
		
//		model.addAttribute(service.get(id));  // 모델 속성명 명시 안하면 해당객체의 lowerCamelCase로 들어감
		
	}
	
	
	//2. submit 눌렀을 때 회원정보 수정
	@PostMapping("modify") 
	@PreAuthorize("isAuthenticated() and (authentication.name eq #member.id)")
	public String modifyProcess(Member member, String oldPassword,  RedirectAttributes rttr) {
		System.out.println(member);
		boolean ok = service.modify(member, oldPassword);
		
		if(ok) {
			rttr.addFlashAttribute("message", "회원정보가 수정되었습니다.");
			return "redirect:/member/info?id=" + member.getId();
		} else {
			rttr.addFlashAttribute("messgae", "회원정보 수정 중 문제가 발생했습니다.");
			return "redirect:/member/modify?id=" + member.getId();
		}	
	}
	
}
