package com.lyrag.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.bitcoinmarketservice.coin.CoinApi;
import gibran.com.br.bitcoinmarketservice.model.Coin;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_bitcoin_price)
    TextView bitCoinPriceText;
    @BindView(R.id.activity_main_litecoin_price)
    TextView liteCoinPriceText;
    @BindView(R.id.activity_main_bcash_price)
    TextView bCashPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CoinApi.getInstance().getTicker(Coin.BTC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ticker -> bitCoinPriceText.setText(ticker.getBuy()));

        CoinApi.getInstance().getTicker(Coin.LTC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ticker -> liteCoinPriceText.setText(ticker.getBuy()));

        CoinApi.getInstance().getTicker(Coin.BCH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ticker -> bCashPriceText.setText(ticker.getBuy()));
    }
}
