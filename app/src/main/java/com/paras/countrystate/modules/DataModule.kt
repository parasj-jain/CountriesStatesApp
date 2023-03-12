package com.paras.countrystate.modules

import com.paras.countrystate.country.data.DataRepository
import com.paras.countrystate.country.data.impl.DataRepositoryImpl
import com.paras.network.APIEndPoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideDataRepository(
        apiEndPoint: APIEndPoint
    ): DataRepository {
        return DataRepositoryImpl(apiEndPoint)
    }

}