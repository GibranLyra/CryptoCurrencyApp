package com.lyrag.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import gibran.com.br.bitcoinmarketservice.coin.CoinApi;
import gibran.com.br.bitcoinmarketservice.model.Coin;
import gibran.com.br.bitcoinmarketservice.model.Ticker;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by gibranlyra on 10/12/17.
 */

public class TickerIntentService extends IntentService {

    public static final String BROADCAST_PRICES_ACTION = "com.lyrag.myapplication.TickerIntentService.BROADCAST";
    public static final String EXTRA_PRICES = "com.lyrag.myapplication.TickerIntentService.PRICES";

    public TickerIntentService() {
        super("TickerIntentService");
        Timber.d("TickerIntentService: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        initializeApiWorkers();
    }

    private void initializeApiWorkers() {
        Observable<Ticker> bitCoinTicker = CoinApi.getInstance().getTicker(Coin.BTC);
        Observable<Ticker> liteCoinTicker = CoinApi.getInstance().getTicker(Coin.LTC);
        Observable<Ticker> bCashTicker = CoinApi.getInstance().getTicker(Coin.BCH);
        Observable
                .zip(bitCoinTicker, liteCoinTicker, bCashTicker, (ticker, ticker2, ticker3) -> {
                    HashMap<Coin, String> pricesHashMap = new HashMap<>();
                    pricesHashMap.put(Coin.BTC, ticker.getBuy());
                    pricesHashMap.put(Coin.LTC, ticker2.getBuy());
                    pricesHashMap.put(Coin.BCH, ticker3.getBuy());
                    return pricesHashMap;
                })
                .delay(10, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(prices -> {
                    Timber.d("initializeApiWorkers: Received values.");
                    Intent localIntent = new Intent(BROADCAST_PRICES_ACTION).putExtra(EXTRA_PRICES, prices);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                });
    }
}
