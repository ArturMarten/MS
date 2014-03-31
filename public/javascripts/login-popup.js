$(document).ready(function() {
    $(".popup_button").click(function() {
		$(".popup").show();
		$(".sulge_popup").click(function() {
			$(".popup").hide();
		});
    });
});
