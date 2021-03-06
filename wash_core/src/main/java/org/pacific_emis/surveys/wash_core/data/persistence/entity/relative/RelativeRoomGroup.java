package org.pacific_emis.surveys.wash_core.data.persistence.entity.relative;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.stream.Collectors;

import org.pacific_emis.surveys.wash_core.data.model.mutable.MutableGroup;
import org.pacific_emis.surveys.wash_core.data.persistence.entity.RoomGroup;
import org.pacific_emis.surveys.wash_core.data.persistence.entity.RoomSubGroup;

public class RelativeRoomGroup {

    @Embedded
    public RoomGroup group;

    @Relation(parentColumn = "uid", entityColumn = "group_id", entity = RoomSubGroup.class)
    public List<RelativeRoomSubGroup> subGroups;

    public MutableGroup toMutable() {
        MutableGroup mutableGroup = new MutableGroup(group);
        mutableGroup.setSubGroups(subGroups.stream().map(RelativeRoomSubGroup::toMutableStandard).collect(Collectors.toList()));
        return mutableGroup;
    }

}
