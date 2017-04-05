package com.rufus.etsy.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rufus.etsy.R;
import com.rufus.etsy.data.local.CurrencyEnum;
import com.rufus.etsy.data.local.PreferencesHelper;
import com.rufus.etsy.data.model.Result;
import com.rufus.etsy.util.CurrencyUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListingResultAdapter extends RecyclerView.Adapter<ListingResultAdapter.ResultViewHolder> {

    private List<Result> mResult;
    private NumberFormat format;
    private PreferencesHelper mPreferencesHelper;
    private CurrencyEnum selectedCurrency;

    @Inject
    ListingResultAdapter(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
        mResult = new ArrayList<>();
        format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        selectedCurrency = CurrencyEnum.USD;
    }

    void setCurrencyEnum(CurrencyEnum currencyEnum) {
        this.selectedCurrency = currencyEnum;
        notifyDataSetChanged();
    }

    void setResults(List<Result> results) {
        mResult = results;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing, parent, false);
        return new ResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ResultViewHolder holder, int position) {

        final Result result = mResult.get(position);
        if (result == null)
            return;
        if (result.getImages() != null && !result.getImages().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(result.getImages().get(0).getUrl75x75())
                    .centerCrop()
                    .crossFade()
                    .into(holder.ivImage);
        }

        holder.tvName.setText(result.getTitle());

        if (!TextUtils.isEmpty(result.getPrice())) {
            CurrencyEnum origin = CurrencyEnum.nameToEnum(result.getCurrencyCode());
            double price = Double.valueOf(result.getPrice());
            if (origin == CurrencyEnum.NOT_SUPPORT) {
                format.setCurrency(Currency.getInstance(result.getCurrencyCode()));
            } else {
                format.setCurrency(Currency.getInstance(selectedCurrency.name()));
                price = CurrencyUtil.convertCurrency(origin, selectedCurrency, price, mPreferencesHelper);
            }
            holder.tvPrice.setText(format.format(price));
        }

        if (!TextUtils.isEmpty(result.getUrl())) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getUrl()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        ResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
