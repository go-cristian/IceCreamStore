package com.example.storedemo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class RestProduct(val name1: String, val name2: String, val price: String,
  val bg_color: String, val type: String)

interface GetProducts {
  @GET("/products")
  suspend fun request(): List<RestProduct>
}

const val BASE_URL = "http://gl-endpoint.herokuapp.com/"

class ProductsRepository {
  private var cached: List<RestProduct> = emptyList()

  suspend fun all(): List<RestProduct> = withContext(Dispatchers.IO) {
    if (cached.isEmpty()) {
      cached = service.request()
    }
    cached
  }

  private val service by lazy {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient().newBuilder()
      .addInterceptor(interceptor)
      .build()
    Retrofit.Builder()
      .client(client)
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(GetProducts::class.java)
  }
}
