/*$(function() {
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
});*/

$(function() {

	$('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});

});