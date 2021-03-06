package org.pacific_emis.surveys.wash_core.data.serialization.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.stream.Collectors;

import org.pacific_emis.surveys.core.data.model.Progress;
import org.pacific_emis.surveys.wash_core.data.model.Group;
import org.pacific_emis.surveys.wash_core.data.model.SubGroup;
import org.pacific_emis.surveys.core.data.model.BaseSerializableIdentifiedObject;

@Root(name = "group")
public class SerializableGroup extends BaseSerializableIdentifiedObject implements Group {

    @Element(name = "id")
    String prefix;

    @Element(name = "name")
    String title;

    @ElementList(inline = true)
    List<SerializableSubGroup> subGroups;

    public SerializableGroup(Group other) {
        this.prefix = other.getPrefix();
        this.title = other.getTitle();

        if (other.getSubGroups() != null) {
            this.subGroups = other.getSubGroups().stream().map(SerializableSubGroup::new).collect(Collectors.toList());
        }
    }

    public SerializableGroup() {
        // required for serialization
    }

    @NonNull
    @Override
    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String getPrefix() {
        return prefix;
    }

    @Nullable
    @Override
    public List<? extends SubGroup> getSubGroups() {
        return subGroups;
    }

    @NonNull
    @Override
    public Progress getProgress() {
        throw new UnsupportedOperationException();
    }
}
