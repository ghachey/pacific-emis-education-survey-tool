package org.pacific_emis.surveys.ui.screens.splash;

import android.Manifest;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.omega_r.libs.omegatypes.Text;
import com.omegar.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import org.pacific_emis.surveys.R;
import org.pacific_emis.surveys.core.ui.screens.base.BaseActivity;
import org.pacific_emis.surveys.ui.screens.menu.MainMenuActivity;
import org.pacific_emis.surveys.ui.screens.password.PasswordsActivity;
import org.pacific_emis.surveys.ui.screens.region.ChooseRegionActivity;


public class SplashActivity extends BaseActivity implements SplashView {

    @BindView(R.id.imageview_logo)
    ImageView logoImageView;

    @InjectPresenter
    SplashPresenter splashPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void navigateToMasterPassword() {
        startActivity(PasswordsActivity.createIntent(this, false));
    }

    @Override
    public void navigateToRegionChoose() {
        startActivity(ChooseRegionActivity.createIntent(this));
    }

    @Override
    public void navigateToMenu() {
        startActivity(MainMenuActivity.createIntent(this));
        finish();
    }

    @Override
    public void requestAppPermissions() {
        String[] permissions = new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH
        };

        Dexter.withActivity(this)
                .withPermissions(permissions)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            splashPresenter.onPermissionsGranted();
                        } else {
                            showMessage(Text.from(R.string.title_error), Text.from(R.string.error_permissions));
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }
}
