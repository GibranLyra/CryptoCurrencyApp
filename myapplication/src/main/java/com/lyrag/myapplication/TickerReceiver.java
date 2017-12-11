package com.lyrag.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;

import gibran.com.br.bitcoinmarketservice.model.Coin;

/**
 * Created by gibranlyra on 10/12/17.
 */

public class TickerReceiver extends BroadcastReceiver {

    private static HashMap<Activity, TickerReceiver> registrations;
    private TicketReceiverListener listener;

    private TickerReceiver(TicketReceiverListener listener) {
        this.listener = listener;
    }

    public static void register(Activity activity, TicketReceiverListener listener) {
        TickerReceiver receiver = new TickerReceiver(listener);
        Intent serviceIntent = new Intent(activity, TickerIntentService.class);
        activity.startService(serviceIntent);
        // Registers the DownloadStateReceiver and its intent filters
        IntentFilter statusIntentFilter = new IntentFilter(TickerIntentService.BROADCAST_PRICES_ACTION);
        LocalBroadcastManager.getInstance(activity).registerReceiver(receiver, statusIntentFilter);
        registrations = new HashMap<>();
        registrations.put(activity, receiver);
    }

    public static void unregister(Context context) {
        TickerReceiver receiver = registrations.get(context);
        if (receiver != null) {
            context.unregisterReceiver(receiver);
            registrations.remove(context);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == TickerIntentService.BROADCAST_PRICES_ACTION) {
            HashMap<Coin, String> prices = (HashMap<Coin, String>)
                    intent.getSerializableExtra(TickerIntentService.EXTRA_PRICES);
            listener.showPrices(prices);
        }
    }
}
