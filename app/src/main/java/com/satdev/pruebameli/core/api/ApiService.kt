package com.satdev.pruebameli.core.api

import com.satdev.pruebameli.DETAILS_URL
import com.satdev.pruebameli.SEARCH_URL
import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH_URL)
    suspend fun searchProducts(@Query("q") query : String) : Response<SearchProductResult?>

    @GET(DETAILS_URL)
    suspend fun getProductDetail(@Path("id") productId:String) : Response<ProductDetail?>
}