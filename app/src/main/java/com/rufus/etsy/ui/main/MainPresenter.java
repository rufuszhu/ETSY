package com.rufus.etsy.ui.main;

import com.rufus.etsy.data.DataManager;
import com.rufus.etsy.data.local.CurrencyEnum;
import com.rufus.etsy.data.local.PreferencesHelper;
import com.rufus.etsy.data.model.Listing;
import com.rufus.etsy.injection.ConfigPersistent;
import com.rufus.etsy.ui.base.BasePresenter;
import com.rufus.etsy.util.RxUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private int mCurrentPage;
    private Subscription mSubscription;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    MainPresenter(DataManager dataManager, PreferencesHelper preferencesHelper) {
        mDataManager = dataManager;
        mPreferencesHelper = preferencesHelper;
        mCurrentPage = 1;
    }

    int getCurrentPage() {
        return mCurrentPage;
    }

    void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }

    void nextPage() {
        checkViewAttached();
        mCurrentPage++;
        getMvpView().displayPage();
        loadCurrentPage();
    }

    void previousPage() {
        if (mCurrentPage > 1) {
            mCurrentPage--;
            getMvpView().displayPage();
            loadCurrentPage();
        }
    }

    void toFirstPage() {
        mCurrentPage = 1;
        getMvpView().displayPage();
        loadCurrentPage();
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    void loadCurrentPage() {
        checkViewAttached();
        getMvpView().showLoading();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getListingFromServer(mCurrentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Listing>() {
                    @Override
                    public void onCompleted() {
                        if (mPreferencesHelper.getRate(CurrencyEnum.CAD.name()) == 0)
                            getMvpView().startSyncCurrencyService();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the listing.");
                        getMvpView().showError();
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onNext(Listing listings) {
                        getMvpView().hideLoading();
                        if (listings.getResults().isEmpty()) {
                            getMvpView().showResultsEmpty();
                        } else {
                            getMvpView().showResults(listings.getResults());
                        }
                    }
                });
    }


}
