package com.satdev.pruebameli.core.api.utils

import android.util.Log
import com.satdev.pruebameli.core.wrapper.ErrorWrapper
import retrofit2.Response
import java.net.HttpURLConnection

object ErrorUtils {
    fun getApiError(response: Response<*>): ErrorWrapper {
        Log.e("error", "getApiError: ${response.code()} ${response.message()} ${response.errorBody()}")
        return when (response.code()) {
            HttpURLConnection.HTTP_UNAVAILABLE -> ErrorWrapper.ServiceNotAvailable
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorWrapper.ServiceInternalError(response.code(), response.message())
            else -> ErrorWrapper.UnknownError
        }
    }

}