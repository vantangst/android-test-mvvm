package test.com.androidtestmvvm.data.network

import android.content.Context
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import test.com.androidtestmvvm.BuildConfig
import test.com.androidtestmvvm.data.model.api.LoginResponse
import java.util.concurrent.TimeUnit

interface DemoServices {

    @POST(ApiEndPoint.LOGIN)
    @FormUrlEncoded
    fun login(@Field("email")email : String, @Field("password")password : String): Observable<LoginResponse>

    companion object {
        fun create(): DemoServices {
            val client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(interceptor)
            }
            client.connectTimeout(30, TimeUnit.SECONDS)
            client.readTimeout(30, TimeUnit.SECONDS)
            client.writeTimeout(30, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiEndPoint.BASE_URL)
                .client(client.build())
                .build()

            return retrofit.create(DemoServices::class.java)
        }

    }
}