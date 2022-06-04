$(document).ready(function () {
    $(".albums").hide();
	$(".one-album").hide();
	$("#one-album-tab").hide();
	$(".tab").on("click", function(){
		$(".tab").removeClass("active");
		$(this).addClass("active");
		if($(this).index() == 0){
			$(".albums").hide();
			$(".one-album").hide();
			$(".publications").show();
		}
		if($(this).index() == 1){
			$(".publications").hide();
			$(".one-album").hide();
			$(".albums").show();
		}
		if($(this).index() == 2){
			$(".publications").hide();
			$(".albums").hide();
			$(".one-album").show();
		}
	});
});