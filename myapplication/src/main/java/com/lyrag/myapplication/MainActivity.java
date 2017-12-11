package com.lyrag.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.bitcoinmarketservice.model.Coin;
import im.dacer.androidcharts.LineView;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements TicketReceiverListener {
    @BindView(R.id.container)
    ViewGroup container;
    @BindView(R.id.activity_main_bitcoin_price)
    TextView bitCoinPriceText;
    @BindView(R.id.activity_main_litecoin_price)
    TextView liteCoinPriceText;
    @BindView(R.id.activity_main_bcash_price)
    TextView bCashPriceText;
    @BindView(R.id.activity_main_bitcoin_difference)
    TextView bitCoinDifferenceText;
    @BindView(R.id.activity_main_litecoin_difference)
    TextView liteCoinDifferenceText;
    @BindView(R.id.activity_main_bcash_difference)
    TextView bCashDifferenceText;
    @BindView(R.id.activity_main_bitcoin_chart)
    LineView bitCoinChart;
    @BindView(R.id.activity_main_litecoin_chart)
    LineView liteCoinChart;
    @BindView(R.id.activity_main_bcash_chart)
    LineView bCashChart;

    private DecimalFormat myFormatter;
    private int randomint = 100;
    private ArrayList<Float> bitCoinValues = new ArrayList<>();
    private ArrayList<Float> liteCoinValues = new ArrayList<>();
    private ArrayList<Float> bCashValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TickerReceiver.register(this, this);
        myFormatter = new DecimalFormat("0,000");
        initLineView();
    }

    @Override
    protected void onDestroy() {
        TickerReceiver.unregister(this);
        super.onDestroy();
    }

    @Override
    public void showPrices(HashMap<Coin, String> prices) {
        TransitionManager.beginDelayedTransition(container,
                new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN));
        TransitionManager.beginDelayedTransition(container, new Recolor());
        showDifferences(prices);
        randomSet(prices);
        bitCoinPriceText.setTextColor(getResources().getColor(
                isValueup(prices, Coin.BTC, bitCoinPriceText.getText().toString())
                        ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
        liteCoinPriceText.setTextColor(getResources().getColor(
                isValueup(prices, Coin.LTC, liteCoinPriceText.getText().toString())
                        ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
        bCashPriceText.setTextColor(getResources().getColor(
                isValueup(prices, Coin.BCH, bCashPriceText.getText().toString())
                        ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
        bitCoinPriceText.setText(prices.get(Coin.BTC).substring(0, 8));
        liteCoinPriceText.setText(prices.get(Coin.LTC).substring(0, 6));
        bCashPriceText.setText(prices.get(Coin.BCH).substring(0, 6));

    }

    private void initLineView() {
        ArrayList<String> test = new ArrayList<>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i + 1));
        }
        bitCoinChart.setBottomTextList(test);
        bitCoinChart.setColorArray(new int[] {
                Color.parseColor("#F44336"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });
        liteCoinChart.setBottomTextList(test);
        liteCoinChart.setColorArray(new int[] {
                Color.parseColor("#9C27B0"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });
        bCashChart.setBottomTextList(test);
        bCashChart.setColorArray(new int[] {
                Color.parseColor("#2196F3"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });

        bitCoinChart.setDrawDotLine(true);
        liteCoinChart.setDrawDotLine(true);
        bCashChart.setDrawDotLine(true);

        bitCoinChart.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY);
        liteCoinChart.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY);
        bCashChart.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY);
    }

    private void randomSet(HashMap<Coin, String> prices) {
        if (bitCoinValues.size() >= randomint) {
            bitCoinValues.remove(0);
        }
        bitCoinValues.add(Float.valueOf(prices.get(Coin.BTC)));
        if (liteCoinValues.size() >= randomint) {
            liteCoinValues.remove(0);
        }
        liteCoinValues.add(Float.valueOf(prices.get(Coin.LTC)));
        if (bCashValues.size() >= randomint) {
            bCashValues.remove(0);
        }
        bCashValues.add(Float.valueOf(prices.get(Coin.BCH)));

        ArrayList<ArrayList<Float>> bitCoinPrices = new ArrayList<>();
        ArrayList<ArrayList<Float>> liteCoinPrices = new ArrayList<>();
        ArrayList<ArrayList<Float>> bCashPrices = new ArrayList<>();

        bitCoinPrices.add(bitCoinValues);
        bitCoinChart.setFloatDataList(bitCoinPrices);

        liteCoinPrices.add(liteCoinValues);
        liteCoinChart.setFloatDataList(liteCoinPrices);

        bCashPrices.add(bCashValues);
        bCashChart.setFloatDataList(bCashPrices);
    }

    private void showDifferences(HashMap<Coin, String> prices) {
        double bitCoinValue = Double.parseDouble(bitCoinPriceText.getText().toString());
        double liteCoinValue = Double.parseDouble(liteCoinPriceText.getText().toString());
        double bCashValue = Double.parseDouble(bCashPriceText.getText().toString());

        double bitCoinNewValue = Double.parseDouble(prices.get(Coin.BTC));
        double liteCoinNewValue = Double.parseDouble(prices.get(Coin.LTC));
        double bCashCoinNewValue = Double.parseDouble(prices.get(Coin.BCH));

        String bitCoinDifference = myFormatter.format(bitCoinNewValue - bitCoinValue);
        String liteCoinDifference = myFormatter.format(liteCoinNewValue - liteCoinValue );
        String bCashDifference = myFormatter.format(bCashCoinNewValue - bCashValue);

        bitCoinDifferenceText.setText(bitCoinDifference);
        liteCoinDifferenceText.setText(liteCoinDifference);
        bCashDifferenceText.setText(bCashDifference);

        bitCoinDifferenceText.setTextColor(getResources().getColor(((bitCoinNewValue - bitCoinValue) > 0)
                        ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
        liteCoinDifferenceText.setTextColor(getResources().getColor(((liteCoinNewValue - liteCoinValue) > 0)
                ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
        bCashDifferenceText.setTextColor(getResources().getColor(((bCashCoinNewValue - bCashValue) > 0)
                ? android.R.color.holo_green_dark : android.R.color.holo_red_dark));
    }

    private boolean isValueup(HashMap<Coin, String> prices, Coin coin, String currentValue) {
        Timber.d("isValueup: %s", priceRaised(Double.valueOf(currentValue), Double.valueOf(prices.get(coin))));
        return priceRaised(Double.valueOf(currentValue), Double.valueOf(prices.get(coin)));
    }

    boolean priceRaised(double currentValue, double newValue) {
        return currentValue < newValue;
    }
}
