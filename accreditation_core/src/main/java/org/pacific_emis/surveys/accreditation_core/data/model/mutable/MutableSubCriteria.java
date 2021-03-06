package org.pacific_emis.surveys.accreditation_core.data.model.mutable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.pacific_emis.surveys.accreditation_core.data.model.Answer;
import org.pacific_emis.surveys.accreditation_core.data.model.SubCriteria;
import org.pacific_emis.surveys.core.data.model.ConflictResolveStrategy;
import org.pacific_emis.surveys.core.data.model.mutable.BaseMutableEntity;

public class MutableSubCriteria extends BaseMutableEntity implements SubCriteria {

    private String suffix;
    private String title;
    private String interviewQuestions;
    private String hint;
    private MutableAnswer answer;

    public static MutableSubCriteria toMutable(@NonNull SubCriteria subCriteria) {
        if (subCriteria instanceof MutableSubCriteria) {
            return (MutableSubCriteria) subCriteria;
        }
        return new MutableSubCriteria(subCriteria);
    }

    public MutableSubCriteria() {
    }

    public MutableSubCriteria(@NonNull SubCriteria otherSubCriteria) {
        this.id = otherSubCriteria.getId();
        this.suffix = otherSubCriteria.getSuffix();
        this.title = otherSubCriteria.getTitle();
        this.interviewQuestions = otherSubCriteria.getInterviewQuestions();
        this.hint = otherSubCriteria.getHint();
        Answer answer = otherSubCriteria.getAnswer();
        if (answer != null) {
            this.answer = new MutableAnswer(answer);
        }
    }

    @NonNull
    @Override
    public String getSuffix() {
        return suffix;
    }

    @NonNull
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getInterviewQuestions() {
        return interviewQuestions;
    }

    @Nullable
    @Override
    public String getHint() {
        return hint;
    }

    @NonNull
    @Override
    public MutableAnswer getAnswer() {
        return answer;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInterviewQuestions(String interviewQuestions) {
        this.interviewQuestions = interviewQuestions;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setAnswer(MutableAnswer answer) {
        this.answer = answer;
    }

    @Nullable
    public MutableAnswer merge(SubCriteria other, ConflictResolveStrategy strategy) {
        Answer externalAnswer = other.getAnswer();

        if (externalAnswer == null) {
            return null;
        }

        return answer.merge(externalAnswer, strategy);
    }
}
