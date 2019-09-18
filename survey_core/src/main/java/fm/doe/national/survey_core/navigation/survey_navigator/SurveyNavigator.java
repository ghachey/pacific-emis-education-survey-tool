package fm.doe.national.survey_core.navigation.survey_navigator;

import java.util.List;

import fm.doe.national.core.data.model.IdentifiedObject;
import fm.doe.national.survey_core.navigation.BuildableNavigationItem;
import fm.doe.national.survey_core.navigation.NavigationItem;
import fm.doe.national.survey_core.ui.survey.SurveyView;

public interface SurveyNavigator {

    void select(BuildableNavigationItem item);

    void select(IdentifiedObject identifiedObject);

    void selectPrevious();

    void selectNext();

    void setViewState(SurveyView surveyView);

    BuildableNavigationItem getCurrentItem();

    void setNavigation(List<NavigationItem> items);

    void close();

}