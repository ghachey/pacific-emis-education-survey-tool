package org.pacific_emis.surveys.offline_sync.ui.surveys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.omega_r.libs.omegarecyclerview.BaseListAdapter;
import com.omegar.mvp.presenter.InjectPresenter;
import com.omegar.mvp.presenter.ProvidePresenter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.pacific_emis.surveys.core.data.model.Survey;
import org.pacific_emis.surveys.core.ui.screens.base.BaseActivity;
import org.pacific_emis.surveys.offline_sync.R;
import org.pacific_emis.surveys.offline_sync.di.OfflineSyncComponentInjector;
import org.pacific_emis.surveys.offline_sync.ui.progress.ProgressActivity;

public class SyncSurveysActivity extends BaseActivity implements SyncSurveysView, BaseListAdapter.OnItemClickListener<Survey>, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private final SyncSurveysAdapter adapter = new SyncSurveysAdapter(this);

    @InjectPresenter
    SyncSurveysPresenter presenter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button buttonNext;
    private View emptyView;

    public static Intent createIntent(Context parentContext) {
        return new Intent(parentContext, SyncSurveysActivity.class);
    }

    @ProvidePresenter
    SyncSurveysPresenter providePresenter() {
        return new SyncSurveysPresenter(OfflineSyncComponentInjector.getComponent(getApplication()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_select_survey);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(this);
        emptyView = findViewById(R.id.textview_empty_surveys);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sync_surveys;
    }

    @Override
    public void setSurveys(List<Survey> surveys) {
        adapter.setItems(surveys);
    }

    @Override
    public void onItemClick(Survey item) {
        presenter.onSurveyPressed(item);
    }

    @Override
    public void setListLoadingVisible(boolean visible) {
        swipeRefreshLayout.setRefreshing(visible);
    }

    @Override
    public void close() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonNext) {
            presenter.onNextPressed();
        }
    }

    @Override
    public void setNextEnabled(boolean enabled) {
        buttonNext.setEnabled(enabled);
    }

    @Override
    public void navigateToProgress() {
        startActivity(ProgressActivity.createIntent(this));
    }

    @Override
    public void setEmptyStateEnabled(boolean enabled) {
        emptyView.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

}
