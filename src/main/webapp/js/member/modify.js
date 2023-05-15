let checkEmail = true;
let checkNickname = true;
let checkPassword = true;

// 수정버튼 활성화
function enableSubmit() {
	if(checkEmail && checkNickname && checkPassword) {
		$("#modifyButton").removeAttr("disabled");
	}else {
		$("#modifyButton").attr("disabled", "");  //disabled = "disabled"
	}
}

$("#emailInput").keyup(function () {
	checkEmail = false;
	$("#availableEmail").addClass("d-none");
	$("#notAvailableEmail").addClass("d-none");
	enableSubmit();
});
$("#nickNameInput").keyup(function () {
	checkNickname = false;
	$("#availableNickname").addClass("d-none");
	$("#notAvailableNickname").addClass("d-none");
	enableSubmit();
});

//nickname 중복확인 버튼 클릭시
$("#checkNicknameBtn").click(function() {
	const nickName = $("#nickNameInput").val();
	
	$.ajax("/member/checkNickname/" + nickName, {
		success: function(data){
			// '{"available": true}'
			if(data.available) {
				// 사용가능하다는 메세지 출력
				$("#availableNickname").removeClass("d-none");
				$("#notAvailableNickname").addClass("d-none");
				//중복확인 됐다는 표시
				checkNickname = true;
			}else {
				//사용 불가능하다는 메세지 출력
				$("#availableNickname").addClass("d-none");
				$("#notAvailableNickname").removeClass("d-none");
				//중복확인 안됐다는 표시
				checkNickname = false;
			}
		},
		//수정버튼 활성화/비활성화
		complete: enableSubmit
	
	})
})
//email 중복확인 버튼 클릭시
$("#checkEmailBtn").click(function() {
	const email = $("#emailInput").val();
	
	$.ajax("/member/checkEmail/" + email, {
		success: function(data) {
			if(data.available) {
				$("#availableEmail").removeClass("d-none");
				$("#notAvailableEmail").addClass("d-none");
				checkEmail = true;
			}else {
				$("#availableEmail").addClass("d-none");
				$("#notAvailableEmail").removeClass("d-none");
				checkEmail = false;
			}
		},
		complete: enableSubmit
		
	})
})

//패스워드 , 패스워드 체크 인풋에 키업 발생하면
$("#pwInput, #pwInputCheck").keyup(function() {

	const pw1 = $("#pwInput").val();
	const pw2 = $("#pwInputCheck").val();

	if (pw1 == pw2) {
		$("#modifyButton").removeClass("disabled");
		$("#passwordSuccessText").removeClass("d-none")
		$("#passwordFailText").addClass("d-none")
		checkPassword = true;
	} else {
		$("#modifyButton").addClass("disabled");
		$("#passwordFailText").removeClass("d-none")
		$("#passwordSuccessText").addClass("d-none")
		checkPassword = false;
	}
	 enableSubmit();
	
})