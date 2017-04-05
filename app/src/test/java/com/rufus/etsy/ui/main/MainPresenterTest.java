package com.rufus.etsy.ui.main;

import com.rufus.etsy.data.DataManager;
import com.rufus.etsy.data.local.PreferencesHelper;
import com.rufus.etsy.data.model.Listing;
import com.rufus.etsy.data.model.Result;
import com.rufus.etsy.util.RxSchedulersOverrideRule;
import com.rufus.etsy.util.TestDataFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;
    @Mock
    PreferencesHelper mPreferencesHelper;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager, mPreferencesHelper);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadPageReturnsListing() {
        List<Result> results = TestDataFactory.makeListResult(10);
        Listing listing = new Listing();
        listing.setResults(results);
        when(mMockDataManager.getListingFromServer(anyInt()))
                .thenReturn(Observable.just(listing));

        mMainPresenter.loadCurrentPage();
        verify(mMockMainMvpView).showResults(results);
        verify(mMockMainMvpView).showLoading();
        verify(mMockMainMvpView).hideLoading();
        verify(mMockMainMvpView, never()).showResultsEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadPageReturnsEmptyList() {
        Listing listing = new Listing();
        listing.setResults(Collections.<Result>emptyList());
        when(mMockDataManager.getListingFromServer(anyInt()))
                .thenReturn(Observable.just(listing));

        mMainPresenter.loadCurrentPage();
        verify(mMockMainMvpView).showResultsEmpty();
        verify(mMockMainMvpView).showLoading();
        verify(mMockMainMvpView).hideLoading();
        verify(mMockMainMvpView, never()).showResults(ArgumentMatchers.<Result>anyList());
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadPageFails() {
        when(mMockDataManager.getListingFromServer(anyInt())).thenReturn(Observable.<Listing>error(new RuntimeException()));

        mMainPresenter.loadCurrentPage();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView).showLoading();
        verify(mMockMainMvpView).hideLoading();
        verify(mMockMainMvpView, never()).showResultsEmpty();
        verify(mMockMainMvpView, never()).showResults(ArgumentMatchers.<Result>anyList());
    }

    @Test
    public void toFirstPage() {
        Listing listing = new Listing();
        listing.setResults(Collections.<Result>emptyList());
        when(mMockDataManager.getListingFromServer(anyInt()))
                .thenReturn(Observable.just(listing));

        mMainPresenter.setCurrentPage(1000);
        assertEquals(mMainPresenter.getCurrentPage(), 1000);
        mMainPresenter.toFirstPage();
        assertEquals(mMainPresenter.getCurrentPage(), 1);
        verify(mMockMainMvpView).displayPage();
    }

    @Test
    public void toPreviousPage() {
        mMainPresenter.setCurrentPage(1);
        mMainPresenter.previousPage();
        assertEquals(mMainPresenter.getCurrentPage(), 1);
        verify(mMockMainMvpView, never()).displayPage();
    }

}
