package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("comment")
public class CommentController {
	
	@Autowired
	private CommentService service;

	@GetMapping("list")
//	@ResponseBody
	public List<Comment> list(@RequestParam("board") Integer boardId, Authentication authentication) {
//		return List.of("댓1", "댓2", "댓3");
		return service.list(boardId, authentication);
	}
	
	@PostMapping("add")
	//@ResponseBody
	//@PreAuthorize("authenticated") // 300이 리다이렉트
	public ResponseEntity<Map<String, Object>> add(@RequestBody Comment comment, Authentication authentication)  {
		
		if (authentication == null) {   //로그아웃 상태일 경우
			Map<String, Object> res = Map.of("message", "로그인 후 댓글 작성 부탁");
			return ResponseEntity.status(401).body(null);  //변화 없음 코드
		} else {                       //로그인 상태일 경우
			Map<String, Object> res = service.add(comment, authentication);
			return ResponseEntity.ok().body(res);  //상태코드 ok(200)
			
		}
		
	}
	
//	@RequestMapping(path = "id/{id}", method = RequestMethod.DELETE)
	@DeleteMapping("id/{id}")
//	@ResponseBody
	@PreAuthorize("authenticated and @customSecurityChecker.checkCommentWriter(authentication, #id)")
	public ResponseEntity<Map<String, Object>>  remove(@PathVariable("id") Integer id) {
		Map<String, Object> res = service.remove(id);
		return ResponseEntity.ok().body(res);
	}
	@GetMapping("id/{id}")
	@ResponseBody
	public Comment get(@PathVariable("id") Integer id) {
		return service.get(id);
	}
	
	@PutMapping("update")
//	@ResponseBody
	@PreAuthorize("authenticated and @customSecurityChecker.checkCommentWriter(authentication, #comment.id)")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Comment comment) {
		Map<String, Object> res = service.update(comment);
		return ResponseEntity.ok().body(res);
	}
}
