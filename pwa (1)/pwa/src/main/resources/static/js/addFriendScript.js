$(document).ready(function () {
	let flag = 0;
	$(".add-friend").click(function() {
		$(".add-friend-plus").queue('fx', []);
		if(flag == 0){
			$(".add-friend-plus").animate(
				{rotate: 45},{
					duration: 300,
					step: function(now) {
						$(this).css({ transform: "rotate(" + now + "deg)" });
					}
				}
			);
			$(".add-friend-text").text("Удалить из друзей");
			flag = 1;
		}
		else{
			$(".add-friend-plus").animate(
				{rotate: 0},{
					duration: 300,
					step: function(now) {
						$(this).css({ transform: "rotate(" + now + "deg)" });
					}
				}
			);
			$(".add-friend-text").text("Добавить в друзья");
			flag = 0;
		}
	});
});