<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<!-- .container-lg>.row.justify-content-center>.col-12.col-md-8.col-lg-6 -->
	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
			
				<h1>${member.id }번 회원 정보</h1>
				<!-- .mb-3*5>(label.form-label[for]+input.form-control[name]) -->
				<div class="mb-3">
					<label for="inputId" class="form-label">ID</label>
					<input id="inputId" type="text" class="form-control" name="" value="${member.id }" readonly/>
				</div>
				<div class="mb-3">
					<label for="inputPassword" class="form-label">PW</label>
					<input id="inputPassword" type="text" class="form-control" name="" value="${member.password }" readonly/>
				</div>
				<div class="mb-3">
					<label for="inputNickName" class="form-label">닉네임</label>
					<input id="inputNickName" type="text" class="form-control" name="" value="${member.nickName }" readonly/>
				</div>
				<div class="mb-3">
					<label for="inputEmail" class="form-label">이메일</label>
					<input id="inputEmail" type="text" class="form-control" name="" value="${member.email }" readonly/>
				</div>
				<div class="mb-3">
					<label for="inputInserted" class="form-label">가입일시</label>
					<input id="inputInserted" type="text" class="form-control" name="" value="${member.inserted }" readonly/>
				</div>
			
			</div>
		</div>
	</div>

	 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>