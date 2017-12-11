package gibran.com.br.bitcoinmarketservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gibranlyra on 10/12/17.
 */

public class TickerContainer<T> {
    @Expose
    @SerializedName("ticker")
    private T ticker;

    public T getTicker() {
        return ticker;
    }

    public void setTicker(T ticker) {
        this.ticker = ticker;
    }
}
