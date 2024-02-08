package com.satdev.pruebameli.feature_product_detail.data.repository

import android.util.Log
import com.satdev.pruebameli.core.wrapper.ApiResult
import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail
import com.satdev.pruebameli.feature_product_detail.data.repository.dataSource.ProductDetailDataSource
import com.satdev.pruebameli.feature_product_detail.domain.ProductDetailRepository
import com.satdev.pruebameli.feature_search_product.data.model.Product
import com.satdev.pruebameli.feature_search_product.data.repository.toApiResult
import com.satdev.pruebameli.feature_search_product.data.repository.toErrorWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(private val productDetailDataSource: ProductDetailDataSource) :ProductDetailRepository{
    override suspend fun getProductDetail(productId: String): ApiResult<ProductDetail?> {
        return withContext(Dispatchers.IO){
            try {
                productDetailDataSource.getProductDetail(productId = productId).toApiResult()
            } catch (e: Exception) {
                Log.e("error", "getProductDetail: ", e)
                ApiResult.Error(e.toErrorWrapper())
            }
        }
    }
}