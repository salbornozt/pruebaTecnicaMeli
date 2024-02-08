package com.satdev.pruebameli.feature_product_detail.data.repository

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.satdev.pruebameli.core.wrapper.ApiResult
import com.satdev.pruebameli.core.wrapper.ErrorWrapper
import com.satdev.pruebameli.feature_product_detail.data.model.ProductDetail
import com.satdev.pruebameli.feature_product_detail.data.repository.dataSource.ProductDetailDataSource
import com.satdev.pruebameli.feature_search_product.data.repository.SearchProductRepositoryImplTest
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.net.HttpURLConnection

class ProductDetailRepositoryImplTest {
    private lateinit var SUT: ProductDetailRepositoryImpl
    private lateinit var productDetailDataSource: ProductDetailDataSource

    companion object {
        private val PRODUCT_ID = "1"
        private val PRODUCT_DETAIL_RESULT = ProductDetail(id = PRODUCT_ID)
    }

    @Before
    fun setUp() {
        productDetailDataSource = mock { }
        SUT = ProductDetailRepositoryImpl(productDetailDataSource)
    }

    @Test
    fun getProductDetail_dataSource_returns_success() = runBlocking {
        //Arrange
        getSuccessFromDataSource()
        //Act
        val result = SUT.getProductDetail(PRODUCT_ID)
        //Assert
        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        assertThat(result.data).isEqualTo(PRODUCT_DETAIL_RESULT)
        verify(1) { productDetailDataSource.getProductDetail(PRODUCT_ID) }
    }

    @Test
    fun getProductDetail_datasource_returns_serverError() = runBlocking {
        //Arrange
        getServerErrorFromDataSource()
        //Act
        val result = SUT.getProductDetail(PRODUCT_ID)
        //Assert
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.ServiceInternalError::class.java)
        assertThat(result.errorWrapper?.statusCode).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
        verify(1) { productDetailDataSource.getProductDetail(PRODUCT_ID) }

    }

    @Test
    fun getProductDetail_datasource_returns_ServiceNotAvailableError() = runBlocking {
        //Arrange
        getServiceNotAvialableErrorFromDataSource()
        //Act
        val result = SUT.getProductDetail(PRODUCT_ID)
        //Assert
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.ServiceNotAvailable::class.java)
        verify(1) { productDetailDataSource.getProductDetail(PRODUCT_ID) }
    }

    @Test
    fun searchProduct_datasource_returns_unknownError() {
        runBlocking {
            //Arrange
            getUnknownErrorFromDataSource()
            ///Act
            val result = SUT.getProductDetail(PRODUCT_ID)
            //Assert
            assertThat(result).isInstanceOf(ApiResult.Error::class.java)
            assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.UnknownError::class.java)
            verify(1) { productDetailDataSource.getProductDetail(PRODUCT_ID) }
        }
    }

    @Test
    fun searchProduct_datasource_throws_exception_returns_ServiceNotAvailableError() {
        runBlocking {
            //Arrange
            throwExceptionFromDataSource()
            ///Act
            val result = SUT.getProductDetail(PRODUCT_ID)
            //Assert
            assertThat(result).isInstanceOf(ApiResult.Error::class.java)
            assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.UnknownError::class.java)
        }
    }

    private suspend fun throwExceptionFromDataSource() {
        doThrow(RuntimeException()).`when`(productDetailDataSource).getProductDetail(
            PRODUCT_ID
        )
    }

    private suspend fun getUnknownErrorFromDataSource() {
        whenever(productDetailDataSource.getProductDetail(PRODUCT_ID)).thenReturn(
            Response.error(404,"error".toResponseBody())
        )
    }

    private suspend fun getServiceNotAvialableErrorFromDataSource() {
        whenever(productDetailDataSource.getProductDetail(PRODUCT_ID)).thenReturn(
            Response.error(503,"error".toResponseBody())
        )
    }

    private suspend fun getServerErrorFromDataSource() {
        whenever(productDetailDataSource.getProductDetail(PRODUCT_ID)).thenReturn(
            Response.error(500,"error".toResponseBody())
        )
    }

    private suspend fun getSuccessFromDataSource() {
        whenever(productDetailDataSource.getProductDetail(PRODUCT_ID)).thenReturn(
            Response.success(
                PRODUCT_DETAIL_RESULT
            )
        )
    }
}