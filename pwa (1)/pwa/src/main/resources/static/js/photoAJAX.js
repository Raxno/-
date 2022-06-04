$(document).ready(function () {
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/photos', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			fillContentDiv(JSON.parse(xhr.responseText), "content-publications");
    		}
    	}
    }
    xhr.send("n=" + nick);
});

function fillContentDiv(jsonArr, divToFill){
	let contentDiv = document.getElementById(divToFill);
	contentDiv.innerHTML = "";
	let postLine;
	for(let i = 0; i < jsonArr.length; i++){
		if(i%3 == 0){
	    	postLine = document.createElement("div");
	    	contentDiv.appendChild(postLine);
	    	$(postLine).attr("id", "post-line");
		}
    	let imageWrapper = document.createElement("div");
    	postLine.appendChild(imageWrapper);
    	$(imageWrapper).attr("id", "image-wrapper");
    		
    	let img = document.createElement("img");
    	imageWrapper.appendChild(img);
    	let src = "/img/" + jsonArr[i].id;
    	$(img).attr("src", "/img/" + jsonArr[i].id);
    	if(i%3 == 1){
    		$(img).attr("class", "second");
    	}
		if(i%3 == 2){
			$(img).attr("class", "third");
		}
    		
    	$(img).click(function (){
    		$("#copyButton").show();
    		openImage(src, jsonArr[i].description, jsonArr[i].date, jsonArr[i].id, jsonArr[i].accesLevel);
    	});
	}
}