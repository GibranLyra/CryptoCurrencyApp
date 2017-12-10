package gibran.com.br.bitcoinmarketservice;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.Clock;

import io.reactivex.annotations.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gibran.lyra on 23/08/2017.
 */
public class BitCoinMarketModule {

    private static final String BASE_URL = "https://www.mercadobitcoin.net/api/";
    private static Retrofit retrofit;

    public static void setRetrofit(@Nullable LoggingInterceptor.Level logLevel) {
        if (logLevel == null) {
            logLevel = LoggingInterceptor.Level.BASIC;
        }
        LoggingInterceptor interceptor = new LoggingInterceptor(Clock.systemDefaultZone());
        interceptor.setLogLevel(logLevel);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        builder.addInterceptor(chain -> chain.proceed(chain.request()));
        builder.addNetworkInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.header("Content-Type", "application/json");
            return chain.proceed(requestBuilder.build());
        });
        OkHttpClient okClient = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(getDefaultGsonBuilder()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Gson getDefaultGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
