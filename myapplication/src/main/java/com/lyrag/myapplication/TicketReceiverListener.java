package com.lyrag.myapplication;

import java.util.HashMap;

import gibran.com.br.bitcoinmarketservice.model.Coin;

/**
 * Created by gibranlyra on 10/12/17.
 */

public interface TicketReceiverListener {

    void showPrices(HashMap<Coin, String> prices);
}
