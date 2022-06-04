$(document).ready(function () {
	let url = window.location.href;
	tag = url.split("?t=");
	if(!(tag[1] === undefined))
		searchByTag(tag[1]);
	
	let rating = 0;
	let ratingMenu = $("#rating-numbers").find("> a");
	$(ratingMenu).click(function(){
		let index = $(this).index();
		rating = index + 1;
		$("#ratingImage").attr("src", "/images/" + rating + ".png");
	});
	let searchInput = document.getElementById("search");
	searchInput.addEventListener('keypress', function (e) {
	    if (e.key === 'Enter'/* && $(searchInput).val() != ""*/) {
		      document.getElementById("content-publications").innerHTML = "";
		      search($(searchInput).val(), rating, $("#dateSelector").val());
	    }
	});
});

function search(query, rating, date){
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/search', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			fillContentDiv(JSON.parse(xhr.responseText));
    		}
    	}
    }
    xhr.send("q=" + query + "&r=" + rating + "&d=" + date);
}

function searchByTag(tag){
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/search/tag', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			fillContentDiv(JSON.parse(xhr.responseText));
    		}
    	}
    }
    xhr.send("t=" + tag);
}

function fillContentDiv(jsonArr){
	let contentDiv = document.getElementById("content-publications");
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