package com.paras.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [APIModule::class])
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun provideAPIInterceptor(): Interceptor {

        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    // Use newBuilder() to customize so that thread-pool and connection-pool same are used
    @Provides
    fun provideOkHttpClientBuilder(
        okHttpClient: Lazy<OkHttpClient>
    ): OkHttpClient.Builder {
        return okHttpClient.value.newBuilder()
    }

    @Provides
    @Singleton
    fun provideBaseOkHttpClient(
        loggingInterceptors: HttpLoggingInterceptor,
        apiInterceptor: ApiInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().apply {
            add(loggingInterceptors)
            add(apiInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory { request -> okHttpClient.newCall(request) }
            .build()
    }

}