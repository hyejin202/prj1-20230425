package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
//@Component - Service 어노테이션 안에 Component 어노테이션 있음
public class BoardService {

	@Autowired
	private BoardMapper mapper;
	
	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}
	
	//id 받아서 mapper에 건네주면 mapper는 데이터를 가져와 해당 id의 본문을 보여줌
	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	// 수정 메소드, 수정되면 true(1) 안되면 false(0)
	public boolean modify(Board board) {
		int cnt = mapper.update(board);   // board파라미터 받아서 mapper한테 update 시킴
		
		return cnt == 1;   // 레코드 1 행이 수정되었다면 true(1)리턴
	}
 
	//삭제 메소드
	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}
	
	
}
