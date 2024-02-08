package com.satdev.pruebameli.feature_search_product.data.repository

import android.util.Log
import com.satdev.pruebameli.core.api.utils.ErrorUtils.getApiError
import com.satdev.pruebameli.core.wrapper.ApiResult
import com.satdev.pruebameli.core.wrapper.ErrorWrapper
import com.satdev.pruebameli.feature_search_product.data.model.SearchProductResult
import com.satdev.pruebameli.feature_search_product.data.repository.dataSource.SearchProductDataSource
import com.satdev.pruebameli.feature_search_product.domain.SearchProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

class SearchProductRepositoryImpl @Inject constructor(private val searchProductDataSource: SearchProductDataSource) :SearchProductRepository {
    override suspend fun searchProduct(query:String): ApiResult<SearchProductResult?> {
        return withContext(Dispatchers.IO) {
            try {
                searchProductDataSource.searchProduct(query).toApiResult()
            } catch (e: Exception) {
                Log.e("error", "searchProduct: ", e)
                ApiResult.Error(e.toErrorWrapper())
            }
        }
    }
}

fun <T> Response<T>.toApiResult() : ApiResult<T?> {
    return if (this.isSuccessful) {
        ApiResult.Success(this.body())
    } else {
        ApiResult.Error(getApiError(this))
    }
}

fun Exception.toErrorWrapper() : ErrorWrapper {
    return when(this){
        is IOException, is SocketException -> {
           ErrorWrapper.ServiceNotAvailable
        }
        else -> {
            ErrorWrapper.UnknownError
        }
    }
}