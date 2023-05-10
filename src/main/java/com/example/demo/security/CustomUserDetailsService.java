package com.example.demo.security;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.authority.*;
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
		
		System.out.println("******로그인하기 위한 코드********");
		System.out.println(member);
		
		//만약 해당 username이 없다면 없다고 보냄
		if(member == null) {
			throw new UsernameNotFoundException(username + "회원이 없습니다.");
		}
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();  //권한이 여러개일수 있으니 List 타입으로 받기
		
		for(String auth : member.getAuthority()) {  //member자바빈에 있는 authority 값을 가져옴
			authorityList.add(new SimpleGrantedAuthority(auth));  //SimpleGrantedAuthority(String role)
		}
		
		
		//있다면 member에 있는 값들을 꺼내
		UserDetails user = User.builder()
				.username(member.getId())
				.password(member.getPassword())
				.authorities(authorityList)   //authorities(SimpleGrantedAuthority타입)
//				.authorities(member.getAuthority().stream().map(SimpleGrantedAuthority::new).toList())
				// member 빈에 있는 authority값을 가져와서, 읽어서, SimpleGrantedAuthority타입으로 맵화해서 리스트로 변환
				.build();
		//user로 리턴
		return user;
	}
	

	
}
