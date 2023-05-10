$("#pwInput, #pwInputCheck").keyup(function() {

	const pw1 = $("#pwInput").val();
	const pw2 = $("#pwInputCheck").val();

	if (pw1 == pw2) {
		$("#modifyButton").removeClass("disabled");
		$("#passwordSuccessText").removeClass("d-none")
		$("#passwordFailText").addClass("d-none")

	} else {
		$("#modifyButton").addClass("disabled");
		$("#passwordFailText").removeClass("d-none")
		$("#passwordSuccessText").addClass("d-none")
	}
})