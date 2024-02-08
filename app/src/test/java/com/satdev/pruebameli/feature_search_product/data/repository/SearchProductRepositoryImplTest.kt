package com.satdev.pruebameli.feature_search_product.data.repository

import com.google.common.truth.Truth.assertThat
import com.satdev.pruebameli.core.wrapper.ApiResult
import com.satdev.pruebameli.core.wrapper.ErrorWrapper
import com.satdev.pruebameli.feature_search_product.data.model.Product
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import com.satdev.pruebameli.feature_search_product.data.repository.dataSource.SearchProductDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR

class SearchProductRepositoryImplTest {
    private lateinit var SUT: SearchProductRepositoryImpl
    private lateinit var searchProductDataSource: SearchProductDataSource

    companion object {
        val SEARCH_QUERY = "pruebas"
        val RESULT_LIST = arrayListOf<Product>(
            Product(id = "1", title = "Producto 1", thumbnail = "priebas")
        )
        val SEARCH_RESULT = SearchProductResult(query = SEARCH_QUERY, results = RESULT_LIST)
    }

    @Before
    fun setUp() {
        searchProductDataSource = mock { }
        SUT = SearchProductRepositoryImpl(searchProductDataSource = searchProductDataSource)

    }

    @Test
    fun searchProduct_datasource_returns_success() {
        runBlocking {
            //Arrange
            getSuccessFromDataSource()
            //Act
            val result = SUT.searchProduct(SEARCH_QUERY)
            //Assert
            assertThat(result).isInstanceOf(ApiResult.Success::class.java)
            assertThat(result.data).isEqualTo(SEARCH_RESULT)
            verify(1) { searchProductDataSource.searchProduct(SEARCH_QUERY) }
        }
    }

    @Test
    fun searchProduct_datasource_returns_serverError() {
        runBlocking {
            //Arrange
            getServerErrorFromDataSource()
            //Act
            val result = SUT.searchProduct(SEARCH_QUERY)
            //Assert
            assertThat(result).isInstanceOf(ApiResult.Error::class.java)
            assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.ServiceInternalError::class.java)
            assertThat(result.errorWrapper?.statusCode).isEqualTo(HTTP_INTERNAL_ERROR)
            verify(1) { searchProductDataSource.searchProduct(SEARCH_QUERY) }
        }
    }

    @Test
    fun searchProduct_datasource_returns_ServiceNotAvailableError() {
        runBlocking {
            //Arrange
            getServiceNotAvialableErrorFromDataSource()
            //Act
            val result = SUT.searchProduct(SEARCH_QUERY)
            //Assert
            assertThat(result).isInstanceOf(ApiResult.Error::class.java)
            assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.ServiceNotAvailable::class.java)
            verify(1) { searchProductDataSource.searchProduct(SEARCH_QUERY) }
        }
    }

    @Test
    fun searchProduct_datasource_returns_unknownError() {
        runBlocking {
            //Arrange
            getUnknownErrorFromDataSource()
            //Act
            val result = SUT.searchProduct(SEARCH_QUERY)
            //Assert
            assertThat(result).isInstanceOf(ApiResult.Error::class.java)
            assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.UnknownError::class.java)
            verify(1) { searchProductDataSource.searchProduct(SEARCH_QUERY) }
        }
    }

    @Test
    fun searchProduct_datasource_throws_exception_returns_ServiceNotAvailableError() {
        runBlocking {
            //Arrange
            throwExceptionFromDataSource()
            //Act
            val result = SUT.searchProduct(SEARCH_QUERY)
            //Assert
            assertThat(result).isInstanceOf(ApiResult.Error::class.java)
            assertThat(result.errorWrapper).isInstanceOf(ErrorWrapper.UnknownError::class.java)
        }
    }

    private suspend fun throwExceptionFromDataSource() {
        doThrow(RuntimeException()).`when`(searchProductDataSource).searchProduct(SEARCH_QUERY)
    }

    private suspend fun getUnknownErrorFromDataSource() {
        whenever(searchProductDataSource.searchProduct(SEARCH_QUERY)).thenReturn(
            Response.error(404,"error".toResponseBody())
        )
    }

    private suspend fun getServiceNotAvialableErrorFromDataSource() {
        whenever(searchProductDataSource.searchProduct(SEARCH_QUERY)).thenReturn(
            Response.error(503,"error".toResponseBody())
        )
    }

    private suspend fun getServerErrorFromDataSource() {
        whenever(searchProductDataSource.searchProduct(SEARCH_QUERY)).thenReturn(
            Response.error(500,"error".toResponseBody())
        )
    }

    private suspend fun getSuccessFromDataSource() {
        whenever(searchProductDataSource.searchProduct(SEARCH_QUERY)).thenReturn(
            Response.success(
                SEARCH_RESULT
            )
        )
    }
}