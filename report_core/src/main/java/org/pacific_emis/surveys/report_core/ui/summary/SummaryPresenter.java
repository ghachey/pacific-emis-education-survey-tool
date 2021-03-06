package org.pacific_emis.surveys.report_core.ui.summary;

import com.omegar.mvp.InjectViewState;

import org.pacific_emis.surveys.report_core.domain.ReportInteractor;
import org.pacific_emis.surveys.report_core.ui.base.BaseReportPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SummaryPresenter extends BaseReportPresenter<SummaryView> {

    private final ReportInteractor interactor;

    public SummaryPresenter(ReportInteractor interactor) {
        super(interactor);
        this.interactor = interactor;
        loadSummary();
    }

    private void loadSummary() {
        addDisposable(interactor.getSummarySubjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().setLoadingVisibility(true))
                .doFinally(() -> getViewState().setLoadingVisibility(false))
                .subscribe(getViewState()::setSummaryData, this::handleError));
    }
}
