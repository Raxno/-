$(document).ready(function () {
	$("#commentButton").click(function() {
		addComment(Number.parseInt(document.getElementById("photo-id").innerHTML), $("#commentInput").val());
		$("#commentInput").val("");
	});
});


function getCommentsByPhoto(photoId) {
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/comments', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4)
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0)
    			fillComments(JSON.parse(xhr.responseText));
    		else{
	    		let commentNumber = document.getElementById("commentNumber");
				commentNumber.innerHTML = "0";
				let commentsPost = document.getElementById("comments-post");
				commentsPost.innerHTML = "";
    		}
    	}
    }
    xhr.send("id=" + photoId);
}

function fillComments(jsonArr){
	let commentNumber = document.getElementById("commentNumber");
	commentNumber.innerHTML = "";
	let number = document.createTextNode(jsonArr.length);
	commentNumber.appendChild(number);
	let commentsPost = document.getElementById("comments-post");
	commentsPost.innerHTML = "";
   	for(let i = 0; i < jsonArr.length; i++){
   		let comment = document.createElement("div");
   		commentsPost.appendChild(comment);
   		$(comment).attr("id", "comment");
   		
   		let commentAvatar = document.createElement("div");
   		comment.appendChild(commentAvatar);
   		$(commentAvatar).attr("id", "avatar-comment");
   		let img = document.createElement("img");
   		commentAvatar.appendChild(img);
   		$(img).attr("src", "/images/avatar.png");
   		$(img).attr("width", "50px");
   		$(img).attr("height", "50px");
   		
   		let nickAndDate = document.createElement("div");
   		comment.appendChild(nickAndDate);
   		$(nickAndDate).attr("id", "nickDate-comment");
   		
   		let postNick = document.createElement("a");
   		nickAndDate.appendChild(postNick);
   		$(postNick).attr("class", "nick-post");
   		let nickname = document.createTextNode(jsonArr[i].authorNickname);
   		postNick.appendChild(nickname);
   		
   		let postDate = document.createElement("span");
   		nickAndDate.appendChild(postDate);
   		$(postDate).attr("class", "date-post");
   		let date = document.createTextNode(jsonArr[i].date);
   		postDate.appendChild(date);

   		let commentText = document.createElement("div");
   		comment.appendChild(commentText);
   		$(commentText).attr("id", "comment-text");
   		
   		let text = document.createTextNode(jsonArr[i].text);
   		commentText.appendChild(text);
   	}
}

function addComment(photoId, text){
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/comments/add', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4)
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0)
    			fillComments(JSON.parse(xhr.responseText));
    		else{
	    		let commentNumber = document.getElementById("commentNumber");
				commentNumber.innerHTML = "0";
				let commentsPost = document.getElementById("comments-post");
				commentsPost.innerHTML = "";
    		}
    	}
    }
    xhr.send("id=" + photoId + "&t=" + text);
}