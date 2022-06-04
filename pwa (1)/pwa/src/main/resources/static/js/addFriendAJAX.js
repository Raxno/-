$(document).ready(function () {
	buttonsList = $("#nickPlus").find("> #addFriend");
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/add-friend/button-text', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		switch(xhr.responseText){
    		case "add":
    			$(buttonsList[1]).remove();
    			$(buttonsList[2]).remove();
    			break;
    		case "delete":
    			$(buttonsList[0]).remove();
    			$(buttonsList[2]).remove();
    			break;
    		case "unsubscribe":
    			$(buttonsList[0]).remove();
    			$(buttonsList[1]).remove();
    			break;
    		case "hide":
    			$(buttonsList[0]).remove();
    			$(buttonsList[1]).remove();
    			$(buttonsList[2]).remove();
    			break;
    		}
    	}
    }
    xhr.send("n=" + nick);
	
	$(buttonsList).click(function() {
		$(this).fadeOut(100);
		var nick = document.getElementById("nick").innerHTML;
		var token = document.head.querySelector("meta[name='_csrf']").content;
		var header = document.head.querySelector("meta[name='_csrf_header']").content;
		var xhr = new XMLHttpRequest();
		xhr.open('POST', '/ajax/add-friend', true);
	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	    xhr.setRequestHeader(header, token);
	    console.log(nick);
	    xhr.send("n=" + nick);
	});
});