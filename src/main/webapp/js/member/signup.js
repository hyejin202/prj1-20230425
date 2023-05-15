let checkId = false;
let checkPassword = false;
let checkNickName = false;
let checkEmail = false;

function enableSubmit() {
	console.log(checkId, checkPassword, checkNickName, checkEmail);
	if(checkId && checkPassword && checkNickName && checkEmail) {
		$("#signupSubmit").removeAttr("disabled");
	}else {
		$("#signupSubmit").attr("disabled", "")
	}
}

//input 아이디에 키보드 입력 발생시
$("#inputId").keyup(function() {
	//아이디 중복확인 다시
	checkId = false;
	$("#availableIdMessage").addClass("d-none")
	$("#notAvailableIdMesssage").addClass("d-none")
	//submit버튼 비활성화
	enableSubmit();
})

//닉네임 인풋에 키보드 입력 발생시
$("#inputNickName").keyup(function() {
	//닉네임 중복확인 다시
	checkNickName = false;
	$("#availableNicknameMessage").addClass("d-none")
	$("#notAvailableNicknameMessage").addClass("d-none")
	//submit버튼 비활성화
	enableSubmit();
})

//이메일 인풋에 키보드 입력 발생시
$("#inputEmail").keyup(function() {
	//이메일 중복확인 다시
	checkEmail = false;
	$("#availableEmailMessage").addClass("d-none")
	$("#notAvailableEmailMessage").addClass("d-none")
	//submit버튼 비활성화
	enableSubmit();
})

//id 중복확인 버튼이 클릭되면
$("#checkIdBtn").click(function() {
	const userid = $("#inputId").val();
	
	//입력한 ID와 ajax 요청 보내서
	$.ajax("/member/checkId/" + userid, {
		success: function(data) {
			// '{"available" : true}'
			
			if (data.available) {
				// 사용가능하다는 메세지 출력
				$("#availableIdMessage").removeClass("d-none");
				$("#notAvailableIdMesssage").addClass("d-none");
				checkId = true;
				
			}else {
				//사용가능하지 않다는 메세지 출력
				$("#availableIdMessage").addClass("d-none");
				$("#notAvailableIdMesssage").removeClass("d-none");
				checkId = false;
			}
		},
		complete: enableSubmit
	})
});

// nickname 중복확인 버튼이 클릭되면
$("#checkNicknameBtn").click(function() {
	const userNickname = $("#inputNickName").val();
	$.ajax("/member/checkNickname/" + userNickname, {
		success: function(data) {
			// '{"available" : true}'
			if(data.available) {
				//사용가능하다는 메세지 출력
				$("#availableNicknameMessage").removeClass("d-none");
				$("#notAvailableNicknameMessage").addClass("d-none");
				checkNickName = true;
			}else {
				//사용 불가능하다는 메세지 출력
				$("#availableNicknameMessage").addClass("d-none");
				$("#notAvailableNicknameMessage").removeClass("d-none");
				checkNickName = false;
			}
		},
		complete: enableSubmit
	});
});


//email 중복확인 버튼이 클릭되면
$("#checkEmailBtn").click(function() {
	const userEmail = $("#inputEmail").val();
	$.ajax("/member/checkEmail/" + userEmail, {
		success: function(data) {
			if(data.available) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
				checkEmail = true;
			} else {
				$("#availableEmailMessage").addClass("d-none");
				$("#notAvailableEmailMessage").removeClass("d-none");
				checkEmail = false;
			}
		},
		complete:  enableSubmit
	})
});
// 패스워드, 패스워드 체크 인풋에 키업 이벤트 발생하면
$("#inputPassword, #inputPasswordCheck").keyup(function() {

	//패스워드에 입력한 값
	const pw1 = $("#inputPassword").val();
	// 패스워드확인에 입력한 값이
	const pw2 = $("#inputPasswordCheck").val();

	if (pw1 == pw2) {
		// 같으면
		//submit 버튼 활성화
		$("#signupSubmit").removeAttr("disabled");
		//패스워드가 같다는 메세지
		$("#passwordSuccessText").removeClass("d-none");
		$("#passwordFailText").addClass("d-none");
		checkPassword = true;
	} else {
		// 그렇지 않으면
		// submit버튼 비활성화
		$("#signupSubmit").attr("disabled", "");
		//패스워드 다르다는 메세지 출력
		$("#passwordFailText").removeClass("d-none");
		$("#passwordSuccessText").addClass("d-none");
		checkPassword = false;
	}
	enableSubmit();
})