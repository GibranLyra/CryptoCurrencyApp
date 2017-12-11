package com.lyrag.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.bitcoinmarketservice.model.Coin;

public class MainActivity extends BaseActivity implements TicketReceiverListener {
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

        TickerReceiver.register(this, this);
    }

    @Override
    public void showPrices(HashMap<Coin, String> prices) {
        bitCoinPriceText.setText(prices.get(Coin.BTC));
        liteCoinPriceText.setText(prices.get(Coin.LTC));
        bCashPriceText.setText(prices.get(Coin.BCH));
    }

    @Override
    protected void onDestroy() {
        TickerReceiver.unregister(this);
        super.onDestroy();
    }
}
