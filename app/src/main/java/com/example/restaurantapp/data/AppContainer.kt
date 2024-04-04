package com.example.restaurantapp.data

import com.example.restaurantapp.network.RestaurantApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val restaurantRepository: RestaurantRepository
}

class DefaultAppContainer: AppContainer{
    //private const val BASE_URL = "https://httpbin.org/"
    private val BASE_URL = "https://restaurantapi20240224142603.azurewebsites.net/"

    fun createRetrofit(): Retrofit {

        var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build();

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }
    fun createApiService(
        retrofit: Retrofit
    ): RestaurantApiService {
        return retrofit
            .create(RestaurantApiService::class.java)
    }
    override val restaurantRepository: RestaurantRepository by lazy {
        RestaurantRepository(createApiService(createRetrofit()))
    }


}
