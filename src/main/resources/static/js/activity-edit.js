/**
 *
 */


// 削除して良いかの確認ウィンドウ
function deleteConfirm(){
	if(window.confirm("この暇つぶしを削除してよろしいですか？")){
		document.deleteForm.submit();
	}
}