package org.pacific_emis.surveys.accreditation_core.data.persistence.entity.relative;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.pacific_emis.surveys.accreditation_core.data.model.mutable.MutableCategory;
import org.pacific_emis.surveys.accreditation_core.data.model.mutable.MutableObservationLogRecord;
import org.pacific_emis.surveys.accreditation_core.data.persistence.entity.RoomCategory;
import org.pacific_emis.surveys.accreditation_core.data.persistence.entity.RoomObservationLogRecord;
import org.pacific_emis.surveys.accreditation_core.data.persistence.entity.RoomStandard;
import org.pacific_emis.surveys.core.utils.CollectionUtils;

public class RelativeRoomCategory {

    @Embedded
    public RoomCategory category;

    @Relation(parentColumn = "uid", entityColumn = "category_id", entity = RoomStandard.class)
    public List<RelativeRoomStandard> standards;

    @Relation(parentColumn = "uid", entityColumn = "category_id", entity = RoomObservationLogRecord.class)
    public List<RoomObservationLogRecord> logRecords;

    public MutableCategory toMutableCategory() {
        MutableCategory mutableCategory = MutableCategory.from(category);
        mutableCategory.setStandards(standards.stream().map(RelativeRoomStandard::toMutableStandard).collect(Collectors.toList()));

        final ArrayList<MutableObservationLogRecord> records = new ArrayList<>();
        if (!CollectionUtils.isEmpty(logRecords)) {
            logRecords.forEach(it -> records.add(MutableObservationLogRecord.from(it)));
        }
        mutableCategory.setLogRecords(records);

        return mutableCategory;
    }

}
