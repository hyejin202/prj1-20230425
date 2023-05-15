$("#likeIcon").click(function() {
	//게시물 번호 request body에 추가
	const boardId = $("#boardIdText").text().trim();
	//const data = {boardId: boardId};
	const data = {boardId};
	
	$.ajax("/like", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		
		success: function(data) {
			if(data.like){
				//꽉찬 좋아요
				$("#likeIcon").html(`<i class="fa-solid fa-thumbs-up"></i>`);
			} else {
				// 빈 좋아요
				$("#likeIcon").html(`<i class="fa-regular fa-thumbs-up"></i>`);
			}
		}
		//error: ,
		//complete: ,
		
	})
})