package com.satdev.pruebameli.di

import com.satdev.pruebameli.core.api.ApiService
import com.satdev.pruebameli.feature_search_product.data.repository.SearchProductRepositoryImpl
import com.satdev.pruebameli.feature_search_product.data.repository.dataSource.SearchProductDataSource
import com.satdev.pruebameli.feature_search_product.data.repository.dataSourceImpl.SearchProductDataSourceImpl
import com.satdev.pruebameli.feature_search_product.domain.SearchProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchProductModule {

    @Singleton
    @Provides
    fun providesSearchProductDataSource(apiService: ApiService) :SearchProductDataSource {
        return SearchProductDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesSearchProductRepository(searchProductDataSource: SearchProductDataSource) : SearchProductRepository {
        return SearchProductRepositoryImpl(searchProductDataSource)
    }
}