package fm.doe.national.ui.screens.shool_accreditation;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import fm.doe.national.mock.MockSchool;
import fm.doe.national.ui.screens.menu.base.MenuDrawerView;

/**
 * Created by Alexander Chibirev on 8/10/2018.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
interface SchoolAccreditationView extends MenuDrawerView {

    void bindSchools(List<MockSchool> schools);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showChooseCategoryScreen(MockSchool school);

}
