package com.satdev.pruebameli.feature_search_product.data.model

import com.google.gson.annotations.SerializedName

data class SearchProductResult(
    @SerializedName("query"                     ) var query                  : String?                     = null,
    @SerializedName("results"                   ) var results                : ArrayList<Product>          = arrayListOf(),
)
