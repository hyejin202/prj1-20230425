<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	<my:navBar />
	<my:alert />

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
					<h1>
					<span id="boardIdText">
						 ${board.id }
					</span>
					번 게시물
					</h1>
					
					<div>
						<h1>
							<span id="likeIcon">
								<i class="fa-regular fa-thumbs-up"></i>
							</span>
							<span id="likeNumber">
								<%-- ${board.like } --%>
							</span>
						</h1>
				</div>
			
				
				<div class="mb-3">
					<lable class="form-label" for="titleInput">제목</lable>
					<input type="text" class="form-control" readonly value="${board.title }" />
					

				</div>
				<!-- 그림 파일 출력 -->
				<div class="mb-3">
					<c:forEach items="${board.fileName}" var="fileName">
						<div class="mb-3">
							<!-- http://localhost:8080/image/3713990/rp.jfif -->
							<!-- localhost8080/image/게시물번호/fileName -->
							<!-- 이미지 파일 aws s3의 버킷에 저장됨 -->
							<img class="img-thumbnail img-fluid" src="${bucketUrl}/${board.id }/${fileName}" alt="" />
						</div>
					</c:forEach>
				</div>
				
				
				<div class="mb-3">
					<lable class="form-label" for="bodyTextarea">본문</lable>
					<textarea class="form-control" rows="10" readonly="">${board.body }</textarea>
				</div>
				<div class="mb-3">
					<lable class="form-label" for="writerInput">작성자</lable>
					<input type="text" class="form-control" readonly value="${board.writer }" />
				</div>
				<div class="mb-3">
					<lable class="form-label" for="insertedInput">작성일시</lable>
					<input type="text" class="form-control" readonly value="${board.inserted }" />
				</div>
				
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="name" var="userId"/> <!-- 게시물 작성자 name얻어냄 -->
					<c:if test="${userId eq board.writer }">
						<div class="mb-3">
							<a class="btn btn-secondary" href="/modify/${board.id }">수정</a>
							<!-- modal trigger button -->
							<button class="btn btn-danger" id="removeButton" data-bs-toggle="modal" data-bs-target="#deleteConfirmModal">삭제</button>
						</div>
					</c:if>
				</sec:authorize>
				
			</div>
		</div>
	</div>
	</div>
	
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="name" var="userId"/> 
		<c:if test="${userId eq board.writer }">
			<div class="d-none">
				<!-- post방식으로 보내기 위해 display-none 형식의 form에 담아 해당 id를 보냄  -->
				<form action="/remove" method="post" id="removeForm">
					<input type="text" name="id" value="${board.id }" />
				</form>
			</div>
	
		
			<!-- Modal -->
			<div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
							<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
						</div>
						<div class="modal-body">삭제하시겠습니까?</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
							<button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</sec:authorize>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script src="/js/board/like.js"></script>
	
</body>
</html>