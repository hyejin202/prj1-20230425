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
	public Map<String, Object> listBoard(Integer page) {
		//페이지 당 행의 수
		Integer rowPerPage = 10;  
		//쿼리 LIMIT절에 사용할 시작 인덱스
		Integer startIndex = (page-1) * rowPerPage;
		
		// 페이지네이션 필요한 정보
		//전체레코드 수
		Integer numOfRecords = mapper.countAll();
		//마지막 페이지 번호
		Integer lastPageNum = (numOfRecords-1) / rowPerPage + 1; 
		
		// 설정 : 총 10페이지 - 현재 페이지 기준으로 왼쪽 4개, 오른쪽 5개
		// 페이지네이션 왼쪽 번호
		Integer leftPageNum = page - 5;
		//1보다 작을 수 없다
		leftPageNum = Math.max(leftPageNum, 1);
		//페이지네이션 오른쪽 번호
		Integer rightPageNum = leftPageNum + 9;
		//마지막 페이지보다 클 수 없음
		rightPageNum = Math.min(lastPageNum, rightPageNum);
		
		// numOfRecordslastPageNumleftPageNumrightPageNum - map에 넣어 저장
		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("rightPageNum", rightPageNum);
		pageInfo.put("leftPageNum", leftPageNum);

		
		// 게시물 목록
		List<Board> list =  mapper.selectAllPaging(startIndex, rowPerPage);  //startIndex필요함
		return Map.of("pageInfo", pageInfo, "boardList", list);
		
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
	
	// 추가 메소드
	public boolean add(Board board) {
		int cnt = mapper.insert(board);
//		int cnt = 0;   //추가 실패
		return cnt == 1;
	}

	
	
}
