package org.pacific_emis.surveys.report_core.ui.level_legend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.omega_r.libs.omegarecyclerview.BaseListAdapter;

import org.pacific_emis.surveys.report_core.R;
import org.pacific_emis.surveys.report_core.domain.ReportLevel;

import java.util.HashMap;
import java.util.Map;


public class LegendAdapter extends BaseListAdapter<ReportLevel> {

    private static final Map<ReportLevel, ColorStateList> sColorStateListMap = new HashMap<>();

    public LegendAdapter(Context context) {
        for (ReportLevel value : ReportLevel.values()) {
            sColorStateListMap.put(value, ColorStateList.valueOf(context.getColor(value.getColorRes())));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(parent);
    }

    class ItemViewHolder extends ViewHolder {

        TextView nameTextView;
        TextView descriptionTextView;

        public ItemViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_level_legend);
            bindViews();
        }

        private void bindViews() {
            nameTextView = findViewById(R.id.textview_name);
            descriptionTextView = findViewById(R.id.textview_description);
        }

        @Override
        protected void onBind(ReportLevel item) {
            nameTextView.setCompoundDrawableTintList(sColorStateListMap.get(item));
            nameTextView.setText(buildLevelName(item));
            descriptionTextView.setText(buildLevelDescription(item));
        }

        private String buildLevelName(ReportLevel item) {
            StringBuilder stringBuilder = new StringBuilder()
                    .append(item.getName().getString(getContext()))
                    .append(" (");
            stringBuilder
                    .append(convertMinValueToUi(item.getMinValue()))
                    .append("-")
                    .append(Math.round(item.getMaxValue()))
                    .append("%")
                    .append(")");
            return stringBuilder.toString();
        }

        private int convertMinValueToUi(float minValue) {
            int minValueAsInt = (int) minValue;
            return minValueAsInt > 0 ? minValueAsInt + 1 : 0;
        }

        private String buildLevelDescription(ReportLevel item) {
            return item.getMeaning().getString(getContext());
            // This one is not needed (but maybe will need it later)
            // + " (" + item.getAwards().getString(getContext()) + ")";
        }
    }
}
