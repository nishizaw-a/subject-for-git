
$(function() {
	$("#type").change(function() {
		var value = $('#type').val();
		$.ajax({

			url: `/facilityreservation/remake/${value}`,
			type: "GET",

			dateType: "JSON"
			,
		}).done(function(data) {
			$("#facility").empty();


			var len = data.facilityFormList.length;
			for (var i = 0; i < len; i++) {
				console.log(data.facilityFormList[i].name);
				$("#facility").append(`<div class="r-col "> <a class="element"  >
				${data.facilityFormList[i].name}</a>
				<p class="element"  >定員${data.facilityFormList[i].capacity}人</p>
				</div>`);
			}



		}).fail(function(data) {
			alert('サーバーエラーが発生しました');
		});
	})
})


