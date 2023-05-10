package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//패스워드 암호화
	public boolean signup(Member member) {
		String plain = member.getPassword();
		member.setPassword(passwordEncoder.encode(plain));
		
		int cnt = mapper.insert(member);
		return cnt == 1;
		
	}

	public List<Member> getMemberList() {
		List<Member> list = mapper.selectAll();
		return list;
	}

	public Member get(String id) {
		return mapper.selectById(id);
	}

	public boolean remove(Member member) {
		Member oldMember = mapper.selectById(member.getId());
		int cnt = 0;
		
		if(passwordEncoder.matches(member.getPassword(), oldMember.getPassword())) {  //matches() 첫번째 파라미터: 평문, 두번째파라미터 : 암호화
			// 암호가 같으면?
			
			// 이 회원이 작성한 게시물 row 삭제
			boardService.removeByWriter(member.getId());
			
			// Member 테이블에서 삭제
			cnt = mapper.deleteById(member);
		} //else {
			//암호가 같지 않으면?
//		}
		return cnt == 1;
		
	}

	public boolean modify(Member member, String oldPassword) {
		// 패스워드를 바꾸기 위해 입력했다면..
		if(! member.getPassword().isBlank()) {
			
			//입력된 패스워드를 암호화
			String plain = member.getPassword();
			member.setPassword(passwordEncoder.encode(plain));
		}
		
		Member oldMember = mapper.selectById(member.getId());
		int cnt = 0;
		
		if(passwordEncoder.matches(oldPassword, oldMember.getPassword())) {
			//이전 암호와 일치하면 수정
			cnt = mapper.update(member);			
		}
		
		return cnt == 1;
		
	}
	
}
