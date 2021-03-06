package gibran.com.br.bitcoinmarketservice.coin;

import gibran.com.br.bitcoinmarketservice.model.Ticker;
import gibran.com.br.bitcoinmarketservice.model.TickerContainer;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gibranlyra on 13/09/17.
 */

public interface CoinService {

    @GET("{coin}/ticker")
    Observable<TickerContainer<Ticker>> getTicker(@Path("coin") String coin);

}
