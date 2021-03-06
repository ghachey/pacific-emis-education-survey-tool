package org.pacific_emis.surveys.accreditation_core.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import org.pacific_emis.surveys.core.data.model.IdentifiedObject;
import org.pacific_emis.surveys.core.data.model.Progressable;

public interface Category extends Progressable, IdentifiedObject {

    @NonNull
    String getTitle();

    @Nullable
    List<? extends Standard> getStandards();

    EvaluationForm getEvaluationForm();

    @Nullable
    ObservationInfo getObservationInfo();

    @Nullable
    List<? extends ObservationLogRecord> getLogRecords();

}
