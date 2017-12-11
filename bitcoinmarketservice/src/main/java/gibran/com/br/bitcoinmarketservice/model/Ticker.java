package gibran.com.br.bitcoinmarketservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticker {
    @Expose
    @SerializedName("date")
    private int date;
    @Expose
    @SerializedName("high")
    private String high;
    @Expose
    @SerializedName("vol")
    private String vol;
    @Expose
    @SerializedName("last")
    private String last;
    @Expose
    @SerializedName("low")
    private String low;
    @Expose
    @SerializedName("buy")
    private String buy;
    @Expose
    @SerializedName("sell")
    private String sell;

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getHigh() {
        return this.high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getVol() {
        return this.vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLow() {
        return this.low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getBuy() {
        return this.buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return this.sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
