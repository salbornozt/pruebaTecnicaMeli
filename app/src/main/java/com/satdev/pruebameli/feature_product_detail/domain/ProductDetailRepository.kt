package com.satdev.pruebameli.feature_product_detail.domain

import com.satdev.pruebameli.core.wrapper.ApiResult
import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail

fun interface ProductDetailRepository {
    suspend fun getProductDetail(productId:String) : ApiResult<ProductDetail?>
}