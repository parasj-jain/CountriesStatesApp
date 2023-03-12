package com.paras.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object APIModule {

    @Provides
    @Singleton
    fun provideApi(
        retrofit: Retrofit
    ): APIEndPoint {
        return retrofit.create(APIEndPoint::class.java)
    }

}