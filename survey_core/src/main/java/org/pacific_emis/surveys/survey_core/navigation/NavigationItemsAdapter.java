package org.pacific_emis.surveys.survey_core.navigation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omega_r.libs.views.OmegaTextView;

import java.util.List;

import org.pacific_emis.surveys.core.data.model.Progress;
import org.pacific_emis.surveys.core.ui.screens.base.BaseAdapter;
import org.pacific_emis.surveys.core.utils.ViewUtils;
import org.pacific_emis.surveys.survey_core.R;
import org.pacific_emis.surveys.survey_core.navigation.survey_navigator.ReportBuildableNavigationItem;

public class NavigationItemsAdapter extends BaseAdapter<NavigationItem> {

    private static final int VIEW_TYPE_SECTION = 0;
    private static final int VIEW_TYPE_QUESTION_GROUP = 1;

    private int selectedItemPosition = RecyclerView.NO_POSITION;
    private boolean isReportEnabled;

    public NavigationItemsAdapter(OnItemClickListener<NavigationItem> clickListener) {
        super(clickListener);
    }

    public void setSelectedItem(long itemId) {
        List<NavigationItem> items = getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == itemId) {
                selectItemAtPosition(i);
                return;
            }
        }
    }

    private void selectItemAtPosition(int position) {
        int previouslySelectedPosition = selectedItemPosition;
        selectedItemPosition = position;
        notifyItemChanged(position);
        notifyItemChanged(previouslySelectedPosition);
    }

    public void notifyProgressChanged(long itemId, Progress progress) {
        List<NavigationItem> items = getItems();
        for (int i = 0; i < items.size(); i++) {
            NavigationItem item = items.get(i);
            if (item instanceof ProgressablePrefixedBuildableNavigationItem) {
                ProgressablePrefixedBuildableNavigationItem castedItem = (ProgressablePrefixedBuildableNavigationItem) item;
                if (castedItem.getId() == itemId) {
                    castedItem.setProgress(progress);
                    notifyItemChanged(i);
                    return;
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        NavigationItem item = getItem(position);

        if (item instanceof BuildableNavigationItem) {
            return VIEW_TYPE_QUESTION_GROUP;
        }

        return VIEW_TYPE_SECTION;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_QUESTION_GROUP:
                return new QuestionGroupViewHolder(parent);
            case VIEW_TYPE_SECTION:
                return new HeaderViewHolder(parent);
        }
        throw new IllegalStateException();
    }

    @Override
    protected ViewHolder provideViewHolder(ViewGroup parent) {
        // unused
        return null;
    }

    public void setReportEnabled(boolean enabled) {
        isReportEnabled = enabled;
        notifyDataSetChanged();
    }

    class QuestionGroupViewHolder extends ViewHolder {

        private TextView progressTextView;
        private ProgressBar progressBar;
        private OmegaTextView titleOmegaTextView;

        QuestionGroupViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_navigation_group);
            bindViews();
        }

        private void bindViews() {
            progressTextView = findViewById(R.id.textview_progress);
            progressBar = findViewById(R.id.progressbar);
            titleOmegaTextView = findViewById(R.id.omegatextview_title);
        }

        @Override
        protected void onBind(NavigationItem item) {
            titleOmegaTextView.setText(item.getTitle());
            itemView.setActivated(isSelected());
            updatePrefix(item);
            updateProgress(item);
            itemView.setEnabled(!(item instanceof ReportBuildableNavigationItem) || isReportEnabled);
        }

        private void updatePrefix(NavigationItem item) {
            if (item instanceof PrefixedBuildableNavigationItem) {
                PrefixedBuildableNavigationItem prefixedItem = (PrefixedBuildableNavigationItem) item;
                titleOmegaTextView.setStartText(prefixedItem.getTitlePrefix());
            } else {
                titleOmegaTextView.setStartText(null);
            }
        }

        private void updateProgress(NavigationItem item) {
            if (item instanceof ProgressablePrefixedBuildableNavigationItem) {
                ProgressablePrefixedBuildableNavigationItem navigationItem = (ProgressablePrefixedBuildableNavigationItem) item;
                Progress progress = navigationItem.getProgress();
                int progressVisibility = progress.getTotal() == 0 ? View.GONE : View.VISIBLE;
                progressTextView.setVisibility(progressVisibility);
                progressBar.setVisibility(progressVisibility);
                ViewUtils.rebindProgress(progress, progressTextView, progressBar);
            } else {
                progressTextView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        }

        private boolean isSelected() {
            return getAdapterPosition() == selectedItemPosition;
        }
    }

    class HeaderViewHolder extends ViewHolder {

        private TextView titleTextView;

        HeaderViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_navigation_header);
            bindViews();
        }

        private void bindViews() {
            titleTextView = findViewById(R.id.textview_title);
        }

        @Override
        public void onClick(View v) {
            // nothing - do not handle clicks on HeaderViewHolder
        }

        @Override
        protected void onBind(NavigationItem item) {
            item.getTitle().applyTo(titleTextView, null);
        }
    }
}
