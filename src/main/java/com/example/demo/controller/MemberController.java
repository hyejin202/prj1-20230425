package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("signup")
	public void sighupForm() {
		
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
	public void list(Model model) {
		List<Member> list = service.getMemberList();
		model.addAttribute("memberList", list);
	}
	// 경로 : /member/info?id=aaaa
	@GetMapping("info")
	public void info(String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
	}
	
	
	
	
	
	
}
