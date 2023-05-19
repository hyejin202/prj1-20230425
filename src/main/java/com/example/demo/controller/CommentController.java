package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("comment")
public class CommentController {
	
	@Autowired
	private CommentService service;

	@GetMapping("list")
	@ResponseBody
	public List<Comment> list(@RequestParam("board") Integer boardId) {
//		return List.of("댓1", "댓2", "댓3");
		return service.list(boardId);
	}
	
	@PostMapping("add")
	//@ResponseBody
	public ResponseEntity<Map<String, Object>> add(@RequestBody Comment comment)  {
		Map<String, Object> res = service.add(comment);
		
		return ResponseEntity.ok().body(res);
	}
	
//	@RequestMapping(path = "id/{id}", method = RequestMethod.DELETE)
	@DeleteMapping("id/{id}")
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
	@ResponseBody
	public ResponseEntity<Map<String, Object>> update(@RequestBody Comment comment) {
		Map<String, Object> res = service.update(comment);
		return ResponseEntity.ok().body(res);
	}
}