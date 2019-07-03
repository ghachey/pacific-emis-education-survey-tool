package fm.doe.national.ui.screens.menu;

import androidx.annotation.Nullable;

import com.omega_r.libs.omegatypes.Image;
import com.omega_r.libs.omegatypes.Text;
import com.omegar.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.omegar.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.omegar.mvp.viewstate.strategy.StateStrategyType;

import fm.doe.national.core.preferences.entities.SurveyType;
import fm.doe.national.offline_sync.ui.base.BaseBluetoothView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainMenuView extends BaseBluetoothView {

    void setIcon(Image icon);

    void setTitle(Text title);

    void setCurrentSurveyType(@Nullable SurveyType surveyType);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void navigateToSurveys();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void navigateToSettings();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void navigateToCredits();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMergeProgress();
}
