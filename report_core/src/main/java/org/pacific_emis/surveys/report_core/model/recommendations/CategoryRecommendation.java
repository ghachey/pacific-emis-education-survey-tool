package org.pacific_emis.surveys.report_core.model.recommendations;

import com.omega_r.libs.omegatypes.Text;

import org.pacific_emis.surveys.accreditation_core.data.model.Category;
import org.pacific_emis.surveys.report_core.R;

public class CategoryRecommendation extends Recommendation<Category> {
    public CategoryRecommendation(Category object) {
        super(object, Text.from(R.string.format_recommendation_category, object.getTitle()));
    }
}
