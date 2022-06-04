$(document).ready(function () {
	$("#new-album").hide();
	$("#addAlbumButton").click(function() {
		$("#new-album").fadeIn(100);
	});
	
	$("#createAlbumButton").click(function() {
		createAlbum($("#newAlbumName").val(), getAccesLevel());
		$("#new-album").fadeOut(100)
	});
	
	function getAccesLevel() {
		let radioList = $("#new-album").find(":radio");
		for(let i = 0; i < radioList.length; i++){
			if($(radioList[i]).prop("checked"))
				return i;
		}
	}
});