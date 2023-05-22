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
	
	<div class="toast-container position-fixed  top-0 start-50 translate-middle-x p-3">
	  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
	  <div class="d-flex">
	    <div class="toast-body"> </div>
	      <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
		  </div>
	  </div>
	</div>

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<dvi class="d-flex">
				<div></div>
					
					<h1>
					<span id="boardIdText">
						 ${board.id }
					</span>
					번 게시물
					</h1>
					
					<div>
						<h1>
							<span id="likeIcon">
								<c:if test="${board.liked }">
									<i class="fa-solid fa-thumbs-up"></i>
								</c:if>
								<c:if test="${not board.liked }">
									<i class="fa-regular fa-thumbs-up"></i>
								</c:if>
								
							</span>
							<span id="likeNumber">
								${board.likeCount}
							</span>
						</h1>
				</div>
				</dvi>
			
				
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
				
				<div id="commentContainer">
					<h1>
						<i class="fa-solid fa-comments"></i>
					</h1>
					<sec:authorize access="isAuthenticated()">
						<div class="mb-3" id="addCommentContainer">
						
							<div class="input-group">
								<div class="form-floating">
									<textarea id="commentTextArea" class="form-control" style="height: 97px" placeholder="댓글을 남겨주세요"></textarea>
									<label for="floatingTextArea">댓글을 남겨주세요</label>
								</div>
								<button id="sendCommentBtn" class="btn btn-outline-primary">
									<i class="fa-regular fa-paper-plane"></i>
								</button>
							</div>
						</div>
					</sec:authorize>
					
					
					<ul class="list-group" id="commentListContainer">
						
						
					</ul>
					
					</div>
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
	
	<!-- 댓글 삭제 모달 -->
	<div class="modal fade" id="deleteCommentConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title fs-5">댓글 삭제 확인</h1>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">삭제 하시겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
						<button id="deleteCommentModalButton" data-bs-dismiss="modal" type="submit" class="btn btn-danger">삭제</button>
					</div>
				</div>
			</div>
		</div>
		
		<%-- 댓글 수정 모달 --%>
		<div class="modal fade" id="commentUpdateModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h1 class="modal-title fs-5">댓글 수정</h1>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="modal-body">
						<div id="updateCommentContainer">
							<input type="hidden" id="commentUpdateIdInput" />
							<textarea class="form-control" id="commentUpdateTextArea"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
						<button type="button" class="btn btn-primary" id="updateCommentBtn" data-bs-dismiss="modal">수정</button>
					</div>
				</div>
			</div>
		</div>
		
	</sec:authorize>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

	<script src="/js/board/like.js"></script>
	<script src="/js/board/comment.js"></script>
	
</body>
</html>