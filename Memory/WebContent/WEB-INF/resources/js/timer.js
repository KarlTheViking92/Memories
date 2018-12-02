var sec = 0;
$(document).ready(function() {
	setInterval(function time() {
		var d = new Date();
		var hours =d.getHours();
		var min = d.getMinutes();
		if ((min + '').length == 1) {
			min = '0' + min;
		}
		
		if ((sec + '').length == 1) {
			sec = '0' + sec;
		}
		jQuery('#countdown #hour').html(hours);
		jQuery('#countdown #min').html(min);
		jQuery('#countdown #sec').html(sec);
		sec++;
	}, 1000);
});