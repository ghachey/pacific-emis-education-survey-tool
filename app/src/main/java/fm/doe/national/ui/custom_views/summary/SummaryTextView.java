package fm.doe.national.ui.custom_views.summary;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatTextView;

import fm.doe.national.R;
import fm.doe.national.app_support.utils.Constants;

public class SummaryTextView extends AppCompatTextView {

    public enum AnswerType {
        POSITIVE,
        NEGATIVE,
        MAX
    }

    private float positiveAnswers;
    private float negativeAnswers;
    private float maxAnswers;

    public SummaryTextView(Context context) {
        super(context);
        updateColor();
    }

    public SummaryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateColor();
    }

    public SummaryTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        updateColor();
    }

    public void setAnswersCount(int answersCount, AnswerType type) throws RuntimeException {
        checkForNegativeCount(answersCount);
        switch (type) {
            case NEGATIVE:
                negativeAnswers = answersCount;
                break;
            case POSITIVE:
                positiveAnswers = answersCount;
                break;
            case MAX:
                maxAnswers = answersCount;
                break;
        }
        updateColor();
    }

    private void checkForNegativeCount(int count) throws RuntimeException {
        if (count < 0) {
            throw new RuntimeException(Constants.Errors.NEGATIVE_ANSWERS_COUNT);
        }
    }

    private void updateColor() {
        float positiveAnswersPercent = positiveAnswers / maxAnswers * 100;

        setBackgroundResource(getColorResource(positiveAnswersPercent));

        invalidate();
    }

    @ColorRes
    private int getColorResource(float positiveAnswersPercent) {
        if (maxAnswers == 0) {
            return R.color.gray;
        }

        if (positiveAnswers + negativeAnswers < maxAnswers) {
            return R.color.pink;
        }

        if (positiveAnswersPercent <= 25) {
            return R.color.red;
        } else if (positiveAnswersPercent < 75) {
            return R.color.yellow;
        } else if (positiveAnswersPercent < 100) {
            return R.color.light_mint;
        } else {
            return R.color.dark_mint;
        }
    }
}
