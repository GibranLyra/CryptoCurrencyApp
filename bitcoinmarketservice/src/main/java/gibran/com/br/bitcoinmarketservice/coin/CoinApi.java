package gibran.com.br.bitcoinmarketservice.coin;

import gibran.com.br.bitcoinmarketservice.BitCoinMarketModule;
import gibran.com.br.bitcoinmarketservice.model.Coin;
import gibran.com.br.bitcoinmarketservice.model.Ticker;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 13/09/2017.
 */
public class CoinApi implements CoinDataSource {
    private static CoinApi instance;
    private final CoinService coinService;

    private CoinApi() {
        Retrofit retrofit = BitCoinMarketModule.getRetrofit();
        coinService = retrofit.create(CoinService.class);
    }

    public static CoinApi getInstance() {
        if (instance == null) {
            instance = new CoinApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new CoinApi();
    }


    @Override
    public Observable<Ticker> getTicker(Coin coin) {
        return coinService.getTicker(coin.name())
                .doOnError(e -> Timber.e(e, "getTicker: %s", e.getMessage()));
    }
}