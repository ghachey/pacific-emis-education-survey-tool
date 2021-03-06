package org.pacific_emis.surveys.ui.dialogs.merge_progress;

import com.omega_r.libs.omegatypes.Text;
import com.omegar.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.omegar.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.omegar.mvp.viewstate.strategy.StateStrategyType;

import org.pacific_emis.surveys.core.ui.screens.base.BaseView;

public interface MergeProgressView extends BaseView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void close();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setProgress(int progress);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDescription(Text text);

}
