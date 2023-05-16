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

	<my:navBar current="list" />
	<my:alert />

	<div class="container-lg">
		<h1>게시물 목록 보기</h1>
		<a class="btn btn-secondary" href="/add"><i class="fa-solid fa-plus"></i></a>
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th><i class="fa-regular fa-thumbs-up"></i></th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일시</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList }" var="board">
					<tr>
						<td>${board.id }</td>
						<td>${board.likeCount }</td>
						<td>
							<!-- 제목 클릭시 본문내용으로 넘어감 --> 
							<a href="/id/${board.id }"> <!--  pathVariable로 함 (쿼리스트링/requestParam 방법도 있음) --> 
								${board.title }
							</a>
							<c:if test="${board.fileCount > 0 }">
								<span>파일 : <span class="badge text-bg-info">
								<i class="fa-regular fa-images"></i>
								${board.fileCount }
								</span>
							</c:if>
						</td>
						<td>${board.writer }</td>
						<td>${board.inserted }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- 페이지네이션 -->
	<div class="container-lg">
		<div class="row">
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-center">  <!-- 가운데로 위치 설정 -->

					<!-- 이전 버튼 -->
					<c:if test="${ pageInfo.currentPageNum ne 1}">  <!-- 현재페이지가 1이 아닐 때 실행 (혹은 1보다 클때 : gt 1) -->
						<li class="page-item ">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="${pageInfo.currentPageNum-1 }" />
								<c:if test="${not empty param.search }">
									<c:param name="search" value="${param.search }" />
								</c:if>
							</c:url> 
							<a class="page-link" href="${pageLink }">
								<i class="fa-solid fa-angle-left"></i>  <!-- 이전버튼모양 -->
							</a>
						</li>
					</c:if>

					<!-- 페이지바 -->
					<c:forEach begin="${pageInfo.leftPageNum }" end="${pageInfo.rightPageNum }" var="pageNum">  
						<c:url value="/list" var="pageLink">
							<c:param name="page" value="${pageNum }" />
							<c:if test="${not empty param.search }">   <!-- 검색 안했을 시 실행X -->
								<c:param name="search" value="${param.search }" />
							</c:if>
						</c:url>
						<li class="page-item"> 				<!-- 현재 페이지 활성화 -->
							<a class="page-link ${pageNum eq pageInfo.currentPageNum ? 'active' : '' }" href="${pageLink }">${pageNum }</a> 
						</li>
					</c:forEach>

					<!-- 다음 버튼 -->
					<c:if test="${pageInfo.currentPageNum lt pageInfo.lastPageNum }">
						<!-- 현재 페이지가 마지막페이지보다 작을때 실행 -->
						<li class="page-item">
							<c:url value="/list" var="pageLink">
								<c:param name="page" value="${pageInfo.currentPageNum+1 }" />
								<c:if test="${not empty param.search }">
									<c:param name="search" value="${param.search }" />
								</c:if>
							</c:url> 
							<a class="page-link" href="${pageLink }">
								<i class="fa-solid fa-angle-right"></i>
							</a>
						</li>
					</c:if>

				</ul>
			</nav>
		</div>
	</div>

	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>