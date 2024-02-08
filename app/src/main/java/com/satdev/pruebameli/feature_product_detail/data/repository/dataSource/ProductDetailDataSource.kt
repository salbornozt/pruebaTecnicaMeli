package com.satdev.pruebameli.feature_product_detail.data.repository.dataSource

import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail
import retrofit2.Response

fun interface ProductDetailDataSource {
    suspend fun getProductDetail(productId:String) : Response<ProductDetail?>
}