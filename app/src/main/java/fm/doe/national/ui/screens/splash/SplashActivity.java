package fm.doe.national.ui.screens.splash;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.view.animation.AccelerateInterpolator;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import fm.doe.national.R;
import fm.doe.national.ui.screens.menu.base.MenuActivity;
import fm.doe.national.ui.screens.menu.base.MenuPresenter;

public class SplashActivity extends MenuActivity implements SplashView {

    @BindView(R.id.layout_splash_start)
    ConstraintLayout constraintLayout;

    @InjectPresenter
    SplashPresenter splashPresenter;

    @ProvidePresenter
    SplashPresenter providePresenter() {
        return new SplashPresenter(getAssets());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash_start;
    }

    @Override
    public void showSelector() {
        ConstraintSet newConstraintSet = new ConstraintSet();
        newConstraintSet.clone(getApplicationContext(), R.layout.activity_splash_end);
        newConstraintSet.applyTo(constraintLayout);
        TransitionManager.beginDelayedTransition(constraintLayout, new ChangeBounds()
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(1000));
    }

    @Override
    protected MenuPresenter getPresenter() {
        return splashPresenter;
    }

}
