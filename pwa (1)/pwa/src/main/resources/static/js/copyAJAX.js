$(document).ready(function () {
	$("#deleteButton").click(function(){
        deletePhoto(Number.parseInt(document.getElementById("photo-id").innerHTML));
        $(this).fadeOut(100);
    });
});

function copyPhotoInAlbum(photoId, albumId) {
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/albums/copy-in', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    xhr.send("id=" + photoId + "&a=" + albumId);
}

function deletePhoto(photoId) {
    var token = document.head.querySelector("meta[name='_csrf']").content;
    var header = document.head.querySelector("meta[name='_csrf_header']").content;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/ajax/photos/delete', true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader(header, token);
    xhr.send("id=" + photoId);
}