package org.pacific_emis.surveys.wash_core.data.model;

import android.content.Context;

import com.omega_r.libs.omegatypes.Text;

import org.pacific_emis.surveys.wash_core.R;

public enum BinaryAnswerState {
    YES(Text.from(R.string.answer_yes)),
    NO(Text.from(R.string.answer_no));

    private final Text text;

    public static BinaryAnswerState createFromText(Context context, Text text) {
        for (BinaryAnswerState state : BinaryAnswerState.values()) {
            if (state.text.getString(context).equals(text.getString(context))) {
                return state;
            }
        }
        throw new IllegalStateException();
    }

    BinaryAnswerState(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }
}
