package com.satdev.pruebameli.feature_search_product.data.repository.dataSourceImpl


import com.google.common.truth.Truth.assertThat
import com.satdev.pruebameli.core.api.ApiService
import com.satdev.pruebameli.feature_search_product.data.model.Product
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class SearchProductDataSourceImplTest {

    private lateinit var SUT: SearchProductDataSourceImpl
    private lateinit var apiService: ApiService

    companion object {
        val TEST_QUERY = "tesing"
        val resultList = arrayListOf<Product>(
            Product(id = "1", title = "Producto 1", thumbnail = "priebas")
        )
        val searchResult = SearchProductResult(query = TEST_QUERY, results = resultList)

        val EMPTY_QUERY = ""
        val emptyResultList = arrayListOf<Product>()
        val emptySearchResult = SearchProductResult(query = EMPTY_QUERY, results = emptyResultList)
        val serverError : Response<SearchProductResult?> = Response.error(500, ResponseBody.create(
            "application/json; charset=utf-8".toMediaType(),""))

    }

    @Before
    fun setUp() {
        apiService = mock { }
        SUT = SearchProductDataSourceImpl(apiService)
    }

    @Test
    fun searchProduct_returns_correct_result() {
        runBlocking {
            //Arrange
            getCorrectResultApiservice()
            //Act
            val result = SUT.searchProduct(TEST_QUERY)
            //Assert
            assertThat(result.isSuccessful).isEqualTo(true)
            assertThat(result.body()).isEqualTo(searchResult)
            verify(1) { apiService.searchProducts(TEST_QUERY) }
        }
    }

    @Test
    fun searchProduct_empty_query_returns_empty_result() {
        runBlocking {
            //Arrange
            getEmptyCorrectResultApiservice()
            //Act
            val result = SUT.searchProduct(EMPTY_QUERY)
            //Assert
            assertThat(result.isSuccessful).isEqualTo(true)
            assertThat(result.body()).isEqualTo(emptySearchResult)
            verify(1) { apiService.searchProducts(TEST_QUERY) }
        }
    }

    @Test
    fun searchProduct_returns_error() {
        runBlocking {
            //Arrange
            getErrorResultApiservice()
            //Act
            val result = SUT.searchProduct(TEST_QUERY)
            //Assert
            assertThat(result.isSuccessful).isEqualTo(false)
            verify(1) { apiService.searchProducts(TEST_QUERY) }
        }
    }

    private suspend fun getErrorResultApiservice() {
        whenever(apiService.searchProducts(TEST_QUERY)).thenReturn(serverError)
    }

    private suspend fun getEmptyCorrectResultApiservice() {
        whenever(apiService.searchProducts(EMPTY_QUERY)).thenReturn(
            Response.success(
                emptySearchResult
            )
        )
    }

    private suspend fun getCorrectResultApiservice() {
        whenever(apiService.searchProducts(TEST_QUERY)).thenReturn(Response.success(searchResult))
    }
}