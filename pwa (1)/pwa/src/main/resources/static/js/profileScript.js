$(document).ready(function () {
    /*plus drive*/
    $(".add-friend-text").hide();	
	$(".add-friend").mouseenter(function(){
		$(".add-friend-text").delay(500).fadeIn(0);
	})
	$(".add-friend").mouseleave(function(){
		$(".add-friend-text").fadeOut(0);
	})
    /*////////////////////*/
    /*photo drive*/
    $(".add-photo-text").hide();	
	$(".add-photo").mouseenter(function(){
		$(".add-photo-text").delay(500).fadeIn(0);
	})
	$(".add-photo").mouseleave(function(){
		$(".add-photo-text").fadeOut(0);
	})
	
	profileCheck();
});