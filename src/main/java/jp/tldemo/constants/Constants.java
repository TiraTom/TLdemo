package jp.tldemo.constants;

public class Constants {

	// インスタンス作成抑止用
	private Constants() {}

	public static final double COST_OVER_BORDER = 0.3;

	public static final int ROULETTE_CANDIDATE_NUMBER = 8;

	public static final String DB_ERROR_MESSAGE = "ごめんなさい。DBエラーが発生しました。";

	public static final String SAVE_SUCCESS_MESSAGE = "保存しました。";

	public static final String SERACH_ACTIVITY_FAILED_MESSAGE = "ごめんなさい。良さそうな暇つぶしが見つかりませんでした。";

	public static final String DELETE_SUCCESS_MESSAGE = "削除しました。";

	public static final String ACTIVITY_NOT_FOUND_MESSAGE = "該当する暇つぶしが見つかりませんでした。";

	public static final String ACTIVITY_SEARCH_CONDITION_INVALID = "整数値を入力してくれたら、おすすめ暇つぶしを提案します";

	public static final String VALIDATION_ERROR = "入力値が適切ではありません";

	public static final String VALIDATION_ERROR_TITLE_LENGTH = "タイトルは1~30文字の長さで入力して下さい";

	public static final String VALIDATION_ERROR_COST = "費用は0~999,999,999の範囲で入力して下さい";
}
