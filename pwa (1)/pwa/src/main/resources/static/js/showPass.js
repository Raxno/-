function showPassword(inputId, eyeId){
	if ($(inputId).attr('type') == 'password'){
		$(eyeId).addClass('view');
		$(inputId).attr('type', 'text');
	} else {
		$(eyeId).removeClass('view');
		$(inputId).attr('type', 'password');
	}
}