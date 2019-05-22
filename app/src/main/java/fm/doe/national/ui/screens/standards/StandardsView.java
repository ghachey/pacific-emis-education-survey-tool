package fm.doe.national.ui.screens.standards;

import com.omegar.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.omegar.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.omegar.mvp.viewstate.strategy.StateStrategyType;

import java.util.Date;
import java.util.List;

import fm.doe.national.core.data.model.Standard;
import fm.doe.national.ui.screens.base.BaseView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface StandardsView extends BaseView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void navigateToCriteriasScreen(long categoryId, long standardId);

    void showStandards(List<Standard> standards);

    void setGlobalProgress(int completed, int total);

    void setSurveyDate(Date date);

    void setCategoryName(String schoolName);
}
