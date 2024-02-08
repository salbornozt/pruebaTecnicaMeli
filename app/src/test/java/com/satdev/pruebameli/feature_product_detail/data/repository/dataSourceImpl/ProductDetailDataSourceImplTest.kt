package com.satdev.pruebameli.feature_product_detail.data.repository.dataSourceImpl

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.satdev.pruebameli.core.api.ApiService
import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import com.satdev.pruebameli.feature_search_product.data.repository.dataSourceImpl.SearchProductDataSourceImplTest
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class ProductDetailDataSourceImplTest {

    private lateinit var SUT : ProductDetailDataSourceImpl
    private lateinit var apiService: ApiService

    companion object {
        private val PRODUCT_ID = "1"
        private val PRODUCT_DETAIL_RESULT = ProductDetail(id = PRODUCT_ID)
        val SERVER_ERROR : Response<ProductDetail?> = Response.error(500, ResponseBody.create(
            "application/json; charset=utf-8".toMediaType(),""))
    }
    @Before
    fun setUp() {
        apiService = mock { }
        SUT = ProductDetailDataSourceImpl(apiService)
    }

    @Test
    fun getProductDetail_returns_success() = runBlocking {
        //Arrange
        getCorrectResultApiservice()
        //Act
        val result = SUT.getProductDetail(PRODUCT_ID)
        //Assert
        assertThat(result.isSuccessful).isEqualTo(true)
        assertThat(result.body()).isEqualTo(PRODUCT_DETAIL_RESULT)
        verify(1) { apiService.getProductDetail(PRODUCT_ID) }
    }

    @Test
    fun getProductDetail_returns_error() {
        runBlocking {
            //Arrange
            getErrorResultApiservice()
            //Act
            val result = SUT.getProductDetail(PRODUCT_ID)
            //Assert
            assertThat(result.isSuccessful).isEqualTo(false)
            verify(1) { apiService.getProductDetail(PRODUCT_ID) }
        }
    }

    private suspend fun getErrorResultApiservice() {
        whenever(apiService.getProductDetail(PRODUCT_ID)).thenReturn(
            SERVER_ERROR
        )
    }
    private suspend fun getCorrectResultApiservice() {
        whenever(apiService.getProductDetail(PRODUCT_ID)).thenReturn(
            Response.success(PRODUCT_DETAIL_RESULT))
    }
}