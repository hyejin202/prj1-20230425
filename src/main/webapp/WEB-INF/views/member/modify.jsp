<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<my:navBar></my:navBar>
	<my:alert></my:alert>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">

				<h1>${member.id }회원정보 수정</h1>
				<form id="modifyForm" action="/member/modify" method="post">

					<!-- .mb-3*5>(label.form-label[for]+input.form-control[name]) -->
					<div class="mb-3">
						<label for="idInput" class="form-label">ID</label> 
						<input id="idInput" type="text" class="form-control" name="id" value="${member.id }" readonly />
					</div>
					
					<div class="mb-3">
						<label for="pwInput" class="form-label">PW</label> 
						<input id="pwInput" type="text" class="form-control" name="password" value="" />
						<div class="form-text">
							입력하지 않으면 기존 패스워드가 유지됩니다.
						</div>
					</div>
					
					<div class="mb-3">
						<label for="pwInputCheck" class="form-label">PW 확인</label> 
						<input id="pwInputCheck" type="text" class="form-control" value="" />
						
						<div id="passwordSuccessText" class="d-none form-text text-primary">
							<i class="fa solid fa-check"></i>
							패스워드 일치
						</div>
						
						<div id="passwordFailText" class="d-none form-text text-danger">
							<i class="fa solid fa-check"></i>
							패스워드 불일치
						</div>
					</div>
					
					<div class="mb-3">
						<label for="nickNameInput" class="form-label">닉네임</label>
						<div class="input-group">
							<input id="nickNameInput" type="text" class="form-control" name="nickName" value="${member.nickName }" />
							<button type="button" id="checkNicknameBtn" class="btn btn-outline-secondary">중복확인</button>
						</div>
						<div id="availableNickname" class="d-none form-text text-primary">
							<i class="fa solid fa-check"></i>
							사용 가능한 닉네임 입니다.
						</div>
						<div id="notAvailableNickname" class="d-none form-text text-danger">
							<i class="fa solid fa-check"></i>
							사용 불가능한 닉네임 입니다.
						</div>
					</div>
					
					<div class="mb-3">
						<label for="emailInput" class="form-label">이메일</label> 
						<div class="input-group">
							<input id="emailInput" type="text" class="form-control" name="email" value="${member.email }" />
							<button type="button" id="checkEmailBtn" class="btn btn-outline-secondary">중복 확인</button>							
						</div>
						<div id="availableEmail" class="d-none form-text text-primary">
							<i class="fa solid fa-check"></i>사용 가능한 이메일 입니다.
						</div>
						<div id="notAvailableEmail" class="d-none form-text text-danger">
							<i class="fa solid fa-check"></i>사용 불가능한 이메일 입니다.
						</div>
						
					</div>
					<div class="mb-3">
						<label for="insertedInput" class="form-label">가입일자</label> 
						<input id="insertedInput" type="text" class="form-control" name="inserted" value="${member.inserted }" readonly />
					</div>


					<button disabled id="modifyButton" type="button" data-bs-toggle="modal" data-bs-target="#confirmModal" class="btn btn-danger" >수정</button>

				</form>

			</div>
		</div>
	</div>
	<!-- 수정 확인Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel"> 수정 확인</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<label for="inputOldPassword" class="form-label">이전 암호</label>
					<input form="modifyForm" id="inputOldPassword" class="form-control" type="password" name="oldPassword" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="submit" form="modifyForm"  class="btn btn-primary">확인</button>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script src="/js/member/modify.js"></script>
	

</body>
</html>