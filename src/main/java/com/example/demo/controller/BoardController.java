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
@RequestMapping("/")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// 경로 : http://localhost:8080
	// 경로 : http://localhost:8080/list?page=
	
	//게시물 목록
//	@RequestMapping({"/", "list"}, method = RequestMethod.GET)
	 @GetMapping({"/", "list"})
	 public String list(Model model) {   // 경로가 2가지이므로 충돌안되게 경로지정(String 리턴타입)
		 // 1. request param 수집/가공
		 // 2. business logic 처리
		 List<Board> list = service.listBoard();   //service의 listBoard()메소드 실행 결과를 list에 담음
		 // 3. add attribute
		 model.addAttribute("boardList", list);
		 
		 // 4. forward / redirect 
		 return  "list";
		 
	 }
	 
	 // 해당 제목(id)의 본문 불러오기
	 @GetMapping("/id/{id}")
	 public String board(@PathVariable("id") Integer id, Model model) {
		 // 1. request param
		 // 2. business logic 처리
		 Board board = service.getBoard(id); // Board객체에 service의 getById() 메소드 결과값 넣음
		 // 3. add attribute
		 model.addAttribute("board", board);
		
		 // 4. forward/redirect
		 
		 return "get";   // get이라는 jsp파일로 리턴
	 }
	 
	 //수정하기 버튼 누르면 해당 아이디 가져와서 modify수정 폼으로 이동
	 @GetMapping("/modify/{id}")
	 public String modify(@PathVariable("id") Integer id, Model model) {
		 model.addAttribute("board", service.getBoard(id));
		 return "modify";
		 
	 }
	 
	// 수정한 후 "수정"버튼 누르면 수정한 내용으로 보드내용 update하기
//	 @RequestMapping(value= "/modify/{id}", method = RequestMethod.POST)
	 @PostMapping("/modify/{id}")
	 public String modifyProcess(Board board, RedirectAttributes rttr) { //board파라미터 받아서(안에 수정된 게시물 정보 있음
		 boolean ok = service.modify(board);                //service의 modify()메소드 실행
		 
		 if(ok) {
			 //해당 게시물 보기로 리다이렉션
			 rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되었습니다.");
			 return "redirect:/id/" + board.getId();   //redirect:/list - 게시물목록보기로 리다이렉션
		 } else {
			 // 수정 잘 안됐을 때 수정폼으로 리다이렉션
			 rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되지 않았습니다.");
			 return "redirect:/modify/" + board.getId();
		 }
	 }
	 
	 @PostMapping("remove")
	 public String remove(Integer id, RedirectAttributes rttr) {
		 boolean ok = service.remove(id);
		 if(ok) {
			 // 쿼리스트링에 추가
//			 rttr.addAttribute("success", "remove");
			 //모델에 추가
			 rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
			 
			 return "redirect:/list";
		 }else {
			 return "redirect/id/" + id;
		 }
	 }
	 
	 @GetMapping("add")
	 public String addForm() {
		 // 게시물 작성 form (view)로 포워드
		 
		 return "add";
	 }
	 
	 @PostMapping("add")
	 public String addProcess(Board board, RedirectAttributes rttr) {
		 // 새 게시물 db에 추가
		 //1.
		 //2. business logic처리
		 boolean ok = service.add(board);             
		 //3. add attribute
		 if(ok) {
			 rttr.addFlashAttribute("message", board.getId() + "번 게시물이 등록되었습니다.");
			 return "redirect:/list";
//			 return "redirect:/id/" + board.getId();
		 } else {
			 //추가 하기 실패했을때, 작성했던 내용 살리기
			 rttr.addFlashAttribute("message", "게시물 등록 중 문제가 발생하였습니다.");
			 rttr.addFlashAttribute("board", board);
			 return "redirect:/add";
		 }
		 //4. forward / redirect
		 // 게시물 목록보기로 리다이렉션
	 }
	
	
}
