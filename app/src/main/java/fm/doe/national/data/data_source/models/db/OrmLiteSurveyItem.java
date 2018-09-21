package fm.doe.national.data.data_source.models.db;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DatabaseTable
public class OrmLiteSurveyItem {

    public interface Column {
        String ID = "id";
        String NAME = "name";
        String TYPE = "type";
        String PARENT = "parent";
        String SURVEY = "survey";
        String CHILDREN = "children";
        String ICON = "icon";
    }

    public enum Type {
        CATEGORY,
        STANDARD,
        CRITERIA,
        SUBCRITERIA
    }

    @DatabaseField(generatedId = true, columnName = Column.ID)
    protected long id;

    @DatabaseField(columnName = Column.NAME)
    protected String name;

    @DatabaseField(columnName = Column.TYPE)
    protected Type type;

    @Nullable
    @DatabaseField(columnName = Column.ICON, canBeNull = true)
    protected Integer icon;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = Column.PARENT)
    protected OrmLiteSurveyItem parentItem;

    @ForeignCollectionField(eager = true, columnName = Column.CHILDREN)
    protected Collection<OrmLiteSurveyItem> childrenItems;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = Column.SURVEY)
    protected OrmLiteSurvey survey;

    public OrmLiteSurveyItem() {
    }

    public OrmLiteSurveyItem(String name, Type type, @Nullable Integer icon, OrmLiteSurvey survey, OrmLiteSurveyItem parentItem) {
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.survey = survey;
        this.parentItem = parentItem;
        this.childrenItems = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public OrmLiteSurveyItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(OrmLiteSurveyItem parentItem) {
        this.parentItem = parentItem;
    }

    public Collection<OrmLiteSurveyItem> getChildrenItems() {
        return childrenItems;
    }

    public void addChild(OrmLiteSurveyItem surveyItem) {
        surveyItem.setParentItem(this);
        childrenItems.add(surveyItem);
    }

    public void addChildren(List<OrmLiteSurveyItem> surveyItems) {
        for (OrmLiteSurveyItem surveyItem : surveyItems) {
            addChild(surveyItem);
        }
    }

    @Nullable
    public Integer getIcon() {
        return icon;
    }

    public OrmLiteSurvey getSurvey() {
        return survey == null ? parentItem.getSurvey() : survey;
    }

    public static int getTotalChildrenCount(OrmLiteSurveyItem surveyItem) {
        if (surveyItem.childrenItems.isEmpty()) {
            return 1;
        }

        int childSum = 0;
        for (OrmLiteSurveyItem item : surveyItem.childrenItems) {
            childSum += getTotalChildrenCount(item);
        }

        return childSum;
    }
}
