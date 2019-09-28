/**
 *
 */

// 暇つぶし提案をルーレット風にやる
function roulette(){

	// ルーレット内で表示させる文字リストの取得
	var candidateStr = $('#candidateList').attr('value');
	var candidateList = candidateStr.slice(1, candidateStr.length -1).split(',')

	// 予算欄は、ルーレット中は空にしておく
	$('#suggestedActivityCost').text("");

	$("#resultDiv")[0].style.visibility = "visible";

	index = 0;

	rouletteObj = setInterval(function() {
			$('#suggestedActivityName').text(candidateList[index]);
			if (index < candidateList.length - 1){
				index++;
			} else {
				index = 0;
			}
      }, 80);

	setTimeout(function(){
		$.ajax({
			async: true,
			url: '/suggestActivityAjax',
			type: 'POST',
			data: { budget : $('#budgetInput').val() },
			dataType:"json"

		}).done(function(result,textStatus,jqXHR) {

			clearInterval(rouletteObj);
			$('#suggestedActivityName').text(result.title);
			$('#suggestedActivityCost').text(result.cost);

		}).fail(function(jqXHR, textStatus, errorThrown){
			clearInterval(rouletteObj);
			$('#suggestedActivityName').text('処理に失敗しました');
			$('#suggestedActivityCost').text('0');
		});

	}, 2000)

}

