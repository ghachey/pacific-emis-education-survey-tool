package fm.doe.national.core.data.persistence.entity.relative;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import fm.doe.national.core.data.model.mutable.MutableSubCriteria;
import fm.doe.national.core.data.persistence.entity.RoomAnswer;
import fm.doe.national.core.data.persistence.entity.RoomSubCriteria;

public class RelativeRoomSubCriteria {

    @Embedded
    public RoomSubCriteria subCriteria;

    @Relation(parentColumn = "uid", entityColumn = "sub_criteria_id", entity = RoomAnswer.class)
    public List<RelativeRoomAnswer> answers;

    public MutableSubCriteria toMutableSubCriteria() {
        MutableSubCriteria mutableSubCriteria = new MutableSubCriteria(subCriteria);
        if (answers != null && !answers.isEmpty()) {
            mutableSubCriteria.setAnswer(answers.get(0).toMutableAnswer());
        }
        return mutableSubCriteria;
    }

}