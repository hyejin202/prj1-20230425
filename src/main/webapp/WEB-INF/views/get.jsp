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
</head>
<body>
	<my:navBar />

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>${board.id }번게시물</h1>
				<div class="mb-3">
					<lable class="form-label" for="titleInput">제목</lable>
					<input type="text" class="form-control" readonly="" value="${board.title }" />

				</div>
				<div class="mb-3">
					<lable class="form-label" for="bodyTextarea">본문</lable>
					<textarea class="form-control" rows="10" readonly="">${board.body }</textarea>
				</div>
				<div class="mb-3">
					<lable class="form-label" for="writerInput">작성자</lable>
					<input type="text" class="form-control" readonly="" value="${board.writer }" />
				</div>
				<div class="mb-3">
					<lable class="form-label" for="insertedInput">작성일시</lable>
					<input type="text" class="form-control" readonly="" value="${board.inserted }" />
				</div>
				<div class="mb-3">
					<a class="btn btn-secondary" href="/modify/${board.id }">수정</a>
					<button class="btn btn-danger" id="removeButton" form="removeForm" type="submit">삭제</button>
				</div>

				<div class="d-none">
					<!-- post방식으로 보내기 위해 display-none 형식의 form에 담아 해당 id를 보냄  -->
					<form action="/remove" method="post" id="removeForm">
						<input type="text" name="id" value="${board.id }" />
					</form>
				</div>
			</div>
		</div>

	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<!-- jquery -->
	<script>
		$("#removeButton").click(function(e) { //id가 removeButton인 버튼을 클릭시 실행
			// 서브밋 진행 이벤트 막기
			e.preventDefault();

			const res = confirm("삭제하시겠습니까?"); //창띄우기
			if (res) {
				//서브밋 실행
				$("#removeForm").submit();
			}
		});
	</script>

	<c:if test="${not empty param.success }">
		<script>
			alert("게시물이 수정되었습니다.");
		</script>
	</c:if>
</body>
</html>