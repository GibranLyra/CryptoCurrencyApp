package gibran.com.br.bitcoinmarketservice.model;

public class Ticker {
    private int date;
    private String high;
    private String vol;
    private String last;
    private String low;
    private String buy;
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
