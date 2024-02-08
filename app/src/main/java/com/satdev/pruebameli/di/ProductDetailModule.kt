package com.satdev.pruebameli.di

import com.satdev.pruebameli.core.api.ApiService
import com.satdev.pruebameli.feature_product_detail.data.repository.ProductDetailRepositoryImpl
import com.satdev.pruebameli.feature_product_detail.data.repository.dataSource.ProductDetailDataSource
import com.satdev.pruebameli.feature_product_detail.data.repository.dataSourceImpl.ProductDetailDataSourceImpl
import com.satdev.pruebameli.feature_product_detail.domain.ProductDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ProductDetailModule {
    @Singleton
    @Provides
    fun providesProductDetailDataSource(apiService: ApiService): ProductDetailDataSource {
        return ProductDetailDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesProductDetailRepository(productDetailDataSource: ProductDetailDataSource) : ProductDetailRepository {
        return ProductDetailRepositoryImpl(productDetailDataSource)
    }
}