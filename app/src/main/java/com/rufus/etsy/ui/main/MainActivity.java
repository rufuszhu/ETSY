package com.rufus.etsy.ui.main;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.rufus.etsy.R;
import com.rufus.etsy.data.SyncCurrencyService;
import com.rufus.etsy.data.local.CurrencyEnum;
import com.rufus.etsy.data.local.PreferencesHelper;
import com.rufus.etsy.data.model.Result;
import com.rufus.etsy.ui.base.BaseActivity;
import com.rufus.etsy.util.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainMvpView {
    private static final String KEY_CURRENT_PAGE = "KEY_CURRENT_PAGE";
    private static final String KEY_SELECTED_CURRENCY = "KEY_SELECTED_CURRENCY";
    private static final int USD = 0;
    private static final int CAD = 1;
    private static final int EUR = 2;
    private static final int GBP = 3;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_current_page)
    TextView tvCurrentPage;

    @OnClick(R.id.iv_back)
    void onBackClicked() {
        mMainPresenter.previousPage();
    }

    @OnClick(R.id.iv_forward)
    void onForwardClicked() {
        mMainPresenter.nextPage();
    }

    @OnClick(R.id.iv_first)
    void onFirstClicked() {
        mMainPresenter.toFirstPage();
    }

    @Inject
    MainPresenter mMainPresenter;
    @Inject
    ListingResultAdapter mListingResultAdapter;
    @Inject
    PreferencesHelper preferencesHelper;

    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private ProgressDialog progressDialog;
    private MenuItem currencyTitle;
    private int selectedCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                mListingResultAdapter.notifyDataSetChanged();
            }
        };

        if (savedInstanceState != null) {
            mMainPresenter.setCurrentPage(savedInstanceState.getInt(KEY_CURRENT_PAGE));
            selectedCurrency = savedInstanceState.getInt(KEY_SELECTED_CURRENCY);
        } else {
            mMainPresenter.setCurrentPage(1);
            selectedCurrency = USD;
        }

        mRecyclerView.setAdapter(mListingResultAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);

        displayPage();
        mMainPresenter.loadCurrentPage();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PAGE, mMainPresenter.getCurrentPage());
        outState.putInt(KEY_SELECTED_CURRENCY, selectedCurrency);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencesHelper.registerListener(listener);
        startSyncCurrencyService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferencesHelper.unregister(listener);
        hideLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        currencyTitle = menu.getItem(0);
        if (selectedCurrency == USD) {
            currencyTitle.setTitle(R.string.usd);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.USD);
        } else if (selectedCurrency == CAD) {
            currencyTitle.setTitle(R.string.cad);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.CAD);
        } else if (selectedCurrency == EUR) {
            currencyTitle.setTitle(R.string.eur);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.EUR);
        } else if (selectedCurrency == GBP) {
            currencyTitle.setTitle(R.string.gbp);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.GBP);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        if (id == R.id.usd) {
            selectedCurrency = USD;
            currencyTitle.setTitle(R.string.usd);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.USD);
            return true;
        }
        if (id == R.id.cad) {
            selectedCurrency = CAD;
            currencyTitle.setTitle(R.string.cad);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.CAD);
            return true;
        }
        if (id == R.id.eur) {
            selectedCurrency = EUR;
            currencyTitle.setTitle(R.string.eur);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.EUR);
            return true;
        }
        if (id == R.id.gbp) {
            selectedCurrency = GBP;
            currencyTitle.setTitle(R.string.gbp);
            mListingResultAdapter.setCurrencyEnum(CurrencyEnum.GBP);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /***** MVP View methods implementation *****/
    //region Description
    @Override
    public void showResults(List<Result> results) {
        mListingResultAdapter.setResults(results);
        mListingResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void showResultsEmpty() {
        mListingResultAdapter.setResults(Collections.<Result>emptyList());
        mListingResultAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_products, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        mListingResultAdapter.setResults(Collections.<Result>emptyList());
        mListingResultAdapter.notifyDataSetChanged();
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_result))
                .show();
    }

    @Override
    public void displayPage() {
        tvCurrentPage.setText(String.valueOf(mMainPresenter.getCurrentPage()));
    }

    @Override
    public void showLoading() {
        if (progressDialog == null)
            progressDialog = DialogFactory.createProgressDialog(this, R.string.loading);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.hide();
    }

    @Override
    public void startSyncCurrencyService() {
        startService(SyncCurrencyService.getStartIntent(this));
    }
    //endregion
}
