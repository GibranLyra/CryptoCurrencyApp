package com.lyrag.cryptocurrencyapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import gibran.com.br.bitcoinmarketservice.BitCoinMarketModule
import gibran.com.br.bitcoinmarketservice.LoggingInterceptor
import gibran.com.br.bitcoinmarketservice.coin.CoinApi
import gibran.com.br.bitcoinmarketservice.model.Coin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    @BindView(R.id.activity_main_bitcoin_price)
    internal var bitCoinPriceText: TextView? = null
    @BindView(R.id.activity_main_litecoin_price)
    internal var liteCoinPriceText: TextView? = null
    @BindView(R.id.activity_main_bcash_price)
    internal var bCashPriceText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showTicker()
    }

    fun showTicker() {
        BitCoinMarketModule.setRetrofit(LoggingInterceptor.Level.BASIC)
        val bitCoinRequest = CoinApi.getInstance().getTicker(Coin.BTC)
        bitCoinRequest
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ticker -> bitCoinPriceText?.text = ticker.last })
        val liteCoinRequest = CoinApi.getInstance().getTicker(Coin.LTC)
        liteCoinRequest
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ticker -> liteCoinPriceText?.text = ticker.last })
        val bCashRequest = CoinApi.getInstance().getTicker(Coin.BCH)
        bCashRequest
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ ticker -> bCashPriceText?.text = ticker.last })


    }
}
