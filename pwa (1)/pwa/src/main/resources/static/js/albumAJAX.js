$(document).ready(function () {	
	var nick = document.getElementById("nick").innerHTML;
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/albums', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			fillAlbums(JSON.parse(xhr.responseText));
    		}
    		else{
    			let albums = document.getElementById("content-album");
    	    	albums.innerHTML = "";
    		}
    	}
    }
    xhr.send("n=" + nick);
    
    let flag1 = 0;
   	var xhr1 = new XMLHttpRequest();
   	xhr1.open('POST', '/ajax/my-profile', true);
    xhr1.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr1.setRequestHeader(header, token);
       
    xhr1.onreadystatechange = function() {
   	if (xhr1.readyState != 4) 
      		return;
      	if (xhr1.status != 200) {
    		alert(xhr1.status + ': ' + xhr1.statusText);
     	} else {
    		if(xhr1.responseText != 0){
    			let attrs = JSON.parse(xhr1.responseText);
				if(!attrs[0].owner && attrs[0].role == "USER")
					flag1 = 1;
     		}
     	}
    }
    xhr1.send("n=" + nick);   

    function deleteAlbum(id) {
    	var token = document.head.querySelector("meta[name='_csrf']").content;
    	var header = document.head.querySelector("meta[name='_csrf_header']").content;
    	var xhr = new XMLHttpRequest();
    	xhr.open('POST', '/ajax/albums/delete', true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.setRequestHeader(header, token);
        
        xhr.send("id=" + id);
    }
    
    function fillAlbums(jsonArr){
    	let albums = document.getElementById("content-album");
    	albums.innerHTML = "";
    	let publications = document.getElementById("content-album");
    	let postLine;
    	for(let i = 0; i < jsonArr.length; i++){
    		if(i%3 == 0){
    	    	postLine = document.createElement("div");
    			publications.appendChild(postLine);
    	    	$(postLine).attr("id", "post-line");
    		}
	    	let albumWrapper = document.createElement("div");
	    	postLine.appendChild(albumWrapper);
	    	$(albumWrapper).attr("class", "album-wrapper");
	    	$(albumWrapper).attr("id", "album-block");
	    	if(i%3 == 1){
	    		$(albumWrapper).css("margin", "0 20px");
	    	}

			let albumNameDiv = document.createElement("div");
			albumWrapper.appendChild(albumNameDiv);
			$(albumNameDiv).attr("id", "albumName-wrapper");

	    	let albumName = document.createElement("span");
	    	$(albumName).attr("class", "album-name");

	    	let albumNameText = document.createTextNode(jsonArr[i].name)
	    	albumName.appendChild(albumNameText);
			albumNameDiv.appendChild(albumName);
			
			let flag = 0;
			if(flag1 == 0){
				let a = document.createElement("a");
		    	albumWrapper.appendChild(a);
		    	
				let crossDiv = document.createElement("div");
				a.appendChild(crossDiv);
				$(crossDiv).attr("id", "cross-wrapper");
	
				let cross = document.createElement("img");
				crossDiv.appendChild(cross);
				$(cross).attr("src", "/images/x.png");
				$(crossDiv).click(function() {
					flag = 1;
					$(albumWrapper).fadeOut(100);
					deleteAlbum(jsonArr[i].id);
				});
			}
	    		
	    	$(albumWrapper).click(function() {
	    		if(flag == 0){
		    		let oneAlbumTab = document.getElementById("one-album-tab");
		    		oneAlbumTab.innerHTML = "";
		    		let albumNameText1 = document.createTextNode(jsonArr[i].name)
		    		oneAlbumTab.appendChild(albumNameText1);
		    		
		    		var nick = document.getElementById("nick").innerHTML;
		    		xhr = new XMLHttpRequest();
		    		xhr.open('POST', '/ajax/photos/' + jsonArr[i].id, true);
		    	    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    	    xhr.setRequestHeader(header, token);
		    	    
		    	    xhr.onreadystatechange = function() {
		    	    	if (xhr.readyState != 4) 
		    	    		return;
		    	    	if (xhr.status != 200) {
		    	    		alert(xhr.status + ': ' + xhr.statusText);
		    	    	} else {
		    	    		if(xhr.responseText != 0){
		    	    			fillContentDiv(JSON.parse(xhr.responseText), "content-one-album");
		    	    		} else{
		    	    			let contentDiv = document.getElementById("content-one-album");
		    	    			contentDiv.innerHTML = "";
		    	    		}
		    	    	}
		    	    }
		    	    xhr.send();
		    		
		            $("#one-album-tab").show();
		    		$(".tab").removeClass("active");
		    		$("#one-album-tab").addClass("active");
		    		$(".publications").hide();
		    		$(".albums").hide();
		    		$(".one-album").show();
	    		}
	        });
    	}
    }
});

function albumListCopy(){
	var token = document.head.querySelector("meta[name='_csrf']").content;
	var header = document.head.querySelector("meta[name='_csrf_header']").content;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/ajax/albums/copy', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    
    xhr.onreadystatechange = function() {
    	if (xhr.readyState != 4) 
    		return;
    	if (xhr.status != 200) {
    		alert(xhr.status + ': ' + xhr.statusText);
    	} else {
    		if(xhr.responseText != 0){
    			if($("#copy-albums").length){
	    			let albums = JSON.parse(xhr.responseText);
	    			let copyAlbums = document.getElementById("copy-albums");
	    			copyAlbums.innerHTML = "";
	    			for(let i = 0; i < albums.length; i++){
	    				let a = document.createElement("a");
	    				copyAlbums.appendChild(a);
	    				$(a).attr("href", "#");
	    				let albumName = document.createTextNode(albums[i].name);
	    				a.appendChild(albumName);
	    				
	    				$(a).click(function() {
	    					copyPhotoInAlbum(Number.parseInt(document.getElementById("photo-id").innerHTML), albums[i].id);
	    					$("#copyButton").fadeOut(100);
	    				});
	    			}
    			}
    		}
    	}
    }
    xhr.send();
}