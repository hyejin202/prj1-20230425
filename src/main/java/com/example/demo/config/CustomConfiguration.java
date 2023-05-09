package com.example.demo.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.access.expression.*;

import jakarta.annotation.*;
import jakarta.servlet.*;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.*;
import software.amazon.awssdk.services.s3.*;

@Configuration
// 특정 메소드 접근 제한
@EnableMethodSecurity
public class CustomConfiguration {
	
	@Value("${aws.accessKeyId}")
	private String accessKeyId;
	@Value("${aws.secretAccessKey}")
	private String secretAccessKey;
	@Value("${aws.bucketUrl}")
	private String bucketUrl;
	
	@Autowired
	private ServletContext application;
	
	//빈이 만들어지자 마자 바로 실행되도록
	@PostConstruct
	public void init() {
		//  application 영역에 넣어주기
		application.setAttribute("bucketUrl", bucketUrl);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilerChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
//		http.formLogin(Customizer.withDefaults());  //로그인 페이지 기본으로 설정
		http.formLogin().loginPage("/member/login"); // 로그인 페이지 경로 명시
		http.logout().logoutUrl("/member/logout"); // 로그아웃 페이지 경로 명시
		
//		http.authorizeHttpRequests().requestMatchers("/add").authenticated();  // "/add"페이지 접근 제한
//		http.authorizeHttpRequests().requestMatchers("/member/signup").anonymous();  // 로그아웃 상태 일때만 회원가입에 접근 가능
//		http.authorizeHttpRequests().requestMatchers("/**").permitAll();  // 그 외 다른 곳은 모두 접근 가능
		
		// spring security expression 사용 (복잡한 권한 제한 시 이용)
//		http.authorizeHttpRequests()
//			.requestMatchers("/add")
//			.access(new WebExpressionAuthorizationManager("isAuthenticated()"));
//		http.authorizeHttpRequests()
//			.requestMatchers("/member/signup")
//			.access(new WebExpressionAuthorizationManager("isAnonymous()"));
//		http.authorizeHttpRequests()
//			.requestMatchers("/**")
//			.access(new WebExpressionAuthorizationManager("permitAll"));
		
		return http.build();
	}

	@Bean
	public S3Client s3client() {

		AwsCredentials credentials 
		= AwsBasicCredentials.create(accessKeyId, secretAccessKey);
		AwsCredentialsProvider provider 
		= StaticCredentialsProvider.create(credentials);
		
		S3Client s3client = S3Client.builder()
				.credentialsProvider(provider)
				.region(Region.AP_NORTHEAST_2)
				.build();
		
		return s3client;
	}
}
