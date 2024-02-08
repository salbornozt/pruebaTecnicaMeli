package com.satdev.pruebameli.feature_product_detail.data.repository.dataSourceImpl

import com.satdev.pruebameli.core.api.ApiService
import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail
import com.satdev.pruebameli.feature_product_detail.data.repository.dataSource.ProductDetailDataSource
import retrofit2.Response
import javax.inject.Inject

class ProductDetailDataSourceImpl @Inject constructor(private val apiService: ApiService) : ProductDetailDataSource {
    override suspend fun getProductDetail(productId: String): Response<ProductDetail?> {
        return apiService.getProductDetail(productId = productId)
    }
}