package com.example.demo.security;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 입력한 username으로 db에 있는 member 값을 꺼냄 
		Member member = mapper.selectById(username);
		
		//만약 해당 username이 없다면 없다고 보냄
		if(member == null) {
			throw new UsernameNotFoundException(username + "회원이 없습니다.");
		}
		
		//있다면 member에 있는 값들을 꺼내
		UserDetails user = User.builder()
				.username(member.getId())
				.password(member.getPassword())
				.authorities(List.of())
				.build();
		//user로 리턴
		return user;
	}
	

	
}
