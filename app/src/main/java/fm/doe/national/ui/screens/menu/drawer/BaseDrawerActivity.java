package fm.doe.national.ui.screens.menu.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fm.doe.national.R;
import fm.doe.national.ui.screens.menu.base.MenuActivity;
import fm.doe.national.ui.screens.settings.SettingsActivity;

public abstract class BaseDrawerActivity extends MenuActivity implements
        BaseDrawerView,
        DrawerLayout.DrawerListener,
        View.OnClickListener {

    protected DrawerLayout drawerLayout;

    @BindView(R.id.textview_education_survey_tool)
    TextView educationSurveyToolTextView;

    @BindView(R.id.textview_settings)
    TextView settingsTextView;

    private ActionBarDrawerToggle drawerToggle;

    protected abstract BaseDrawerPresenter getPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        educationSurveyToolTextView.setOnClickListener(this);
        settingsTextView.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_drawer);
        drawerLayout = findViewById(R.id.drawerlayout);
        View contentView = getLayoutInflater().inflate(layoutResID, drawerLayout, false);
        drawerLayout.addView(contentView, 0);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void hideMenu() {
        drawerLayout.closeDrawers();
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

    @OnClick(R.id.textview_education_survey_tool)
    public void onEducationSurveyToolClicked() {
        getPresenter().onEducationSurveyToolClicked();
    }

    @OnClick(R.id.textview_settings)
    public void onSettingClicked() {
        getPresenter().onSettingClicked();
    }

    @Override
    public void navigateToEducationSurveyToolScreen() {
        //TODO added logic afters creating Education Survey Tool screen
    }

    @Override
    public void navigateToSettingsScreen() {
        startActivity(SettingsActivity.createIntent(this));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_education_survey_tool:
                getPresenter().onEducationSurveyToolClicked();
                break;
            case R.id.textview_settings:
                getPresenter().onSettingClicked();
                break;
        }
    }
}