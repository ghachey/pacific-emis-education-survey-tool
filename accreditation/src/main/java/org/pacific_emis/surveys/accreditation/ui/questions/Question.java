package org.pacific_emis.surveys.accreditation.ui.questions;

import androidx.annotation.Nullable;

import org.pacific_emis.surveys.accreditation_core.data.model.Criteria;
import org.pacific_emis.surveys.accreditation_core.data.model.SubCriteria;
import org.pacific_emis.surveys.core.data.model.Answerable;
import org.pacific_emis.surveys.core.data.model.BaseAnswer;


public class Question implements Answerable {

    @Nullable
    private SubCriteria subCriteria;
    private Criteria criteria;

    public Question(Criteria criteria, @Nullable SubCriteria subCriteria) {
        this.criteria = criteria;
        this.subCriteria = subCriteria;
    }

    public Question(@Nullable Criteria criteria) {
        this.criteria = criteria;
    }

    public boolean isCriteriaOnly() {
        return subCriteria == null;
    }

    @Nullable
    public SubCriteria getSubCriteria() {
        return subCriteria;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    @Nullable
    @Override
    public BaseAnswer getAnswer() {
        if (subCriteria == null) {
            return null;
        }

        return subCriteria.getAnswer();
    }
}
