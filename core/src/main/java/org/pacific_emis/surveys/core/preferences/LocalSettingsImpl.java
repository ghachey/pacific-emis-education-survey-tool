package org.pacific_emis.surveys.core.preferences;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.omega_r.libs.omegatypes.Text;
import com.omega_r.libs.omegatypes.image.Image;
import com.omega_r.libs.omegatypes.image.ResourceImage;
import com.omega_r.libs.omegatypes.image.UrlImage;

import org.pacific_emis.surveys.core.BuildConfig;
import org.pacific_emis.surveys.core.R;
import org.pacific_emis.surveys.core.data.exceptions.NotImplementedException;
import org.pacific_emis.surveys.core.preferences.entities.AppRegion;
import org.pacific_emis.surveys.core.preferences.entities.OperatingMode;
import org.pacific_emis.surveys.core.preferences.entities.SurveyType;

public class LocalSettingsImpl implements LocalSettings {

    private static final String PREF_KEY_APP_REGION = "PREF_KEY_APP_REGION";
    private static final AppRegion DEFAULT_APP_REGION = AppRegion.FSM;
    private static final int NO_INT_VALUE = -1;

    private static final String PREF_KEY_SURVEY_TYPE = "PREF_KEY_SURVEY_TYPE";
    private static final int NO_SURVEY_TYPE_VALUE = -1;
    private static final SurveyType DEFAULT_SURVEY_TYPE = SurveyType.SCHOOL_ACCREDITATION;

    private static final String PREF_KEY_LOGO_PATH = "PREF_KEY_LOGO_PATH";
    private static final String PREF_KEY_APP_NAME = "PREF_KEY_APP_NAME";
    private static final String PREF_KEY_CONTACT_NAME = "PREF_KEY_CONTACT_NAME";
    private static final String PREF_KEY_MASTER_PASSWORD = "PREF_KEY_MASTER_PASSWORD";

    private static final String PREF_KEY_OPERATING_MODE = "PREF_KEY_OPERATING_MODE";
    private static final OperatingMode DEFAULT_OPERATING_MODE = OperatingMode.DEV;

    private static final Image sDefaultIconFsm = new ResourceImage(R.drawable.ic_fsm);
    private static final Image sDefaultIconRmi = new ResourceImage(R.drawable.ic_rmi);

    private static final String PREF_KEY_EXCEL_EXPORT = "PREF_KEY_EXCEL_EXPORT";
    private static final String PREF_KEY_PROD_CERT = "PREF_KEY_PROD_CERT";

    private final SharedPreferences sharedPreferences;

    public LocalSettingsImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public AppRegion getAppRegion() {
        AppRegion savedAppRegion = getSavedAppRegion();
        return savedAppRegion != null ? savedAppRegion : DEFAULT_APP_REGION;
    }

    @Override
    public boolean isAppRegionSaved() {
        return getSavedAppRegion() != null;
    }

    @Nullable
    private AppRegion getSavedAppRegion() {
        return AppRegion.createFromValue(sharedPreferences.getInt(PREF_KEY_APP_REGION, NO_INT_VALUE));
    }

    @Override
    public void setAppRegion(AppRegion appRegion) {
        sharedPreferences.edit().putInt(PREF_KEY_APP_REGION, appRegion.getValue()).apply();
    }

    @Override
    public Image getLogo() {
        String logoPath = getLogoPath();

        if (logoPath != null) {
            return new UrlImage(logoPath);
        }

        switch (getAppRegion()) {
            case FSM:
                return sDefaultIconFsm;
            case RMI:
                return sDefaultIconRmi;
            default:
                throw new NotImplementedException();
        }
    }

    @Nullable
    @Override
    public String getLogoPath() {
        return sharedPreferences.getString(PREF_KEY_LOGO_PATH, null);
    }

    @Override
    public boolean isLogoSaved() {
        return getLogoPath() != null;
    }

    @Override
    public void setLogoPath(@Nullable String path) {
        sharedPreferences.edit().putString(PREF_KEY_LOGO_PATH, path).apply();
    }

    @Nullable
    @Override
    public SurveyType getSurveyType() {
        return SurveyType.createFromValue(sharedPreferences.getInt(PREF_KEY_SURVEY_TYPE, NO_SURVEY_TYPE_VALUE));
    }

    @NonNull
    @Override
    public SurveyType getSurveyTypeOrDefault() {
        SurveyType savedSurveyType = getSurveyType();
        return savedSurveyType == null ? DEFAULT_SURVEY_TYPE : savedSurveyType;
    }

    @Override
    public void setSurveyType(SurveyType surveyType) {
        sharedPreferences.edit().putInt(PREF_KEY_SURVEY_TYPE, surveyType.getValue()).apply();
    }

    @Override
    public String getMasterPassword() {
        String savedPassword = getSavedMasterPassword();
        return savedPassword == null ? getFactoryPassword() : savedPassword;
    }

    @Nullable
    private String getSavedMasterPassword() {
        return sharedPreferences.getString(PREF_KEY_MASTER_PASSWORD, null);
    }

    @Override
    public boolean isMasterPasswordSaved() {
        return getSavedMasterPassword() != null;
    }

    @Override
    public void setMasterPassword(String password) {
        sharedPreferences.edit().putString(PREF_KEY_MASTER_PASSWORD, password).apply();
    }

    @Override
    public String getFactoryPassword() {
        return BuildConfig.FACTORY_PASSWORD;
    }

    @Override
    public Text getAppName() {
        String savedName = getSavedAppName();
        if (savedName == null) {
            return Text.from(R.string.app_name);
        }
        return Text.from(savedName);
    }

    @Nullable
    private String getSavedAppName() {
        return sharedPreferences.getString(PREF_KEY_APP_NAME, null);
    }

    @Override
    public boolean isAppNameSaved() {
        return getSavedAppName() != null;
    }

    @Override
    public void setAppName(String name) {
        sharedPreferences.edit().putString(PREF_KEY_APP_NAME, name).apply();
    }

    @Override
    public String getContactName() {
        String savedContactName = getSavedContactName();
        return savedContactName != null ? savedContactName : "";
    }

    @Nullable
    private String getSavedContactName() {
        return sharedPreferences.getString(PREF_KEY_CONTACT_NAME, null);
    }

    @Override
    public boolean isContactNameSaved() {
        return getSavedContactName() != null;
    }

    @Override
    public void setContactName(String name) {
        sharedPreferences.edit().putString(PREF_KEY_CONTACT_NAME, name).apply();
    }

    @Override
    public OperatingMode getOperatingMode() {
        return OperatingMode.createFromValue(sharedPreferences.getInt(PREF_KEY_OPERATING_MODE, DEFAULT_OPERATING_MODE.getValue()));
    }

    @Override
    public boolean isOperatingModeSaved() {
        return sharedPreferences.getInt(PREF_KEY_OPERATING_MODE, NO_INT_VALUE) != NO_INT_VALUE;
    }

    @Override
    public void setOperatingMode(OperatingMode mode) {
        sharedPreferences.edit().putInt(PREF_KEY_OPERATING_MODE, mode.getValue()).apply();
    }

    @Override
    public boolean isExportToExcelEnabled() {
        return sharedPreferences.getBoolean(PREF_KEY_EXCEL_EXPORT, false);
    }

    @Override
    public void setExportToExcelEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(PREF_KEY_EXCEL_EXPORT, enabled).apply();
    }

    @Override
    public void setProdCert(String cert) {
        sharedPreferences.edit().putString(PREF_KEY_PROD_CERT, cert).apply();
    }

    @Nullable
    @Override
    public String getProdCert() {
        return sharedPreferences.getString(PREF_KEY_PROD_CERT, null);
    }
}
