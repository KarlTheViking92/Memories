$(function() {
	$('#register-form-link').click(function(e) {
		console.log("SWITCH TO LOGIN");
		// $("#register-form").css("display", "");
		$("#register-form").delay(150).fadeIn(150);
		$("#login-form").fadeOut(150);

		// $("#login-form").css("display","none");
		// $('#register-form-link').removeClass('active');
		// $(this).addClass('active');
		e.stopPropagation();
	});
	$('#login-form-link').click(function(e) {
		console.log("SWITCH TO REGISTRATION");
		$("#login-form").delay(150).fadeIn(150);
		$("#register-form").fadeOut(150);
		e.stopPropagation();
	});
});