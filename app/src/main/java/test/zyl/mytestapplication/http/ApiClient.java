package test.zyl.mytestapplication.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import test.zyl.mytestapplication.BuildConfig;

public class ApiClient {

    /**
     * 获取指定数据类型
     *
     * @return
     */
    public static DbDataService getGankDataService() {

        DbDataService gankDataService = initService(ApiConstants.BaseHost, DbDataService.class);

        return gankDataService;
    }

    /**
     * 获得想要的 retrofit service
     *
     * @param baseUrl 数据请求url
     * @param clazz   想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>     api service
     * @return
     */
    private static <T> T initService(String baseUrl, Class<T> clazz) {
        return getRetrofitInstance(baseUrl).create(clazz);
    }

    /**
     * 单例retrofit
     */
    private static Retrofit retrofitInstance;

    private static Retrofit getRetrofitInstance(String baseUrl) {
        if (retrofitInstance == null || !retrofitInstance.baseUrl().toString().equals(baseUrl)) {
            synchronized (ApiClient.class) {
                if (retrofitInstance == null || !retrofitInstance.baseUrl().toString().equals(baseUrl)) {
                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClientInstance())
                            .build();
                }
            }
        }
        return retrofitInstance;
    }

    /**
     * 单例OkHttpClient
     */
    private static OkHttpClient okHttpClientInstance;

    private static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClientInstance == null) {
            synchronized (ApiClient.class) {
                if (okHttpClientInstance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(httpLoggingInterceptor);
//                      builder.addNetworkInterceptor(new StethoInterceptor());
//                      BuildConfig.STETHO.addNetworkInterceptor(builder);
                    }
                    okHttpClientInstance = builder.build();
                }
            }
        }
        return okHttpClientInstance;
    }
}
