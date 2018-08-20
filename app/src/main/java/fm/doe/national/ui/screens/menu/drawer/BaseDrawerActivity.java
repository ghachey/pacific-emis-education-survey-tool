package fm.doe.national.ui.screens.menu.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import fm.doe.national.R;
import fm.doe.national.ui.screens.base.BaseActivity;
import fm.doe.national.ui.screens.menu.base.MenuDrawerView;

public abstract class BaseDrawerActivity extends BaseActivity implements BaseDrawerView, DrawerLayout.DrawerListener {

    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.textview_education_survey_tool)
    TextView educationSurveyToolTextView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    protected void setContentView(@LayoutRes int contentLayoutResID, @LayoutRes int menuLayoutResID) {
        drawerLayout = new DrawerLayout(this);
        super.setContentView(drawerLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutInflater inflater = getLayoutInflater();

        inflater.inflate(contentLayoutResID, drawerLayout);

        View menuView = inflater.inflate(menuLayoutResID, drawerLayout, false);
        menuView.setClickable(true);


        int width = getResources().getDimensionPixelSize(R.dimen.drawer_menu_width);
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.START;
        menuView.setLayoutParams(params);

        drawerLayout.addView(menuView, params);

        initToolbar();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public void hideMenu() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void selectMenuOption(MenuDrawerView.MenuItems menuOption) {
        educationSurveyToolTextView.setSelected(menuOption == MenuDrawerView.MenuItems.EDUCATION_SURVEY_TOOL);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        //nothing
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        //nothing
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        //nothing
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        hideKeyboard();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
