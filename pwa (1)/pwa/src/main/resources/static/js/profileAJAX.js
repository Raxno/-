function profileCheck() {
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/my-profile', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			let attrs = JSON.parse(xhr.responseText);
    			if(attrs[0].owner){
    				$("#session").hide();
    				document.getElementById("copyButton").remove();
    			}
    			if(!attrs[0].owner && attrs[0].role == "USER"){
    				document.getElementById("deleteButton").remove();
    			}
    			if(!attrs[0].owner){
    				$("#addPhotoButton").hide();
    			}
    			if(!(attrs[0].role == "ADMIN"))
    				document.getElementById("ban").remove();
    			else{
    				$("#ban").click(function () {
    					ban();
    					$("#ban").fadeOut(100);
    				});
    			}
    		}
    	}
    }
    xhr.send("n=" + nick);
}

function ban(){
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/ban', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);

    xhr.send("n=" + nick);
}