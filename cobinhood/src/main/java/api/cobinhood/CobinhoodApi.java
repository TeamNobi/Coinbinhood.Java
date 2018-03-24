package api.cobinhood;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

import api.cobinhood.api.CobinhoodService;
import api.cobinhood.converters.DateConverter;
import api.cobinhood.converters.RxErrorHandlingCallAdapterFactory;
import api.cobinhood.converters.RetrofitEnumConverter;
import api.cobinhood.interceptors.AuthenticationInterceptor;
import api.cobinhood.models.market.OfferItem;
import api.cobinhood.serializers.DateDeserializer;
import api.cobinhood.serializers.DateSerializer;
import api.cobinhood.serializers.OfferDeserializer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static api.cobinhood.utils.Util.isEmpty;


public class CobinhoodApi {

    private CobinhoodService cobinhoodService;

    public static final String COINBINHOOD_WEB_API_ENDPOINT = "https://api.cobinhood.com/v1/";
    public static final String COINBINHOOD_WEBSOCKET_ENDPOINT = "wss://feed.cobinhood.com/ws";


    public CobinhoodService getCobinhoodService() {
        return cobinhoodService;
    }

    /**
     * This builder is utilized for setting up all the configurations that
     * will be needed to create an instance of the Coinbinhood API
     */
    public static class Builder {
        private String accessToken;
        private Interceptor loggingInterceptor;

        public Interceptor getLoggingInterceptor() {
            return loggingInterceptor;
        }

        public Builder setLoggingInterceptor(Interceptor loggingInterceptor) {
            this.loggingInterceptor = loggingInterceptor;

            return this;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;

            return this;
        }

        public CobinhoodService build() {

            return getRetrofit().create(CobinhoodService.class);

        }

        private Retrofit getRetrofit() {

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            if (loggingInterceptor != null) {
                okHttpBuilder.addInterceptor(loggingInterceptor);
            }

            if (!isEmpty(accessToken)) {
                okHttpBuilder.addInterceptor(new AuthenticationInterceptor(accessToken));
            }


            OkHttpClient okHttpClient = okHttpBuilder.build();


            final GsonBuilder builder = new GsonBuilder();
            builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

            builder.registerTypeAdapter(new TypeToken<List<OfferItem>>(){}.getType(),new OfferDeserializer());
            builder.registerTypeAdapter(Date.class, new DateDeserializer());
            builder.registerTypeAdapter(Date.class, new DateSerializer());

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(new DateConverter())
                    .addConverterFactory(new RetrofitEnumConverter())
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .baseUrl(COINBINHOOD_WEB_API_ENDPOINT)
                    .build();

            return retrofit;
        }
    }

}
