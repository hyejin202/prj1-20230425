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
	
	@PostMapping("remove")
	public String remove(Member member, RedirectAttributes rttr) {
		
		boolean ok = service.remove(member);
		
		if(ok) {
			rttr.addFlashAttribute("message", "회원 탈퇴하였습니다.");
			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("message", "회원 탈퇴 중 문제가 발생되었습니다.");
			return "redirect:/member/info?id=" + member.getId();
		}
	}
	
	//1. 수정버튼 눌렀을 때 수정 폼으로 이동
	@GetMapping("modify")
	public void modifyForm(String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
		
//		model.addAttribute(service.get(id));  // 모델 속성명 명시 안하면 해당객체의 lowerCamelCase로 들어감
		
	}
	
	
	//2. submit 눌렀을 때 회원정보 수정
	@PostMapping("modify") 
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
