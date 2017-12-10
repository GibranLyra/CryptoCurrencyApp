package gibran.com.br.bitcoinmarketservice.coin;

import gibran.com.br.bitcoinmarketservice.model.Coin;
import gibran.com.br.bitcoinmarketservice.model.Ticker;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 13/09/17.
 */

public interface CoinDataSource {
    public Observable<Ticker> getTicker(Coin coin);

}
